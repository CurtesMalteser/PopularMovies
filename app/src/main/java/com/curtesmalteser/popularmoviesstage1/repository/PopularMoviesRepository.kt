package com.curtesmalteser.popularmoviesstage1.repository

import com.curtesmalteser.popularmoviesstage1.di.ApiKey
import com.curtesmalteser.popularmoviesstage1.utils.MoviesModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

/**
 * Created by António 'Curtes Malteser' Bastião on 25/07/2021.
 */
class PopularMoviesRepository @Inject constructor(
    @ApiKey private val apiKey: String,
    private val moviesProvider: IMoviesProvider,
) : IMoviesRepository {

    override val moviesList: MutableStateFlow<List<MoviesModel>> = MutableStateFlow(emptyList())

    override suspend fun fetchMovies(page: Int): Result<MoviesModel> {

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