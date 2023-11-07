package com.curtesmalteser.popularmovies.core.models

/**
 * Created by António Bastião on 06.11.2023
 * Refer to <a href="https://github.com/CurtesMalteser">CurtesMalteser github</a>
 */
interface MovieDetails: Movie {
    val backdropPath: String
    val overview: String
    val releaseDate: String
}