package com.curtesmalteser.popularmovies.core.models

/**
 * Created by António Bastião on 18.10.2023
 * Refer to <a href="https://github.com/CurtesMalteser">CurtesMalteser github</a>
 */
interface Movie {
    val id: Long
    val title: String
    val posterPath: String
}