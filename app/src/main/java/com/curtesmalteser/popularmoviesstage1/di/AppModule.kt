package com.curtesmalteser.popularmoviesstage1.di

import com.curtesmalteser.popularmoviesstage1.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by António 'Curtes Malteser' Bastião on 25/07/2021.
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @ApiKey
    fun provideApiKey(): String = BuildConfig.API_KEY

}