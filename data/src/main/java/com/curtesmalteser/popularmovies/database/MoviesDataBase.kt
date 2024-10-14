package com.curtesmalteser.popularmovies.database

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Created by António Bastião on 13.10.2024
 * Refer to <a href="https://github.com/CurtesMalteser">CurtesMalteser github</a>
 */
@Database(entities = [MovieEntity::class], version = 1)
abstract class MoviesDataBase: RoomDatabase() {
    //abstract fun favoriteMoviesDao(): FavoriteMoviesDao
}