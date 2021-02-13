package com.example.evaluation.ui.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.evaluation.utils.Resources
import com.example.evaluation.R
import com.example.evaluation.network.models.Photo
import com.example.evaluation.network.models.PhotosResponse
import com.example.evaluation.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class PhotosViewModel(app : Application, private val repository: Repository) : BaseViewModel(app){

    val photos : MutableLiveData<Resources<PhotosResponse>> = MutableLiveData()
    private var photosResponse : PhotosResponse? = null
    var photosPageNumber = 1

    fun getPhotos(query:String) = viewModelScope.launch {
        safePhotos(query)
    }

    fun saveItem(photo: Photo) = viewModelScope.launch {
        repository.insert(photo)
    }

    private suspend fun safePhotos(query: String){
        photos.postValue(Resources.loading(null))
        try {
            if(hasInternetConnection()) {
                val response = repository.getPhotos(query, photosPageNumber)
                photos.postValue(handleResponse(response))
            }else{
                photos.postValue(Resources.error(getApplication<Application>().getString(R.string.no_internet),null))
            }
        }catch (t : Throwable){
            when (t) {
                is IOException -> photos.postValue(
                    Resources.error(getApplication<Application>().getString(
                    R.string.network_error),null))
                else -> photos.postValue(
                    Resources.error(getApplication<Application>().getString(
                    R.string.parse_error), null))
            }
        }
    }

    private fun handleResponse(response: Response<PhotosResponse>): Resources<PhotosResponse> {
        if(response.isSuccessful){
            response.body()?.let { resultResponse ->
                photosPageNumber++
                if(photosResponse == null){
                    photosResponse = resultResponse
                }else{
                    val oldPhotos = photosResponse?.photos
                    val newPhotos = resultResponse.photos
                    oldPhotos?.addAll(newPhotos)
                }
                return Resources.success(photosResponse ?: resultResponse)
            }
        }
        return Resources.error(response.message(),null)
    }

}