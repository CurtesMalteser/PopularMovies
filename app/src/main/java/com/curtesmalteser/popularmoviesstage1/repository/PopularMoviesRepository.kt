package com.curtesmalteser.popularmoviesstage1.repository

import com.curtesmalteser.popularmovies.data.MovieData
import com.curtesmalteser.popularmovies.data.MoviesModelData
import com.curtesmalteser.popularmovies.network.IMoviesProvider
import com.curtesmalteser.popularmoviesstage1.di.ApiKey
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Created by António 'Curtes Malteser' Bastião on 25/07/2021.
 */
class PopularMoviesRepository(
    @ApiKey private val apiKey: String,
    private val moviesProvider: IMoviesProvider,
) : IMoviesRepository {

    override val moviesList: MutableStateFlow<List<MovieData>> = MutableStateFlow(emptyList())

    override suspend fun fetchMovies(page: Int): Result<MoviesModelData> {

        val queryParams: Map<String, String> = mapOf(
            "api_key" to apiKey,
            "language" to "en-US",
            "page" to page.toString(),
        )

        return moviesProvider.fetchMovies(queryParams).also {
            it.onSuccess { model ->
                moviesList.emit(model.moviesModels)
            }
        }

    }

}