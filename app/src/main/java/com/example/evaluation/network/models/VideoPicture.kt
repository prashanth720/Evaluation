package com.example.evaluation.network.models


import com.google.gson.annotations.SerializedName

data class VideoPicture(
    @SerializedName("id")
    var id: Int?,
    @SerializedName("nr")
    var nr: Int?,
    @SerializedName("picture")
    var picture: String?
)