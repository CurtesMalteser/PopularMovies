package com.curtesmalteser.popularmovies.network

import com.curtesmalteser.popularmovies.data.MoviesModelData
import com.curtesmalteser.popularmovies.data.ReviewsModelData
import com.curtesmalteser.popularmovies.data.VideosModelData
import com.curtesmalteser.popularmovies.util.jsonToData

/**
 * Created by António Bastião on 18.10.2024
 * Refer to <a href="https://github.com/CurtesMalteser">CurtesMalteser github</a>
 */
// TODO: 18.10.2024 Move to MovieDetails provider
class MoviesAPIMock : MoviesAPIInterface {

    val videosData = jsonToData<VideosModelData>("videos_response.json")
    val reviewsData = jsonToData<ReviewsModelData>("reviews_response.json")

    val noVideosMessageForId = { id: Long -> "No videos found for movieId: $id" }
    val noReviewsMessageForId = { id: Long -> "No reviews found for movieId: $id" }
    override suspend fun getVideos(movieId: Long, apiKey: String): VideosModelData = run {
        if (movieId == videosData.id) videosData else throw MovieDetailsException(
            noVideosMessageForId(movieId)
        )
    }

    override suspend fun getReviews(movieId: Long, apiKey: String): ReviewsModelData = run {
        if (movieId == reviewsData.id) reviewsData else throw MovieDetailsException(
            noReviewsMessageForId(movieId)
        )
    }

    override suspend fun fetchPopularMovies(queryParams: Map<String, String>): MoviesModelData {
        throw NotImplementedError(message = "Not relevant for this test")
    }

    override suspend fun fetchTopRated(queryParams: Map<String, String>): MoviesModelData {
        throw NotImplementedError(message = "Not relevant for this test")
    }

    class MovieDetailsException(message: String) : Exception(message) {
        override fun equals(other: Any?): Boolean =
            message == (other as MovieDetailsException).message

        override fun hashCode(): Int = javaClass.hashCode()
    }
}
