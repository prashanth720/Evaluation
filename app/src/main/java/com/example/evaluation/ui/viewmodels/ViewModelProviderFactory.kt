package com.example.evaluation.ui.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.evaluation.repository.Repository
import java.lang.IllegalArgumentException

class ViewModelProviderFactory(val app: Application, private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PhotosViewModel::class.java))
            return PhotosViewModel(app, repository) as T

        if(modelClass.isAssignableFrom(HomeViewModel::class.java))
            return HomeViewModel(app, repository) as T

        if(modelClass.isAssignableFrom(VideosViewModel::class.java))
            return VideosViewModel(app, repository) as T

        if(modelClass.isAssignableFrom(FavoritesViewModel::class.java))
            return FavoritesViewModel(app, repository) as T

        throw IllegalArgumentException("Unknown Class Name")
    }
}