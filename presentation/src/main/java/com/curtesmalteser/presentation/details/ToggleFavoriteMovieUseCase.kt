package com.curtesmalteser.presentation.details

import com.curtesmalteser.popularmovies.core.models.MovieDetails
import com.curtesmalteser.popularmovies.repository.details.IMovieDetailsRepository

/**
 * Created by António Bastião on 18.10.2024
 * Refer to <a href="https://github.com/CurtesMalteser">CurtesMalteser github</a>
 */
interface IToggleFavoriteMovieUseCase {
    suspend fun toggleFavorite(details: MovieDetails)
}

internal class ToggleFavoriteMovieUseCase(
    private val movieDetailsRepository: IMovieDetailsRepository,
) : IToggleFavoriteMovieUseCase {
    override suspend fun toggleFavorite(details: MovieDetails) = movieDetailsRepository
        .toggleFavorite(details)
}