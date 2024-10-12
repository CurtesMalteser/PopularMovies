package com.curtesmalteser.popularmovies.repository

import com.curtesmalteser.popularmovies.network.IMoviesProvider
import com.curtesmalteser.popularmovies.core.di.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
    ): IMoviesRepository = PopularMoviesRepository(
        apiKey,
        moviesProvider
    )

    @Provides
    @Singleton
    @TopRatedRepo
    fun bindTopRatedMoviesRepository(
        @ApiKey apiKey: String,
        @TopRatedMovieProviderQualifier moviesProvider: IMoviesProvider,
    ): IMoviesRepository = PopularMoviesRepository(
        apiKey,
        moviesProvider
    )

}