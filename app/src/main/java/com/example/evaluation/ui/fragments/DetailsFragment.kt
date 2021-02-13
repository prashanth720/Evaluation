package com.example.evaluation.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.evaluation.R
import com.example.evaluation.db.database.PhotosVideosDatabase
import com.example.evaluation.network.models.Photo
import com.example.evaluation.repository.Repository
import com.example.evaluation.ui.viewmodels.PhotosViewModel
import com.example.evaluation.ui.viewmodels.ViewModelProviderFactory
import com.example.evaluation.utils.IntentUtils
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : BaseFragment(R.layout.fragment_details) {

    private var details : Photo?= null
    private lateinit var viewModel: PhotosViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        extractArguments()
    }

    private fun extractArguments(){
        arguments?.let {
            details = it.getSerializable(IntentUtils.DETAILS) as Photo
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        setUpViewModel()
        setUpListener()
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(
            this, ViewModelProviderFactory(
                requireActivity().application,
                Repository(PhotosVideosDatabase(requireContext()))
            )
        ).get(PhotosViewModel::class.java)
    }

    private fun initUI(){
        val url = details?.src?.original
        Glide.with(requireActivity()).load(url).into(img_details)
    }
    private fun setUpListener(){
        img_fav_details.setOnClickListener {
            (it as ImageButton).setImageResource(R.mipmap.favorite_home_select)
            viewModel.saveItem(details!!)
            Snackbar.make(view!!,getString(R.string.saved_successfully), Snackbar.LENGTH_SHORT).show()
        }
        tv_photographer_name_details.text = details?.photographer
    }

    companion object {
        fun newInstance(photo : Photo) = DetailsFragment().apply {
            arguments = Bundle().apply {
                putSerializable(IntentUtils.DETAILS, photo)
            }
        }
    }
}