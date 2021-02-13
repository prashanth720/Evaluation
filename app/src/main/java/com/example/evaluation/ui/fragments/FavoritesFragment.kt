package com.example.evaluation.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.evaluation.R
import com.example.evaluation.db.database.PhotosVideosDatabase
import com.example.evaluation.network.models.Photo
import com.example.evaluation.repository.Repository
import com.example.evaluation.ui.adapters.FavoritesAdapter
import com.example.evaluation.ui.viewmodels.FavoritesViewModel
import com.example.evaluation.ui.viewmodels.ViewModelProviderFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_favorites.*

class FavoritesFragment : BaseFragment(R.layout.fragment_favorites) {

    private lateinit var favAdapter : FavoritesAdapter
    private lateinit var viewModel : FavoritesViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewModel()
        setUpRecyclerView()
        subscribeToObservers()
        setUpOnItemClickListener()
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(
            this, ViewModelProviderFactory(
                requireActivity().application,
                Repository(PhotosVideosDatabase(requireContext()))
            )
        ).get(FavoritesViewModel::class.java)
    }


    private fun setUpRecyclerView() {
        favAdapter = FavoritesAdapter(arrayListOf())
        rvFavorites.apply {
            adapter = favAdapter
            layoutManager = LinearLayoutManager(activity)
        }
        setUpRecycleViewItemTouchCallback()
    }

    private fun subscribeToObservers(){
        viewModel.getSavedItems().observe(viewLifecycleOwner, Observer { item ->
            renderList(item.toMutableList())
        })
    }

    private fun setUpOnItemClickListener(){
        favAdapter.setOnItemClickListener {

        }
    }

    private fun renderList(photo: MutableList<Photo>) {
        favAdapter.renderItems(photo)
    }

    private val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            val item = favAdapter.getItems()[position]
            viewModel.deleteItem(item)
            Snackbar.make(view!!,getString(R.string.delete_successfull), Snackbar.LENGTH_SHORT).apply {
                subscribeToObservers()
                setAction(getString(R.string.undo)){
                    viewModel.saveItem(item)
                }
                show()
            }
        }
    }

    private fun setUpRecycleViewItemTouchCallback(){
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(rvFavorites)
        }
    }
}