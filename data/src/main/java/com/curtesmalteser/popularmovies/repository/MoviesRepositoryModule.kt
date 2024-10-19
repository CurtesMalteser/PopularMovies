package com.curtesmalteser.popularmovies.repository

import com.curtesmalteser.popularmovies.core.di.ApiKey
import com.curtesmalteser.popularmovies.core.di.FavoriteRepo
import com.curtesmalteser.popularmovies.core.di.IoDispatcher
import com.curtesmalteser.popularmovies.core.di.PopularMovieProviderQualifier
import com.curtesmalteser.popularmovies.core.di.PopularMoviesRepo
import com.curtesmalteser.popularmovies.core.di.TopRatedMovieProviderQualifier
import com.curtesmalteser.popularmovies.core.di.TopRatedRepo
import com.curtesmalteser.popularmovies.database.FavoriteMoviesDao
import com.curtesmalteser.popularmovies.network.IMoviesProvider
import com.curtesmalteser.popularmovies.repository.favorite.FavoriteMoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

/**
 * Created by António 'Curtes Malteser' Bastião on 30/07/2021.
 */
@Module
@InstallIn(SingletonComponent::class)
class MoviesRepositoryModule {

    @Provides
    @Singleton
    @PopularMoviesRepo
    fun bindPopularMoviesRepository(
        @ApiKey apiKey: String,
        @PopularMovieProviderQualifier moviesProvider: IMoviesProvider,
    ): IMoviesRepository = PopularMoviesRepository(apiKey, moviesProvider)

    @Provides
    @Singleton
    @TopRatedRepo
    fun bindTopRatedMoviesRepository(
        @ApiKey apiKey: String,
        @TopRatedMovieProviderQualifier moviesProvider: IMoviesProvider,
    ): IMoviesRepository = PopularMoviesRepository(apiKey, moviesProvider)

    @Provides
    @Singleton
    @FavoriteRepo
    fun bindFavoriteMoviesRepository(
        dao: FavoriteMoviesDao,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ): IMoviesRepository = FavoriteMoviesRepository(dao, ioDispatcher)
}