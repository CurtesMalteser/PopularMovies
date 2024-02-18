package com.curtesmalteser.presentation

import com.curtesmalteser.popularmovies.core.models.MovieDetails
import com.curtesmalteser.popularmovies.data.MovieData
import com.curtesmalteser.popularmovies.data.MoviesModelData
import com.curtesmalteser.popularmovies.repository.IMoviesRepository
import com.curtesmalteser.popularmovies.repository.details.IMovieDetailsRepository
import com.curtesmalteser.popularmovies.repository.details.MovieDetailsResult
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

/**
 * Created by António Bastião on 06.11.2023
 * Refer to [CurtesMalteser github](https://github.com/CurtesMalteser)
 */
class SetupMovieDetailsUseCaseTest {

    private val moviesRepositoryMock = MoviesRepositoryMock()
    private val movieDetailsRepositoryMock = MovieDetailsRepositoryMock()

    private val sut = SetupMovieDetailsUseCase(
        popularMoviesRepository = moviesRepositoryMock,
        topMoviesRepository = moviesRepositoryMock,
        movieDetailsRepository = movieDetailsRepositoryMock
    )

    @Test
    fun `setupMovieDetailsFor calls repository with that has correct id`() = runTest {
        sut.setupMovieDetailsFor(1)
        movieDetailsRepositoryMock.setupMovieDetailsCalled shouldBe true
    }

    private class MoviesRepositoryMock : IMoviesRepository {

        override val moviesList: Flow<List<MovieData>> = flowOf(emptyList())

        override suspend fun fetchMovies(page: Int): Result<MoviesModelData> = Result
            .success(MoviesModelData(1, emptyList()))
    }

    private class MovieDetailsRepositoryMock: IMovieDetailsRepository {

        private var _setupMovieDetailsCalled = false
        val setupMovieDetailsCalled get() = _setupMovieDetailsCalled

        override val movieDetailsFlow: Flow<Result<MovieDetailsResult>>
            get() = flowOf(Result.success(MovieDetailsResult.NoDetails))

        override suspend fun setupMovie(details: MovieDetails?) {
            _setupMovieDetailsCalled = true
        }

    }
}