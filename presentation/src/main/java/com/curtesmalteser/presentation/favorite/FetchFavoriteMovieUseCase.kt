package com.curtesmalteser.presentation.favorite

import com.curtesmalteser.popularmovies.core.models.MovieDetails
import com.curtesmalteser.popularmovies.repository.IMoviesRepository
import kotlinx.coroutines.flow.Flow

interface IFetchFavoriteMovieUseCase {
    val moviesList: Flow<List<MovieDetails>>
}

/**
 * Created by António Bastião on 19.10.2024
 * Refer to <a href="https://github.com/CurtesMalteser">CurtesMalteser github</a>
 */
class FetchFavoriteMovieUseCase(repository: IMoviesRepository) : IFetchFavoriteMovieUseCase {
    override val moviesList = repository.moviesList
}