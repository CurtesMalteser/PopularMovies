package com.curtesmalteser.popularmovies.database

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * Created by António Bastião on 18.10.2024
 * Refer to <a href="https://github.com/CurtesMalteser">CurtesMalteser github</a>
 */
class FavoriteMoviesDaoMock : FavoriteMoviesDao {

    private var favoriteMoviesMap = mutableMapOf<Long, MovieEntity>()

    override suspend fun insert(movie: MovieEntity) {
        favoriteMoviesMap[movie.id] = movie
    }

    override suspend fun delete(id: Long) {
        favoriteMoviesMap.remove(id)
    }

    override fun moviesList(): Flow<List<MovieEntity>> = flowOf(favoriteMoviesMap.values.toList())

    override suspend fun isFavorite(id: Long): Boolean = favoriteMoviesMap.containsKey(id)

}