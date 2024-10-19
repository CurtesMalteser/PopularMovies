package com.curtesmalteser.popularmovies.repository.favorite

import app.cash.turbine.test
import com.curtesmalteser.popularmovies.data.MoviesModelData
import com.curtesmalteser.popularmovies.database.FavoriteMoviesDaoMock
import com.curtesmalteser.popularmovies.database.toEntity
import com.curtesmalteser.popularmovies.util.jsonToData
import io.kotest.matchers.longs.shouldBeExactly
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

/**
 * Created by António Bastião on 19.10.2024
 * Refer to [CurtesMalteser github](https://github.com/CurtesMalteser)
 */
class FavoriteMoviesRepositoryTest {

    private val favoriteMoviesDaoMock = FavoriteMoviesDaoMock()

    @Test
    fun `moviesList is empty`() = runTest {
        favoriteMoviesRepository().let { sut ->
            sut.moviesList.test {
                awaitItem().size shouldBe 0
            }
        }
    }

    @Test
    fun `moviesList emit items stored in dao`() = runTest {
        favoriteMoviesRepository().let { sut ->
            sut.moviesList.test {
                val movieDetails = jsonToData<MoviesModelData>("movies_list_response.json")
                val movie = movieDetails.moviesModels.first()
                favoriteMoviesDaoMock.insert(movie.toEntity())
                awaitItem().first().id shouldBeExactly movie.id
            }
        }
    }

    private fun TestScope.favoriteMoviesRepository(): FavoriteMoviesRepository {
        val testDispatcher = StandardTestDispatcher(testScheduler)
        return FavoriteMoviesRepository(
            favoriteMoviesDao = favoriteMoviesDaoMock,
            ioDispatcher = testDispatcher
        )
    }
}