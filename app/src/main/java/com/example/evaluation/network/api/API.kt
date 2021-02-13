package com.example.evaluation.network.api

import com.example.evaluation.network.models.PhotosResponse
import com.example.evaluation.network.models.VideosResponse
import com.example.evaluation.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface API {
    @Headers("Authorization: ${Constants.API_KEY}")
    @GET(Constants.SEARCH_PHOTO_URL)
    suspend fun getPhotos(
        @Query("query") query : String = "Animal",
        @Query("page") page : Int = 1,
        @Query("per_page") per_page : Int = 20
    ): Response<PhotosResponse>

    @Headers("Authorization: ${Constants.API_KEY}")
    @GET(Constants.SEARCH_CURATED_PHOTO_URL)
    suspend fun getCuratedPhotos(
        @Query("per_page") per_page : Int = 1
    ): Response<PhotosResponse>

    @Headers("Authorization: ${Constants.API_KEY}")
    @GET(Constants.SEARCH_VIDEO_URL)
    suspend fun getVideos(
        @Query("query") query : String = "Animal",
        @Query("page") page : Int = 1,
        @Query("per_page") per_page : Int = 20
    ): Response<VideosResponse>


}