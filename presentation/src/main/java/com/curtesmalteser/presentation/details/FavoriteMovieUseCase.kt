package com.curtesmalteser.presentation.details

import com.curtesmalteser.popularmovies.core.models.MovieDetails
import com.curtesmalteser.popularmovies.repository.details.IMovieDetailsRepository

/**
 * Created by António Bastião on 18.10.2024
 * Refer to <a href="https://github.com/CurtesMalteser">CurtesMalteser github</a>
 */
interface IFavoriteMovieUseCase {
    suspend fun toggleFavorite(details: MovieDetails)
}

internal class FavoriteMovieUseCase(
    private val movieDetailsRepository: IMovieDetailsRepository,
) : IFavoriteMovieUseCase {
    override suspend fun toggleFavorite(details: MovieDetails) = movieDetailsRepository
        .toggleFavorite(details)
}