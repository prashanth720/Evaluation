package com.example.evaluation.network.models


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "Photos")
data class Photo(
    @PrimaryKey(autoGenerate = true)
    var key : Int ? = null,
    @SerializedName("avg_color")
    var avgColor: String?,
    @SerializedName("height")
    var height: Int?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("liked")
    var liked: Boolean?,
    @SerializedName("photographer")
    var photographer: String?,
    @SerializedName("photographer_id")
    var photographerId: Int?,
    @SerializedName("photographer_url")
    var photographerUrl: String?,
    @SerializedName("src")
    var src: Src?,
    @SerializedName("url")
    var url: String?,
    @SerializedName("width")
    var width: Int?
) : Serializable