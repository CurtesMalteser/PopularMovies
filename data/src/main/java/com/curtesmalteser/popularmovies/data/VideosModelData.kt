package com.curtesmalteser.popularmovies.data

import com.google.gson.annotations.SerializedName

/**
 * Created by António Bastião on 18.10.2023
 * Refer to <a href="https://github.com/CurtesMalteser">CurtesMalteser github</a>
 */
data class VideosModelData(
    @SerializedName("id")
    val id: Long,
    @SerializedName("results")
    var videosModels: List<VideoData>,
)

data class VideoData(
    @SerializedName("id")
    val id: String,
    @SerializedName("key")
    val key: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("type")
    val type: String,
)