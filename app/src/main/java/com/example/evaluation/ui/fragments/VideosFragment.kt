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
import com.example.evaluation.network.models.Video
import com.example.evaluation.repository.Repository
import com.example.evaluation.ui.adapters.VideosAdapter
import com.example.evaluation.ui.viewmodels.VideosViewModel
import com.example.evaluation.ui.viewmodels.ViewModelProviderFactory
import com.example.evaluation.utils.Constants
import kotlinx.android.synthetic.main.fragment_photos.*
import kotlinx.android.synthetic.main.fragment_photos.paginationProgressBar
import kotlinx.android.synthetic.main.fragment_videos.*

class VideosFragment : BaseFragment(R.layout.fragment_videos) {
    private lateinit var videosAdapter: VideosAdapter
    private lateinit var viewModel: VideosViewModel
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
        ).get(VideosViewModel::class.java)
    }

    private fun setUpRecyclerView() {
        videosAdapter = VideosAdapter(arrayListOf())
        rvVideos.apply {
            adapter = videosAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@VideosFragment.scrollListener)
        }
    }

    private fun subscribeToObservers() {
        viewModel.getVideos(searchQuery ?: Constants.DEFAULT_SEARCH_QUERY)
        viewModel.videos.observe(viewLifecycleOwner, Observer { videosResponse ->
            when (videosResponse.status) {
                Status.SUCCESS -> {
                    hideProgressBar()
                    videosResponse.data?.let {
                        renderList(it.videos)
                        val totalPages =
                            it.total_results / Constants.QUERY_PAGE_SIZE + 2 //integer div is always rounded off.
                        isLastPage = viewModel.videosPageNumber == totalPages
                        if (isLastPage) rvPhotos.setPadding(0, 0, 0, 0)
                    }
                }
                Status.ERROR -> {
                    hideProgressBar()
                    videosResponse.message?.let {
                        Toast.makeText(context, "Error Occurred : $it", Toast.LENGTH_LONG).show()
                    }
                }
                Status.LOADING -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun renderList(videos: MutableList<Video>) {
        videosAdapter.renderVideos(videos)
    }


    private fun setUpOnclickListener() {
        videosAdapter.setOnItemClickListener { video ->

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
                viewModel.getVideos(searchQuery ?: "Animal")
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
        fun newInstance(searchQuery: String) = VideosFragment().apply {
            arguments = Bundle().apply {
                putString(IntentUtils.SEARCH_QUERY, searchQuery)
            }
        }
    }
}