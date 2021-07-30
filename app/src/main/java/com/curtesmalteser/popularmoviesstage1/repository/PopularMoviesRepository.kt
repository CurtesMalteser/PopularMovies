package com.curtesmalteser.popularmoviesstage1.repository

import com.curtesmalteser.popularmoviesstage1.utils.MoviesModel

/**
 * Created by António 'Curtes Malteser' Bastião on 25/07/2021.
 */
class PopularMoviesRepository(private val moviesProvider: IMoviesProvider) : IMoviesRepository {

    override suspend fun fetchMovies(queryParams: Map<String, String>): MoviesModel = moviesProvider.fetchMovies(queryParams)

}