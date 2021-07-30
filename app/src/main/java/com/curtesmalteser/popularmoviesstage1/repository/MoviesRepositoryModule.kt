package com.curtesmalteser.popularmoviesstage1.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by António 'Curtes Malteser' Bastião on 30/07/2021.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class MoviesRepositoryModule {

    @Binds
    abstract fun bindPopularMoviesRepository(repo: PopularMoviesRepository): IMoviesRepository

}