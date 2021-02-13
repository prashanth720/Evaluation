package com.example.evaluation.ui.viewmodels

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.evaluation.network.models.Photo
import com.example.evaluation.repository.Repository
import kotlinx.coroutines.launch

class FavoritesViewModel(app : Application, private val repository: Repository) : BaseViewModel(app) {

    fun saveItem(photo: Photo) = viewModelScope.launch {
        repository.insert(photo)
    }

    fun deleteItem(photo: Photo) = viewModelScope.launch {
        repository.delete(photo)
    }

    fun getSavedItems() = repository.getSavedItems()
}