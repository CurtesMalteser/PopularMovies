package com.curtesmalteser.presentation.favorite

import com.curtesmalteser.popularmovies.core.di.FavoriteRepo
import com.curtesmalteser.popularmovies.repository.IMoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * Created by António Bastião on 19.10.2024
 * Refer to <a href="https://github.com/CurtesMalteser">CurtesMalteser github</a>
 */
@Module
@InstallIn(ViewModelComponent::class)
class FavoriteMoviesUseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideFavoriteMovieUseCase(
        @FavoriteRepo movieRepository: IMoviesRepository,
    ): IFetchFavoriteMovieUseCase = FetchFavoriteMovieUseCase(movieRepository)
}