package com.curtesmalteser.popularmoviesstage1.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.curtesmalteser.popularmovies.core.di.PopularMoviesRepo
import com.curtesmalteser.popularmovies.core.di.TopRatedRepo
import com.curtesmalteser.popularmovies.repository.IMoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by António 'Curtes Malteser' Bastião on 25/07/2021.
 */
@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    @PopularMoviesRepo moviesRepository: IMoviesRepository,
    savedStateHandle: SavedStateHandle
) : MoviesViewModel(moviesRepository, savedStateHandle)

@HiltViewModel
class TopRatedMoviesViewModel @Inject constructor(
    @TopRatedRepo moviesRepository: IMoviesRepository,
    savedStateHandle: SavedStateHandle
) : MoviesViewModel(moviesRepository, savedStateHandle)