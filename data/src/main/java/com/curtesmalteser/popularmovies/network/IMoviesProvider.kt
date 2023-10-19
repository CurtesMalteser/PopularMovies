package com.curtesmalteser.popularmovies.network

import com.curtesmalteser.popularmovies.data.MoviesModelData
import javax.inject.Inject

/**
 * Created by António 'Curtes Malteser' Bastião on 30/07/2021.
 */
interface IMoviesProvider {
    suspend fun fetchMovies(queryParams: Map<String, String>): Result<MoviesModelData>
}

class PopularMoviesProvider @Inject constructor(private val moviesAPI: MoviesAPIInterface) :
    IMoviesProvider {
    override suspend fun fetchMovies(queryParams: Map<String, String>): Result<MoviesModelData> =
        runCatching {
            moviesAPI.fetchPopularMovies(queryParams)
        }
}

class TopRatedMoviesProvider @Inject constructor(private val moviesAPI: MoviesAPIInterface) :
    IMoviesProvider {
    override suspend fun fetchMovies(queryParams: Map<String, String>): Result<MoviesModelData> =
        runCatching {
            moviesAPI.fetchTopRated(queryParams)
        }
}