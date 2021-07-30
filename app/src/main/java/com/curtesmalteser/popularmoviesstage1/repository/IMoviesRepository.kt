package com.curtesmalteser.popularmoviesstage1.repository

import com.curtesmalteser.popularmoviesstage1.utils.MoviesModel
import kotlinx.coroutines.flow.Flow

/**
 * Created by António 'Curtes Malteser' Bastião on 25/07/2021.
 */
interface IMoviesRepository {
    val moviesList : Flow<List<MoviesModel>>
    suspend fun fetchMovies(page: Int): Result<MoviesModel>
}