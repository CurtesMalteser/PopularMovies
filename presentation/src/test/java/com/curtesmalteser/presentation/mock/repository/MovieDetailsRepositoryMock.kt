package com.curtesmalteser.presentation.mock.repository

import com.curtesmalteser.popularmovies.core.models.MovieDetails
import com.curtesmalteser.popularmovies.repository.details.IMovieDetailsRepository
import com.curtesmalteser.popularmovies.repository.details.MovieDetailsResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * Created by António Bastião on 19.10.2024
 * Refer to <a href="https://github.com/CurtesMalteser">CurtesMalteser github</a>
 */
class MovieDetailsRepositoryMock: IMovieDetailsRepository {

    private var _setupMovieDetailsCalled = false
    val setupMovieDetailsCalled get() = _setupMovieDetailsCalled

    private var _toggleFavoriteCalled = false
        val toggleFavoriteCalled get() = _toggleFavoriteCalled

    override val movieDetailsFlow: Flow<Result<MovieDetailsResult>>
        get() = flowOf(Result.success(MovieDetailsResult.NoDetails))

    override suspend fun setupMovie(details: MovieDetails?) {
        _setupMovieDetailsCalled = true
    }

    override suspend fun toggleFavorite(details: MovieDetails) {
        _toggleFavoriteCalled = true

    }

}
