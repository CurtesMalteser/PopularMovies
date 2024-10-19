package com.curtesmalteser.popularmovies.network

import com.curtesmalteser.popularmovies.core.di.PopularMovieProviderQualifier
import com.curtesmalteser.popularmovies.core.di.TopRatedMovieProviderQualifier
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by António 'Curtes Malteser' Bastião on 30/07/2021.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class MoviesProviderModule {

    @Binds
    @Singleton
    @PopularMovieProviderQualifier
    abstract fun bindPopularMoviesProvider(provider: PopularMoviesProvider): IMoviesProvider

    @Binds
    @Singleton
    @TopRatedMovieProviderQualifier
    abstract fun bindTopMoviesProvider(provider: TopRatedMoviesProvider): IMoviesProvider

}