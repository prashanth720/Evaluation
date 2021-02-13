package com.example.evaluation.repository

import com.example.evaluation.db.database.PhotosVideosDatabase
import com.example.evaluation.network.models.Photo
import com.example.evaluation.network.retrofit.RetrofitBuilder

class Repository(private val db : PhotosVideosDatabase) {

    suspend fun getPhotos(query: String, pageNo: Int) =
        RetrofitBuilder.apiService.getPhotos(query, pageNo)

    suspend fun getCuratedPhotos() =
        RetrofitBuilder.apiService.getCuratedPhotos()

    suspend fun getVideos(query: String, pageNo: Int) =
        RetrofitBuilder.apiService.getVideos(query, pageNo )

    suspend fun insert(photo: Photo) = db.getDao().insert(photo)

    fun getSavedItems() = db.getDao().getAllPhotos()

    suspend fun delete(photo: Photo) = db.getDao().delete(photo)
}