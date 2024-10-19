package com.curtesmalteser.popularmovies.repository.details

import com.curtesmalteser.popularmovies.core.models.MovieDetails
import com.curtesmalteser.popularmovies.data.ReviewsModelData
import com.curtesmalteser.popularmovies.data.VideosModelData
import com.curtesmalteser.popularmovies.database.FavoriteMoviesDao
import com.curtesmalteser.popularmovies.database.MovieEntity
import com.curtesmalteser.popularmovies.database.toEntity
import com.curtesmalteser.popularmovies.network.MoviesAPIInterface
import com.curtesmalteser.popularmovies.repository.details.MovieDetailsResult.MovieDetailsData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.withContext

/**
 * Created by António Bastião on 06.11.2023
 * Refer to <a href="https://github.com/CurtesMalteser">CurtesMalteser github</a>
 */

sealed class MovieDetailsResult {
    data class MovieDetailsData(
        private val details: MovieDetails,
        // TODO: 18.10.2024 Handle with result, in case db fails to inform user
        val isFavorite: Boolean,
        val videosData: Result<VideosModelData>,
        val reviewsData: Result<ReviewsModelData>,
    ) : MovieDetails by details, MovieDetailsResult()

    data object NoDetails : MovieDetailsResult()
}

interface IMovieDetailsRepository {

    val movieDetailsFlow: Flow<Result<MovieDetailsResult>>

    suspend fun setupMovie(details: MovieDetails?)

    suspend fun toggleFavorite(details: MovieDetails)
}

internal class MovieDetailsRepository(
    private val apiKey: String,
    private val moviesAPI: MoviesAPIInterface, // TODO: 18.10.2024 Move to MovieDetails provider
    private val favoriteMoviesDao: FavoriteMoviesDao,
    private val ioDispatcher: CoroutineDispatcher,
) : IMovieDetailsRepository {

    private val _movieDetailsFlow = MutableStateFlow<Result<MovieDetailsResult>?>(null)

    override val movieDetailsFlow: Flow<Result<MovieDetailsResult>>
        get() = _movieDetailsFlow
            .filterNotNull()
            .onCompletion {
                _movieDetailsFlow.value = Result.success(MovieDetailsResult.NoDetails)
            }.flowOn(ioDispatcher)

    override suspend fun setupMovie(details: MovieDetails?) = withContext(ioDispatcher) {
        val result = details?.let {
            val videosData = getVideos(it.id)
            val reviewsData = getReviews(it.id)
            val isFavorite = favoriteMoviesDao.isFavorite(it.id)
            Result.success(
                MovieDetailsData(
                    details = it,
                    videosData = videosData,
                    reviewsData = reviewsData,
                    isFavorite = isFavorite,
                )
            )
        } ?: run { Result.success(MovieDetailsResult.NoDetails) }

        _movieDetailsFlow.emit(result)
    }

    override suspend fun toggleFavorite(details: MovieDetails) = withContext(ioDispatcher) {
        runCatching {
            toggleFavoriteInDB(details.toEntity())
        }.fold(
            onSuccess = { isFavorite ->
                _movieDetailsFlow.value = _movieDetailsFlow.value?.map {
                    (it as? MovieDetailsData)?.copy(isFavorite = isFavorite) ?: it
                }
            },
            onFailure = { /*Intentionally left empty. It will emit a result isFavorite */ }
        )
    }

    private suspend fun toggleFavoriteInDB(entity: MovieEntity): Boolean = favoriteMoviesDao
        .isFavorite(entity.id).also { isFavorite ->
            if (isFavorite) {
                favoriteMoviesDao.delete(entity.id)
            } else {
                favoriteMoviesDao.insert(entity)
            }
        }.not()

    private suspend fun getVideos(movieId: Long) = moviesAPI.runCatching {
        getVideos(movieId, apiKey = apiKey)
    }

    private suspend fun getReviews(movieId: Long) = moviesAPI.runCatching {
        getReviews(movieId, apiKey = apiKey)
    }
}