package com.curtesmalteser.popularmovies.repository.details

import app.cash.turbine.test
import com.curtesmalteser.popularmovies.core.models.MovieDetails
import com.curtesmalteser.popularmovies.data.MoviesModelData
import com.curtesmalteser.popularmovies.database.FavoriteMoviesDaoMock
import com.curtesmalteser.popularmovies.database.toEntity
import com.curtesmalteser.popularmovies.network.MoviesAPIMock
import com.curtesmalteser.popularmovies.repository.details.MovieDetailsResult.MovieDetailsData
import com.curtesmalteser.popularmovies.util.jsonToData
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import kotlin.Result.Companion.failure

/**
 * Created by António Bastião on 07.11.2023
 * Refer to [CurtesMalteser github](https://github.com/CurtesMalteser)
 */
class MovieDetailsRepositoryTest {

    private val movieDetails = jsonToData<MoviesModelData>("movies_list_response.json")

    private val apiMock = MoviesAPIMock()

    private val favoriteMoviesDaoMock = FavoriteMoviesDaoMock()

    @Test
    fun `setupMovie returns NoDetails on null movie details`() = runTest {
        movieDetailsRepository().let { sut ->
            sut.movieDetailsFlow.test {
                sut.setupMovie(null)
                awaitItem().getOrThrow() shouldBe MovieDetailsResult.NoDetails
            }
        }
    }

    @Test
    fun `setupMovie with details `() = runTest {
        movieDetailsRepository().let { sut ->
            sut.movieDetailsFlow.test {
                sut.setupMovie(movieDetails.moviesModels.first())

                awaitItem().getOrThrow() shouldBe MovieDetailsData(
                    details = movieDetails.moviesModels.first(),
                    videosData = Result.success(apiMock.videosData),
                    reviewsData = Result.success(apiMock.reviewsData),
                    isFavorite = false
                )
            }
        }
    }

    @Test
    fun `movieDetailsFlow result has no movies, nor reviews for id due to failure`() = runTest {
        movieDetailsRepository().let { sut ->
            sut.movieDetailsFlow.test {
                val movieDetails = object : MovieDetails {
                    override val id: Long = -1
                    override val title = ""
                    override val posterPath = ""
                    override val backdropPath = ""
                    override val overview = ""
                    override val releaseDate = ""
                    override val voteAverage = ""
                }

                sut.setupMovie(movieDetails)

                val movieDetailsResult = (awaitItem().getOrThrow() as MovieDetailsData)

                movieDetailsResult.run {
                    videosData shouldBe failure<MovieDetailsData>(
                        MoviesAPIMock.MovieDetailsException(
                            apiMock.noVideosMessageForId(
                                movieDetails.id
                            )
                        )
                    )
                    reviewsData shouldBe failure<MovieDetailsData>(
                        MoviesAPIMock.MovieDetailsException(
                            apiMock.noReviewsMessageForId(
                                movieDetails.id
                            )
                        )
                    )
                }
            }
        }
    }

    @Test
    fun `toggleFavorite updates movie isFavorite to true`() = runTest {
        movieDetailsRepository().let { sut ->
            sut.movieDetailsFlow.test {

                val movie = movieDetails.moviesModels.first()
                sut.setupMovie(movie)

                skipItems(1)

                sut.toggleFavorite(movie)

                val data = awaitItem().getOrThrow() as MovieDetailsData

                data.isFavorite shouldBe true
            }
        }
    }

    @Test
    fun `toggleFavorite updates movie isFavorite to false`() = runTest {
        movieDetailsRepository().let { sut ->
            sut.movieDetailsFlow.test {

                val movie = movieDetails.moviesModels.first()
                favoriteMoviesDaoMock.insert(movie.toEntity())
                sut.setupMovie(movie)

                skipItems(1)

                sut.toggleFavorite(movie)

                val data = awaitItem().getOrThrow() as MovieDetailsData

                data.isFavorite shouldBe false
            }
        }
    }

    private fun TestScope.movieDetailsRepository(): MovieDetailsRepository {
        val testDispatcher = StandardTestDispatcher(testScheduler)
        return MovieDetailsRepository(
            apiKey = "apiKey",
            moviesAPI = apiMock,
            favoriteMoviesDao = favoriteMoviesDaoMock,
            ioDispatcher = testDispatcher
        )
    }

}