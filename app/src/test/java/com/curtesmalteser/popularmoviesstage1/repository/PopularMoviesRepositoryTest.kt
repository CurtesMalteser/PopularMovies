package com.curtesmalteser.popularmoviesstage1.repository

import com.curtesmalteser.popularmoviesstage1.util.loadFileAsStringOrNull
import com.curtesmalteser.popularmoviesstage1.utils.MoviesModel
import com.google.gson.Gson
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test

/**
 * Created by António Bastião on 29.09.21
 * Refer to [CurtesMalteser github](https://github.com/CurtesMalteser)
 */
@ExperimentalCoroutinesApi
internal class PopularMoviesRepositoryTest {

    private val moviesProviderMockk = mockk<IMoviesProvider>()

    private val sut = PopularMoviesRepository(
        "test",
        moviesProviderMockk
    )

    private val moviesModel: MoviesModel?
        get() {
            val json = loadFileAsStringOrNull("movies_list_response.json")
            return Gson().fromJson(json, MoviesModel::class.java)
        }

    @Test
    fun `moviesList is not empty after successful request`() = runBlockingTest {

        coEvery { moviesProviderMockk.fetchMovies(any()) } returns Result.success(moviesModel!!)

        val result = sut.fetchMovies(0)

        result.isSuccess shouldBe true

        sut.moviesList.first().isNotEmpty() shouldBe true
        sut.moviesList.first().first().shouldBeInstanceOf<MoviesModel>()

    }

    @Test
    fun `moviesList is empty, it is the default value`() = runBlockingTest {

        sut.moviesList.first().isEmpty() shouldBe true

    }

    @Test
    fun `fetchMovies fails`() = runBlockingTest {

        coEvery { moviesProviderMockk.fetchMovies(any()) } returns Result.failure(Exception())

        val result = sut.fetchMovies(0)

        result.isFailure shouldBe true

    }

    @Test
    fun `fetchMovies succeeds`() = runBlockingTest {

        coEvery { moviesProviderMockk.fetchMovies(any()) } returns Result.success(moviesModel!!)

        val result = sut.fetchMovies(0)

        result.isSuccess shouldBe true

        result.getOrNull().shouldBeInstanceOf<MoviesModel>()

    }
}
