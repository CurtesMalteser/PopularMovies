package com.curtesmalteser.popularmovies.repository

import com.curtesmalteser.popularmovies.data.MovieData
import com.curtesmalteser.popularmovies.data.MoviesModelData
import kotlinx.coroutines.flow.Flow

/**
 * Created by António 'Curtes Malteser' Bastião on 25/07/2021.
 */
interface IMoviesRepository {
    val moviesList: Flow<List<MovieData>>
    suspend fun fetchMovies(page: Int): Result<MoviesModelData>
}