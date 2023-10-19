package com.curtesmalteser.popularmoviesstage1.repository

import com.curtesmalteser.popularmovies.network.IMoviesProvider
import com.curtesmalteser.popularmovies.network.PopularMoviesProvider
import com.curtesmalteser.popularmovies.network.TopRatedMoviesProvider
import com.curtesmalteser.popularmoviesstage1.di.PopularMovieProviderQualifier
import com.curtesmalteser.popularmoviesstage1.di.TopRatedMovieProviderQualifier
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