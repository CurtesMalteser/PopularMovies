package com.curtesmalteser.popularmovies.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by António Bastião on 14.10.2024
 * Refer to <a href="https://github.com/CurtesMalteser">CurtesMalteser github</a>
 */
@Module
@InstallIn(SingletonComponent::class)
class MoviesDatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): MoviesDataBase = Room
        .databaseBuilder(context, MoviesDataBase::class.java, "database-movies").build()

    @Provides
    @Singleton
    fun provideFavoriteMoviesDao(db: MoviesDataBase): FavoriteMoviesDao = db.favoriteMoviesDao()

}