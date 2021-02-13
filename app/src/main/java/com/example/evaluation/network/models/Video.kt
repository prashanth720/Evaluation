package com.example.evaluation.network.models


import com.google.gson.annotations.SerializedName

data class Video(
    @SerializedName("avg_color")
    var avgColor: Any?,
    @SerializedName("duration")
    var duration: Int?,
    @SerializedName("full_res")
    var fullRes: Any?,
    @SerializedName("height")
    var height: Int?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("image")
    var image: String?,
    @SerializedName("tags")
    var tags: List<Any>?,
    @SerializedName("url")
    var url: String?,
    @SerializedName("user")
    var user: User?,
    @SerializedName("video_files")
    var videoFiles: List<VideoFile>?,
    @SerializedName("video_pictures")
    var videoPictures: List<VideoPicture>?,
    @SerializedName("width")
    var width: Int?
)