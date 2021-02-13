package com.example.evaluation.network.models


import com.google.gson.annotations.SerializedName

data class VideoFile(
    @SerializedName("file_type")
    var fileType: String?,
    @SerializedName("height")
    var height: Int?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("link")
    var link: String?,
    @SerializedName("quality")
    var quality: String?,
    @SerializedName("width")
    var width: Int?
)