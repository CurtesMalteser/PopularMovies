package com.curtesmalteser.presentation

import com.curtesmalteser.popularmovies.core.di.PopularMoviesRepo
import com.curtesmalteser.popularmovies.core.di.TopRatedRepo
import com.curtesmalteser.popularmovies.repository.IMoviesRepository
import com.curtesmalteser.popularmovies.repository.details.IMovieDetailsRepository
import com.curtesmalteser.presentation.details.FavoriteMovieUseCase
import com.curtesmalteser.presentation.details.IFavoriteMovieUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * Created by António Bastião on 22.10.2023
 * Refer to <a href="https://github.com/CurtesMalteser">CurtesMalteser github</a>
 */
@Module
@InstallIn(ViewModelComponent::class)
class MovieDetailsUseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideMovieDetailsUseCase(
        @PopularMoviesRepo popularMoviesRepository: IMoviesRepository,
        @TopRatedRepo topMoviesRepository: IMoviesRepository,
        movieDetailsRepository: IMovieDetailsRepository,
    ): ISetupMovieDetailsUseCase = SetupMovieDetailsUseCase(
        popularMoviesRepository = popularMoviesRepository,
        topMoviesRepository = topMoviesRepository,
        movieDetailsRepository = movieDetailsRepository,
    )


    @Provides
    @ViewModelScoped
    fun provideMovieDetailsFlowUseCase(
        movieDetailsRepository: IMovieDetailsRepository,
    ): IMovieDetailsFlowUseCase = MovieDetailsFlowUseCase(movieDetailsRepository)


    @Provides
    @ViewModelScoped
    fun provideFavoriteMovieUseCase(
        movieDetailsRepository: IMovieDetailsRepository,
    ): IFavoriteMovieUseCase = FavoriteMovieUseCase(movieDetailsRepository)
}