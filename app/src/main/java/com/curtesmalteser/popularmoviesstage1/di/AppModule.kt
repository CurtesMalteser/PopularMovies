package com.curtesmalteser.popularmoviesstage1.di

import com.curtesmalteser.popularmovies.core.di.ApiKey
import com.curtesmalteser.popularmovies.core.di.IoDispatcher
import com.curtesmalteser.popularmoviesstage1.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Created by António 'Curtes Malteser' Bastião on 25/07/2021.
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @ApiKey
    fun provideApiKey(): String = BuildConfig.API_KEY

    @Provides
    @IoDispatcher
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

}