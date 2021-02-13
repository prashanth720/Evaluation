package com.example.evaluation.network.retrofit

import com.example.evaluation.network.api.API
import com.example.evaluation.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitBuilder {

    private fun getRetrofit() : Retrofit {
        return Retrofit.Builder().apply {
            baseUrl(Constants.BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
            client(getOkHttpClient())
        }.build()
    }

    private fun getOkHttpClient() : OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder().apply {
            addInterceptor(logging)
        }.build()
    }

    val apiService : API = getRetrofit().create(API::class.java)
}