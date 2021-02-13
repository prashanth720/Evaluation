package com.example.evaluation.network.models


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    var id: Int?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("url")
    var url: String?
)