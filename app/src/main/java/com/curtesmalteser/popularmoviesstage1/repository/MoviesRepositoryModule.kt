package com.curtesmalteser.popularmoviesstage1.repository

import com.curtesmalteser.popularmoviesstage1.di.*
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