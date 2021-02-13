package com.example.evaluation.ui.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.evaluation.utils.Resources
import com.example.evaluation.R
import com.example.evaluation.network.models.VideosResponse
import com.example.evaluation.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class VideosViewModel(app : Application, private val repository: Repository) : BaseViewModel(app){

    val videos : MutableLiveData<Resources<VideosResponse>> = MutableLiveData()
    private var videosResponse : VideosResponse? = null
    var videosPageNumber = 1

    fun getVideos(query:String) = viewModelScope.launch {
        safeVideos(query)
    }

    private suspend fun safeVideos(query: String){
        videos.postValue(Resources.loading(null))
        try {
            if(hasInternetConnection()) {
                val response = repository.getVideos(query, videosPageNumber)
                videos.postValue(handleResponse(response))
            }else{
                videos.postValue(Resources.error(getApplication<Application>().getString(R.string.no_internet),null))
            }
        }catch (t : Throwable){
            when (t) {
                is IOException -> videos.postValue(
                    Resources.error(getApplication<Application>().getString(
                    R.string.network_error),null))
                else -> videos.postValue(
                    Resources.error(getApplication<Application>().getString(
                    R.string.parse_error), null))
            }
        }
    }

    private fun handleResponse(response: Response<VideosResponse>): Resources<VideosResponse> {
        if(response.isSuccessful){
            response.body()?.let { resultResponse ->
                videosPageNumber++
                if(videosResponse == null){
                    videosResponse = resultResponse
                }else{
                    val oldVideos = videosResponse?.videos
                    val newVideos = resultResponse.videos
                    oldVideos?.addAll(newVideos)
                }
                return Resources.success(videosResponse ?: resultResponse)
            }
        }
        return Resources.error(response.message(),null)
    }
}