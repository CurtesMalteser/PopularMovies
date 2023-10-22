package com.curtesmalteser.presentation

import com.curtesmalteser.popularmovies.core.di.PopularMoviesRepo
import com.curtesmalteser.popularmovies.core.di.TopRatedRepo
import com.curtesmalteser.popularmovies.repository.IMoviesRepository

/**
 * Created by António Bastião on 22.10.2023
 * Refer to <a href="https://github.com/CurtesMalteser">CurtesMalteser github</a>
 */
interface IMovieDetailsUseCase {

}

class MovieDetailsUseCase(
    @PopularMoviesRepo popularMoviesRepository: IMoviesRepository,
    @TopRatedRepo topMoviesRepository: IMoviesRepository,
) : IMovieDetailsUseCase {

}