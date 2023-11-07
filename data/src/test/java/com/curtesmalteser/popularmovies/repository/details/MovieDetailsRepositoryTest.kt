package com.curtesmalteser.popularmovies.repository.details

import app.cash.turbine.test
import com.curtesmalteser.popularmovies.data.MoviesModelData
import com.curtesmalteser.popularmovies.data.ReviewsModelData
import com.curtesmalteser.popularmovies.data.VideosModelData
import com.curtesmalteser.popularmovies.network.MoviesAPIInterface
import com.curtesmalteser.popularmovies.util.jsonToData
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

/**
 * Created by António Bastião on 07.11.2023
 * Refer to [CurtesMalteser github](https://github.com/CurtesMalteser)
 */
class MovieDetailsRepositoryTest {

    private val movieDetails = jsonToData<MoviesModelData>("movies_list_response.json")

    private val apiMock = MoviesAPIMock()

    private val sut = MovieDetailsRepository(
        apiKey = "apiKey",
        moviesAPI = apiMock,
    )

    @Test
    fun `setupMovie with details is successful`() = runTest {
        sut.movieDetailsFlow.test {

            sut.setupMovie(movieDetails.moviesModels.first())

            awaitItem().getOrThrow() shouldBe MovieDetailsResult.MovieDetailsData(
                details = movieDetails.moviesModels.first(),
                videosData = Result.success(apiMock.videosData),
                reviewsData = Result.success(apiMock.reviewsData),
            )

        }
    }

    private class MoviesAPIMock : MoviesAPIInterface {

        val videosData = jsonToData<VideosModelData>("videos_response.json")
        val reviewsData = jsonToData<ReviewsModelData>("reviews_response.json")

        override suspend fun getVideos(movieId: String, apiKey: String): VideosModelData =
            videosData

        override suspend fun getReviews(movieId: String, apiKey: String): ReviewsModelData =
            reviewsData

        override suspend fun fetchPopularMovies(queryParams: Map<String, String>): MoviesModelData {
            throw NotImplementedError(message = "Not relevant for this test")
        }

        override suspend fun fetchTopRated(queryParams: Map<String, String>): MoviesModelData {
            throw NotImplementedError(message = "Not relevant for this test")
        }
    }

}