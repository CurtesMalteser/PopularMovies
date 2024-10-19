package com.curtesmalteser.popularmovies.repository.details

import com.curtesmalteser.popularmovies.core.di.ApiKey
import com.curtesmalteser.popularmovies.core.di.IoDispatcher
import com.curtesmalteser.popularmovies.database.FavoriteMoviesDao
import com.curtesmalteser.popularmovies.network.MoviesAPIInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

/**
 * Created by António Bastião on 06.11.2023
 * Refer to <a href="https://github.com/CurtesMalteser">CurtesMalteser github</a>
 */
@Module
@InstallIn(SingletonComponent::class)
class MovieDetailsRepositoryModule {

    @Provides
    @Singleton
    fun provideMovieDetailsRepository(
        @ApiKey apiKey: String,
        moviesAPI: MoviesAPIInterface,
        favoriteMoviesDao: FavoriteMoviesDao,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): IMovieDetailsRepository = MovieDetailsRepository(
        apiKey = apiKey,
        moviesAPI = moviesAPI,
        favoriteMoviesDao = favoriteMoviesDao,
        ioDispatcher = ioDispatcher,
    )

}