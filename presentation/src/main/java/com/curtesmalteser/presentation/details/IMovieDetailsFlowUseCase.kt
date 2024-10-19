package com.curtesmalteser.presentation.details

import com.curtesmalteser.popularmovies.repository.details.IMovieDetailsRepository
import com.curtesmalteser.popularmovies.repository.details.MovieDetailsResult
import kotlinx.coroutines.flow.Flow

/**
 * Created by António Bastião on 18.02.2024
 * Refer to <a href="https://github.com/CurtesMalteser">CurtesMalteser github</a>
 */
interface IMovieDetailsFlowUseCase {
    val movieDetailsFlow: Flow<Result<MovieDetailsResult>>
}

internal class MovieDetailsFlowUseCase(
    movieDetailsRepository: IMovieDetailsRepository,
) : IMovieDetailsFlowUseCase {
        override val movieDetailsFlow: Flow<Result<MovieDetailsResult>> = movieDetailsRepository.movieDetailsFlow
}