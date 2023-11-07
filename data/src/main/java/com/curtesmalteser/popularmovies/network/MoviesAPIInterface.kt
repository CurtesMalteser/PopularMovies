package com.curtesmalteser.popularmovies.network

import com.curtesmalteser.popularmovies.data.MoviesModelData
import com.curtesmalteser.popularmovies.data.ReviewsModelData
import com.curtesmalteser.popularmovies.data.VideosModelData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

/**
 * Created by António "Curtes Malteser" Bastião on 21/02/2018.
 */
interface MoviesAPIInterface {

    @GET("movie/{id}/videos")
    suspend fun getVideos(
        @Path("id") movieId: String,
        @Query("api_key") apiKey: String
    ): VideosModelData

    @GET("movie/{id}/reviews")
    suspend fun getReviews(
        @Path("id") movieId: String,
        @Query("api_key") apiKey: String
    ): ReviewsModelData

    @GET("movie/popular")
    suspend fun fetchPopularMovies(@QueryMap queryParams: Map<String, String>): MoviesModelData

    @GET("movie/top_rated")
    suspend fun fetchTopRated(@QueryMap queryParams: Map<String, String>): MoviesModelData

}