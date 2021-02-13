package com.example.evaluation.ui.fragments

import android.app.Service
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.evaluation.R
import com.example.evaluation.db.database.PhotosVideosDatabase
import com.example.evaluation.network.models.Photo
import com.example.evaluation.repository.Repository
import com.example.evaluation.ui.adapters.PagerAdapter
import com.example.evaluation.ui.viewmodels.HomeViewModel
import com.example.evaluation.ui.viewmodels.ViewModelProviderFactory
import com.example.evaluation.utils.Constants
import com.example.evaluation.utils.Status
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.search_bar.*


class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private lateinit var viewModel: HomeViewModel
    private lateinit var pagerAdapter: PagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListener()
        setViewPagerAdapter()
        setUpViewModel()
        subscribeToObservers()
    }

    private fun setUpListener(){
        search_bar_title.setOnEditorActionListener(object : OnEditorActionListener{
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val inputManger = requireActivity().getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputManger.hideSoftInputFromWindow(search_bar_title.windowToken,0)
                    pagerAdapter.updateSearchQuery(search_bar_title.text.toString())
                    viewPager.adapter?.notifyDataSetChanged()
                    search_bar_title.setText("")
                    return true
                }
                return false
            }

        })
    }


    private fun setUpViewModel() {
        viewModel = ViewModelProvider(
            this, ViewModelProviderFactory(
                requireActivity().application,
                Repository(PhotosVideosDatabase(requireContext()))
            )
        ).get(HomeViewModel::class.java)
    }

    private fun subscribeToObservers() {
        viewModel.photos.observe(viewLifecycleOwner, Observer { photosResponse ->
            when (photosResponse.status) {
                Status.SUCCESS -> {
                    photosResponse.data?.let {
                        renderPhoto(it.photos)
                    }
                }
                Status.ERROR -> {
                    photosResponse.message?.let {
                        Toast.makeText(context, "Error Occurred : $it", Toast.LENGTH_LONG).show()
                    }
                }
                Status.LOADING -> {
                    //
                }
            }
        })
    }

    private fun renderPhoto(photos: MutableList<Photo>) {
        val url = photos[0].src?.original
        Glide.with(requireActivity()).load(url).into(img_banner)
    }

    private fun setViewPagerAdapter(){
         pagerAdapter = PagerAdapter(requireActivity(),requireActivity().supportFragmentManager,Constants.DEFAULT_SEARCH_QUERY)
        viewPager.apply {
            adapter = pagerAdapter
            addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        }
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab?.position ?: 0
            }

        })
    }

}