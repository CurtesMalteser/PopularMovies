package com.curtesmalteser.popularmoviesstage1.viewmodel.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.curtesmalteser.popularmovies.core.models.MovieDetails
import com.curtesmalteser.popularmoviesstage1.viewmodel.MoviesPresenter
import com.curtesmalteser.presentation.favorite.IFetchFavoriteMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by António Bastião on 19.10.2024
 * Refer to <a href="https://github.com/CurtesMalteser">CurtesMalteser github</a>
 */
@HiltViewModel
class FavoriteMoviesViewModel @Inject constructor(
    private val useCase: IFetchFavoriteMovieUseCase,
) : MoviesPresenter, ViewModel() {

    private val _moviesList: MutableStateFlow<List<MovieDetails>> = MutableStateFlow(emptyList())
    override val moviesList: StateFlow<List<MovieDetails>>
        get() = _moviesList

    init {
        viewModelScope.launch {
            useCase.moviesList.collect(_moviesList::emit)
        }
    }
}