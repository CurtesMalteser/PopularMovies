package com.curtesmalteser.popularmovies.repository.details

import com.curtesmalteser.popularmovies.core.models.MovieDetails
import com.curtesmalteser.popularmovies.network.MoviesAPIInterface
import com.curtesmalteser.popularmovies.repository.details.MovieDetailsResult.MovieDetailsData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.drop

/**
 * Created by António Bastião on 06.11.2023
 * Refer to <a href="https://github.com/CurtesMalteser">CurtesMalteser github</a>
 */

sealed class MovieDetailsResult {
    data class MovieDetailsData(
        val details: MovieDetails,
    ) : MovieDetails by details, MovieDetailsResult()

    data object NoDetails : MovieDetailsResult()
}

interface IMovieDetailsRepository {

    val movieDetailsFlow: Flow<Result<MovieDetailsResult>>

    suspend fun setupMovie(details: MovieDetails?)
}

internal class MovieDetailsRepository(
    private val moviesAPI: MoviesAPIInterface
) : IMovieDetailsRepository {

    private val _movieDetailsFlow = MutableStateFlow<Result<MovieDetailsResult>>(
        Result.success(MovieDetailsResult.NoDetails)
    )
    override val movieDetailsFlow: Flow<Result<MovieDetailsResult>>
        get() = _movieDetailsFlow.drop(1)

    override suspend fun setupMovie(details: MovieDetails?) {
        details?.let {
            _movieDetailsFlow.emit(Result.success(MovieDetailsData(it)))
        } ?: run {
            Result.success(MovieDetailsResult.NoDetails)
        }
    }

}