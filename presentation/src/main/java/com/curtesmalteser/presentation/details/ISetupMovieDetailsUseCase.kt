package com.curtesmalteser.presentation.details

import com.curtesmalteser.popularmovies.core.models.MovieDetails
import com.curtesmalteser.popularmovies.repository.IMoviesRepository
import com.curtesmalteser.popularmovies.repository.details.IMovieDetailsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge

/**
 * Created by António Bastião on 22.10.2023
 * Refer to <a href="https://github.com/CurtesMalteser">CurtesMalteser github</a>
 */
interface ISetupMovieDetailsUseCase {
    suspend fun setupMovieDetailsFor(movieId: Long)
}

internal class SetupMovieDetailsUseCase(
    private val popularMoviesRepository: IMoviesRepository,
    private val topMoviesRepository: IMoviesRepository,
    private val favoriteMoviesRepository: IMoviesRepository,
    private val movieDetailsRepository: IMovieDetailsRepository,
) : ISetupMovieDetailsUseCase {

    // TODO: gracefully handle the case when the movie is not found
    //  and trigger navigation to the previous screen
    override suspend fun setupMovieDetailsFor(movieId: Long) = merge(
        popularMoviesRepository.moviesList,
        topMoviesRepository.moviesList,
        favoriteMoviesRepository.moviesList,
    ).map { moviesList -> moviesList.firstOrNull { movie -> movie.id == movieId } }
        .first { it is MovieDetails }.let { movieDetailsRepository.setupMovie(it) }
}