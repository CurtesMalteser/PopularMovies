package com.curtesmalteser.popularmovies.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.curtesmalteser.popularmovies.core.models.MovieDetails

/**
 * Created by António Bastião on 14.10.2024
 * Refer to <a href="https://github.com/CurtesMalteser">CurtesMalteser github</a>
 */
@Entity(tableName = "favorite_movies")
data class MovieEntity(
    @PrimaryKey
    override val id: Long,
    override val backdropPath: String,
    override val overview: String,
    override val releaseDate: String,
    override val voteAverage: String,
    override val title: String,
    override val posterPath: String
): MovieDetails

fun MovieDetails.toEntity() = MovieEntity(
    id = id,
    title = title,
    posterPath = posterPath,
    backdropPath = backdropPath,
    overview = overview,
    releaseDate = releaseDate,
    voteAverage = voteAverage
)