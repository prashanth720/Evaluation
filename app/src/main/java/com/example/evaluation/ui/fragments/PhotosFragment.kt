package com.example.evaluation.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.evaluation.utils.IntentUtils
import com.example.evaluation.utils.Status
import com.example.evaluation.R
import com.example.evaluation.db.database.PhotosVideosDatabase
import com.example.evaluation.network.models.Photo
import com.example.evaluation.repository.Repository
import com.example.evaluation.ui.activities.BaseActivity
import com.example.evaluation.ui.adapters.PhotosAdapter
import com.example.evaluation.ui.viewmodels.PhotosViewModel
import com.example.evaluation.ui.viewmodels.ViewModelProviderFactory
import com.example.evaluation.utils.Constants
import com.example.evaluation.utils.Constants.Companion.QUERY_PAGE_SIZE
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_photos.*

class PhotosFragment: BaseFragment(R.layout.fragment_photos) {

    private lateinit var photosAdapter: PhotosAdapter
    private lateinit var viewModel: PhotosViewModel
    private var isLastPage = false
    private var isScrolling = false
    private var isLoading = false
    private var searchQuery : String?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        extractArguments()
    }

    private fun extractArguments(){
        arguments?.let {
            searchQuery = it.getString(IntentUtils.SEARCH_QUERY)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewModel()
        setUpRecyclerView()
        setUpOnclickListener()
        subscribeToObservers()
    }


    private fun setUpViewModel() {
        viewModel = ViewModelProvider(
            this, ViewModelProviderFactory(
                requireActivity().application,
                Repository(PhotosVideosDatabase(requireContext()))
            )
        ).get(PhotosViewModel::class.java)
    }

    private fun setUpRecyclerView() {
        photosAdapter = PhotosAdapter(arrayListOf())
        rvPhotos.apply {
            adapter = photosAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@PhotosFragment.scrollListener)
        }
    }

    private fun subscribeToObservers() {
        viewModel.getPhotos(searchQuery ?: Constants.DEFAULT_SEARCH_QUERY)
        viewModel.photos.observe(viewLifecycleOwner, Observer { photosResponse ->
            when (photosResponse.status) {
                Status.SUCCESS -> {
                    hideProgressBar()
                    photosResponse.data?.let {
                        renderList(it.photos)
                        val totalPages =
                            it.total_results / QUERY_PAGE_SIZE + 2 //integer div is always rounded off.
                            isLastPage = viewModel.photosPageNumber == totalPages
                        if (isLastPage) rvPhotos.setPadding(0, 0, 0, 0)
                    }
                }
                Status.ERROR -> {
                    hideProgressBar()
                    photosResponse.message?.let {
                        Toast.makeText(context, "Error Occurred : $it", Toast.LENGTH_LONG).show()
                    }
                }
                Status.LOADING -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun renderList(photos: MutableList<Photo>) {
        photosAdapter.renderPhotos(photos)
    }


    private fun setUpOnclickListener() {
        photosAdapter.setOnItemFavClickListener { photo ->
            viewModel.saveItem(photo)
            Snackbar.make(view!!,getString(R.string.saved_successfully), Snackbar.LENGTH_SHORT).show()
        }

        photosAdapter.setOnItemClickListener { photo ->
            (activity as BaseActivity).navigationController?.launchDetailsFragment(photo)
        }
    }

    private fun hideProgressBar() {
        paginationProgressBar.visibility = View.GONE
        isLoading = false
    }

    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val shouldPaginate = isNotLoadingAndNotLastPage && isLastItem && isNotAtBeginning
            if (shouldPaginate) {
                viewModel.getPhotos(searchQuery ?: "Animal")
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, state: Int) {
            super.onScrollStateChanged(recyclerView, state)
            if (state == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) isScrolling =
                true
        }
    }

    companion object {
        fun newInstance(searchQuery : String) = PhotosFragment().apply {
            arguments = Bundle().apply {
                putString(IntentUtils.SEARCH_QUERY, searchQuery)
            }
        }
    }
}