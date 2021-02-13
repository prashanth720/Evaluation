package com.example.evaluation.network.models

data class VideosResponse(
    val videos :MutableList<Video>,
    val total_results: Int)