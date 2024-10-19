package com.curtesmalteser.presentation.details

import com.curtesmalteser.popularmovies.core.di.FavoriteRepo
import com.curtesmalteser.popularmovies.core.di.PopularMoviesRepo
import com.curtesmalteser.popularmovies.core.di.TopRatedRepo
import com.curtesmalteser.popularmovies.data.MovieData
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
    @PopularMoviesRepo private val popularMoviesRepository: IMoviesRepository,
    @TopRatedRepo private val topMoviesRepository: IMoviesRepository,
    @FavoriteRepo private val favoriteMoviesRepository: IMoviesRepository,
    private val movieDetailsRepository: IMovieDetailsRepository,
) : ISetupMovieDetailsUseCase {

    // TODO: gracefully handle the case when the movie is not found
    //  and trigger navigation to the previous screen
    override suspend fun setupMovieDetailsFor(movieId: Long) = merge(
            popularMoviesRepository.moviesList,
            topMoviesRepository.moviesList,
            favoriteMoviesRepository.moviesList,
        ).map { moviesData -> moviesData.firstOrNull { movieData -> movieData.id == movieId } }
        .first { it is MovieData }.let { movieDetailsRepository.setupMovie(it) }
}