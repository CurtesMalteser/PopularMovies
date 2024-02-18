package com.curtesmalteser.popularmovies.repository.details

import com.curtesmalteser.popularmovies.core.models.MovieDetails
import com.curtesmalteser.popularmovies.data.ReviewsModelData
import com.curtesmalteser.popularmovies.data.VideosModelData
import com.curtesmalteser.popularmovies.network.MoviesAPIInterface
import com.curtesmalteser.popularmovies.repository.details.MovieDetailsResult.MovieDetailsData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onCompletion

/**
 * Created by António Bastião on 06.11.2023
 * Refer to <a href="https://github.com/CurtesMalteser">CurtesMalteser github</a>
 */

sealed class MovieDetailsResult {
    data class MovieDetailsData(
        val details: MovieDetails,
        val videosData: Result<VideosModelData>,
        val reviewsData: Result<ReviewsModelData>,
    ) : MovieDetails by details, MovieDetailsResult()

    data object NoDetails : MovieDetailsResult()
}

interface IMovieDetailsRepository {

    val movieDetailsFlow: Flow<Result<MovieDetailsResult>>

    suspend fun setupMovie(details: MovieDetails?)
}

internal class MovieDetailsRepository(
    private val apiKey: String,
    private val moviesAPI: MoviesAPIInterface,
) : IMovieDetailsRepository {

    private val _movieDetailsFlow = MutableStateFlow<Result<MovieDetailsResult>?>(null)

    override val movieDetailsFlow: Flow<Result<MovieDetailsResult>>
        get() = _movieDetailsFlow
            .filterNotNull()
            .onCompletion {
                _movieDetailsFlow.value = Result.success(MovieDetailsResult.NoDetails)
            }

    override suspend fun setupMovie(details: MovieDetails?) {
        val result = details?.let {
            val videosData = getVideos(it.id)
            val reviewsData = getReviews(it.id)
            Result.success(
                MovieDetailsData(
                    details = it,
                    videosData = videosData,
                    reviewsData = reviewsData,
                )
            )
        } ?: run { Result.success(MovieDetailsResult.NoDetails) }

        _movieDetailsFlow.emit(result)
    }

    private suspend fun getVideos(movieId: Long) = moviesAPI.runCatching {
        getVideos(movieId, apiKey = apiKey)
    }

    private suspend fun getReviews(movieId: Long) =  moviesAPI.runCatching {
getReviews(movieId, apiKey = apiKey)
    }
}