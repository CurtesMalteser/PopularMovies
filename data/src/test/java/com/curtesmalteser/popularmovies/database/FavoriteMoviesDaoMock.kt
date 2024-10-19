package com.curtesmalteser.popularmovies.database

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

/**
 * Created by António Bastião on 18.10.2024
 * Refer to <a href="https://github.com/CurtesMalteser">CurtesMalteser github</a>
 */
class FavoriteMoviesDaoMock : FavoriteMoviesDao {

    private val favoriteMoviesMapFlow = MutableStateFlow<Map<Long, MovieEntity>>(emptyMap())

    override suspend fun insert(movie: MovieEntity) {
        val favoriteMoviesMap = favoriteMoviesMapFlow.value.toMutableMap()
        favoriteMoviesMap[movie.id] = movie
        favoriteMoviesMapFlow.emit(favoriteMoviesMap)
    }

    override suspend fun delete(id: Long) {
        val favoriteMoviesMap = favoriteMoviesMapFlow.value.toMutableMap()
        favoriteMoviesMap.remove(id)
        favoriteMoviesMapFlow.emit(favoriteMoviesMap)
    }

    override fun moviesList(): Flow<List<MovieEntity>> = favoriteMoviesMapFlow
        .map { it.values.toList() }

    override suspend fun isFavorite(id: Long): Boolean = favoriteMoviesMapFlow.value.containsKey(id)

}