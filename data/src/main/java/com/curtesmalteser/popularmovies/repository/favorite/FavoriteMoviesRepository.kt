package com.curtesmalteser.popularmovies.repository.favorite

import com.curtesmalteser.popularmovies.core.models.MovieDetails
import com.curtesmalteser.popularmovies.data.MoviesModelData
import com.curtesmalteser.popularmovies.database.FavoriteMoviesDao
import com.curtesmalteser.popularmovies.repository.IMoviesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

/**
 * Created by António Bastião on 19.10.2024
 * Refer to <a href="https://github.com/CurtesMalteser">CurtesMalteser github</a>
 */
class FavoriteMoviesRepository(
    private val favoriteMoviesDao: FavoriteMoviesDao,
    private val ioDispatcher: CoroutineDispatcher,
) : IMoviesRepository {

    override val moviesList: Flow<List<MovieDetails>>
        get() = favoriteMoviesDao.moviesList().flowOn(ioDispatcher)

    override suspend fun fetchMovies(page: Int): Result<MoviesModelData> {
        TODO("Not yet implemented! To be implemented with Pagination feature")
    }
}

