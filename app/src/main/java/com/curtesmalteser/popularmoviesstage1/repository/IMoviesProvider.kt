package com.curtesmalteser.popularmoviesstage1.repository

import com.curtesmalteser.popularmoviesstage1.utils.MoviesAPIInterface
import com.curtesmalteser.popularmoviesstage1.utils.MoviesModel

/**
 * Created by António 'Curtes Malteser' Bastião on 30/07/2021.
 */
interface IMoviesProvider {
    suspend fun fetchMovies(queryParams: Map<String, String>): MoviesModel
}

class PopularMoviesProvider(private val moviesAPI: MoviesAPIInterface): IMoviesProvider {
    override suspend fun fetchMovies(queryParams: Map<String, String>): MoviesModel = moviesAPI.fetchPopularMovies(queryParams)
}