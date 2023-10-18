package com.curtesmalteser.popularmoviesstage1.utils

import com.curtesmalteser.popularmovies.data.MoviesModelData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

/**
 * Created by António "Curtes Malteser" Bastião on 21/02/2018.
 */
interface MoviesAPIInterface {

    @GET("movie/popular")
    fun getPopularMovies(@QueryMap queryParams: Map<String, String>): Call<MoviesModel>

    @GET("movie/top_rated")
    fun getTopRated(@QueryMap queryParams: Map<String, String>): Call<MoviesModel>

    @GET("movie/{id}/videos")
    fun getVideos(
        @Path("id") movieId: String,
        @Query("api_key") apiKey: String
    ): Call<VideosModel>

    @GET("movie/{id}/reviews")
    fun getReviews(
        @Path("id") movieId: String,
        @Query("api_key") apiKey: String
    ): Call<ReviewsModel>

    @GET("movie/popular")
    suspend fun fetchPopularMovies(@QueryMap queryParams: Map<String, String>): MoviesModelData

    @GET("movie/top_rated")
    suspend fun fetchTopRated(@QueryMap queryParams: Map<String, String>): MoviesModelData

}