package com.curtesmalteser.popularmovies.core.di

import javax.inject.Qualifier

/**
 * Created by António 'Curtes Malteser' Bastião on 30/07/2021.
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiKey

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PopularMovieProviderQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PopularMoviesRepo

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TopRatedMovieProviderQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TopRatedRepo

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FavoriteRepo

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcher