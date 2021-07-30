package com.curtesmalteser.popularmoviesstage1.viewmodel

import androidx.lifecycle.ViewModel
import com.curtesmalteser.popularmoviesstage1.repository.IMoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by António 'Curtes Malteser' Bastião on 25/07/2021.
 */
@HiltViewModel
class PopularMoviesViewModel @Inject constructor(moviesRepository: IMoviesRepository): ViewModel() {

}