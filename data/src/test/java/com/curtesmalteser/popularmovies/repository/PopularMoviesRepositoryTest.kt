package com.curtesmalteser.popularmovies.repository

import com.curtesmalteser.popularmovies.data.MovieData
import com.curtesmalteser.popularmovies.data.MoviesModelData
import com.curtesmalteser.popularmovies.network.IMoviesProvider
import com.curtesmalteser.popularmovies.util.jsonToData
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

/**
 * Created by António Bastião on 29.09.21
 * Refer to [CurtesMalteser github](https://github.com/CurtesMalteser)
 */
@ExperimentalCoroutinesApi
internal class PopularMoviesRepositoryTest {

    private val moviesProviderMockk = MoviesProviderMock()

    private val sut = PopularMoviesRepository(
        "test",
        moviesProviderMockk
    )

    @Test
    fun `moviesList is not empty after successful request`() = runTest {

        val result = sut.fetchMovies(0)

        result.isSuccess shouldBe true

        sut.moviesList.first().isNotEmpty() shouldBe true
        sut.moviesList.first().first().shouldBeInstanceOf<MovieData>()

    }

    @Test
    fun `moviesList is empty, it is the default value`() = runTest {

        sut.moviesList.first().isEmpty() shouldBe true

    }

    @Test
    fun `fetchMovies fails`() = runTest {

         moviesProviderMockk.mockFailure()

        val result = sut.fetchMovies(0)

        result.isFailure shouldBe true

    }

    @Test
    fun `fetchMovies succeeds`() = runTest {

        val result = sut.fetchMovies(0)

        result.isSuccess shouldBe true

        result.getOrNull().shouldBeInstanceOf<MoviesModelData>()

    }

    private class MoviesProviderMock : IMoviesProvider {

        private var isFailure = false

        private val moviesModel: MoviesModelData
            get() = jsonToData("movies_list_response.json")


        override suspend fun fetchMovies(queryParams: Map<String, String>): Result<MoviesModelData> {
            return Result.success(moviesModel).takeIf { !isFailure } ?: Result.failure(Exception())
        }

        fun mockFailure() {
            isFailure = true
        }

    }
}
