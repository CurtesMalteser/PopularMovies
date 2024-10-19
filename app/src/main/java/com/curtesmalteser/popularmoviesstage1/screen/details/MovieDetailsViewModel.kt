package com.curtesmalteser.popularmoviesstage1.screen.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.curtesmalteser.popularmovies.repository.details.MovieDetailsResult
import com.curtesmalteser.presentation.IMovieDetailsFlowUseCase
import com.curtesmalteser.presentation.ISetupMovieDetailsUseCase
import com.curtesmalteser.presentation.details.IFavoriteMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by António Bastião on 21.10.2023
 * Refer to <a href="https://github.com/CurtesMalteser">CurtesMalteser github</a>
 */
@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieDetailsUseCase: ISetupMovieDetailsUseCase,
    movieDetailsFlowUseCase: IMovieDetailsFlowUseCase,
    private val favoriteMovieUseCase: IFavoriteMovieUseCase,
) : ViewModel() {

    private val _movieDetailsFlow = MutableStateFlow<Result<MovieDetailsResult>>(
        Result.success(MovieDetailsResult.NoDetails)
    )
    val movieDetailsFlow: StateFlow<Result<MovieDetailsResult>> = _movieDetailsFlow

    init {
        viewModelScope.launch {
            movieDetailsFlowUseCase.movieDetailsFlow.collect {
                _movieDetailsFlow.value = it
            }
        }
    }

    fun setupMovieDetailsFor(movieId: Long) {
        viewModelScope.launch {
            movieDetailsUseCase.setupMovieDetailsFor(movieId)
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            // get if is Result.Success<MovieDetailsResult> and get the movieDetails
            _movieDetailsFlow.filter { it.isSuccess }
                .map { it.getOrNull() as? MovieDetailsResult.MovieDetailsData }
                .firstOrNull()
                ?.let { favoriteMovieUseCase.toggleFavorite(it) }
        }
    }
}