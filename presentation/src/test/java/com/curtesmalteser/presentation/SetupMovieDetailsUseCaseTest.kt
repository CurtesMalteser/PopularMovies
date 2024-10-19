package com.curtesmalteser.presentation

import com.curtesmalteser.popularmovies.data.MovieData
import com.curtesmalteser.popularmovies.data.MoviesModelData
import com.curtesmalteser.popularmovies.repository.IMoviesRepository
import com.curtesmalteser.presentation.details.SetupMovieDetailsUseCase
import com.curtesmalteser.presentation.mock.repository.MovieDetailsRepositoryMock
import io.kotest.assertions.throwables.shouldThrow
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
        movieDetailsRepository = movieDetailsRepositoryMock,
        favoriteMoviesRepository = moviesRepositoryMock,
    )

    @Test
    fun `setupMovieDetailsFor calls repository with that has correct id`() = runTest {
        sut.setupMovieDetailsFor(1)
        movieDetailsRepositoryMock.setupMovieDetailsCalled shouldBe true
    }

    @Test
    fun `setupMovieDetailsFor throws if movie with id is not found`() = runTest {
        shouldThrow<NoSuchElementException> {
            sut.setupMovieDetailsFor(0)
        }
    }

    private class MoviesRepositoryMock : IMoviesRepository {

        private val _moviesList = listOf(
            MovieData(
                id = 1,
                title = "title",
                posterPath = "poster",
                backdropPath = "backdrop",
                overview = "overview",
                releaseDate = "releaseDate",
                voteAverage = "voteAverage",
            )
        )

        override val moviesList: Flow<List<MovieData>> = flowOf(_moviesList)

        override suspend fun fetchMovies(page: Int): Result<MoviesModelData> = Result
            .success(MoviesModelData(1, _moviesList))
    }
}