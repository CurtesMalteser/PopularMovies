package com.curtesmalteser.popularmoviesstage1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.curtesmalteser.popularmoviesstage1.repository.IMoviesRepository
import com.curtesmalteser.popularmoviesstage1.utils.MoviesModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by António 'Curtes Malteser' Bastião on 25/07/2021.
 */
@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    private val moviesRepository: IMoviesRepository,
) : ViewModel() {

    val moviesList : Flow<List<MoviesModel>> = moviesRepository.moviesList

    // TODO: 30/07/2021 Handle error and no connection 
    fun makeMoviesQuery(page: Int) {
        viewModelScope.launch {
            moviesRepository.fetchMovies(page)
        }
    }

}