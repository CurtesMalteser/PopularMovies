package com.curtesmalteser.popularmoviesstage1.utils;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by António "Curtes Malteser" Bastião on 21/02/2018.
 */

public interface MoviesAPIInterface {
        @GET("movie/popular")
        Call<MoviesModel> getPopularMovies(@QueryMap Map<String, String> queryParams);

        @GET("movie/top_rated")
        Call<MoviesModel> getTopRated(@QueryMap Map<String, String> queryParams);

        @GET("movie/{id}/videos")
        Call<VideosModel> getVideos(@Path("id") String movieId, @Query("api_key") String apiKey);

        @GET("movie/{id}/reviews")
        Call<ReviewsModel> getReviews(@Path("id") String movieId, @Query("api_key") String apiKey);
}
