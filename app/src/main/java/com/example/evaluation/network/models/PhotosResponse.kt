package com.example.evaluation.network.models

data class PhotosResponse(
    val photos :MutableList<Photo>,
    val total_results: Int
)