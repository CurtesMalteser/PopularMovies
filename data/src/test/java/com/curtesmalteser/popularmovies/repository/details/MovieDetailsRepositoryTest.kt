package com.curtesmalteser.popularmovies.repository.details

import app.cash.turbine.test
import com.curtesmalteser.popularmovies.core.models.MovieDetails
import com.curtesmalteser.popularmovies.data.MoviesModelData
import com.curtesmalteser.popularmovies.data.ReviewsModelData
import com.curtesmalteser.popularmovies.data.VideosModelData
import com.curtesmalteser.popularmovies.network.MoviesAPIInterface
import com.curtesmalteser.popularmovies.repository.details.MovieDetailsRepositoryTest.MoviesAPIMock.MovieDetailsException
import com.curtesmalteser.popularmovies.repository.details.MovieDetailsResult.MovieDetailsData
import com.curtesmalteser.popularmovies.util.jsonToData
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import java.lang.Exception
import kotlin.Result.Companion.failure

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
    fun `setupMovie returns NoDetails on null movie details`() = runTest {
        sut.movieDetailsFlow.test {

            sut.setupMovie(null)

            awaitItem().getOrThrow() shouldBe MovieDetailsResult.NoDetails

        }
    }

    @Test
    fun `setupMovie with details `() = runTest {
        sut.movieDetailsFlow.test {

            sut.setupMovie(movieDetails.moviesModels.first())

            awaitItem().getOrThrow() shouldBe MovieDetailsData(
                details = movieDetails.moviesModels.first(),
                videosData = Result.success(apiMock.videosData),
                reviewsData = Result.success(apiMock.reviewsData),
            )

        }
    }

    @Test
    fun `movieDetailsFlow result has no movies, nor reviews for id due to failure`() = runTest {
        sut.movieDetailsFlow.test {

            val movieDetails = object : MovieDetails {
                override val id: Long = -1
                override val title = ""
                override val posterPath = ""
                override val backdropPath = ""
                override val overview = ""
                override val releaseDate = ""
            }

            sut.setupMovie(movieDetails)

            val movieDetailsResult =  (awaitItem().getOrThrow() as MovieDetailsData)

            movieDetailsResult.run {
                videosData shouldBe failure<MovieDetailsData>(MovieDetailsException(apiMock.noVideosMessageForId(movieDetails.id)))
                reviewsData shouldBe failure<MovieDetailsData>(MovieDetailsException(apiMock.noReviewsMessageForId(movieDetails.id)))
            }
        }
    }

    private class MoviesAPIMock : MoviesAPIInterface {

        val videosData = jsonToData<VideosModelData>("videos_response.json")
        val reviewsData = jsonToData<ReviewsModelData>("reviews_response.json")

        val noVideosMessageForId = { id: Long -> "No videos found for movieId: $id" }
        val noReviewsMessageForId = { id: Long -> "No reviews found for movieId: $id" }
        override suspend fun getVideos(movieId: Long, apiKey: String): VideosModelData = run {
            if(movieId == videosData.id) videosData else throw MovieDetailsException(noVideosMessageForId(movieId))
        }

        override suspend fun getReviews(movieId: Long, apiKey: String): ReviewsModelData = run {
            if(movieId == reviewsData.id) reviewsData else throw MovieDetailsException(noReviewsMessageForId(movieId))
        }

        override suspend fun fetchPopularMovies(queryParams: Map<String, String>): MoviesModelData {
            throw NotImplementedError(message = "Not relevant for this test")
        }

        override suspend fun fetchTopRated(queryParams: Map<String, String>): MoviesModelData {
            throw NotImplementedError(message = "Not relevant for this test")
        }

        class MovieDetailsException(message: String) : Exception(message) {
            override fun equals(other: Any?): Boolean = message == (other as MovieDetailsException).message
            override fun hashCode(): Int = javaClass.hashCode()
        }
    }

}