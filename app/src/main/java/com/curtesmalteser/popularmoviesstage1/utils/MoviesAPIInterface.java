package com.curtesmalteser.popularmoviesstage1.utils;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by António "Curtes Malteser" Bastião on 21/02/2018.
 */

public interface MoviesAPIInterface {
        @GET("movie/popular")
        Call<MoviesModel> getPopularMovies(@Query("api_key") String apiKey);

        @GET("movie/top_rated")
        Call<MoviesModel> getTopRated(@Query("api_key") String apiKey);
}
