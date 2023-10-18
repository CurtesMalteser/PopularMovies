package com.curtesmalteser.popularmovies.data

import com.google.gson.annotations.SerializedName

/**
 * Created by António Bastião on 18.10.2023
 * Refer to <a href="https://github.com/CurtesMalteser">CurtesMalteser github</a>
 */
data class ReviewsModelData(
    @SerializedName("results")
    val reviewsModels: ArrayList<ReviewData>,
)

data class ReviewData(
    @SerializedName("author")
    val author: String,
    @SerializedName("content")
    val content: String,
)
