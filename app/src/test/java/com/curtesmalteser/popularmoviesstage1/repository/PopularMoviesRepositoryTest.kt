package com.curtesmalteser.popularmoviesstage1.repository

import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test

/**
 * Created by António Bastião on 29.09.21
 * Refer to [CurtesMalteser github](https://github.com/CurtesMalteser)
 */
internal class PopularMoviesRepositoryTest {

    private val moviesProviderMockk = mockk<IMoviesProvider>()
    private val sut = PopularMoviesRepository(
        "test",
        moviesProviderMockk
    )

    @Test
    fun getMoviesList() {
    }

    @Test
    fun `fetchMovies fails`() = runBlockingTest {

        coEvery { moviesProviderMockk.fetchMovies(any()) } returns Result.failure(Exception())
        val result = sut.fetchMovies(0)


        result.isSuccess shouldBe false

    }
}