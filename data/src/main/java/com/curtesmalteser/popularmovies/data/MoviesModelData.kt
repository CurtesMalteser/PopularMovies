package com.curtesmalteser.popularmovies.data

import com.curtesmalteser.popularmovies.core.models.MovieDetails
import com.google.gson.annotations.SerializedName

/**
 * Created by António Bastião on 18.10.2023
 * Refer to <a href="https://github.com/CurtesMalteser">CurtesMalteser github</a>
 */
data class MoviesModelData(
    @SerializedName("page")
    val page: Long,
    @SerializedName("results")
    val moviesModels: List<MovieData>,
)

data class MovieData(
    @SerializedName("id")
    override val id: Long,
    @SerializedName("title")
    override val title: String,
    @SerializedName("vote_average")
    override val voteAverage: String,
    @SerializedName("poster_path")
    override val posterPath: String,
    @SerializedName("backdrop_path")
    override val backdropPath: String,
    @SerializedName("overview")
    override val overview: String,
    @SerializedName("release_date")
    override val releaseDate: String,
) : MovieDetails