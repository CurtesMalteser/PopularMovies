package com.curtesmalteser.popularmoviesstage1.viewmodel

import com.curtesmalteser.popularmovies.core.models.MovieDetails
import kotlinx.coroutines.flow.StateFlow

/**
* Created by António Bastião on 19.10.2024
* Refer to <a href="https://github.com/CurtesMalteser">CurtesMalteser github</a>
*/
interface MoviesPresenter {
    val moviesList: StateFlow<List<MovieDetails>>
}