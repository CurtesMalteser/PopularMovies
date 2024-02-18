package com.curtesmalteser.popularmoviesstage1.screen.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.curtesmalteser.presentation.ISetupMovieDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by António Bastião on 21.10.2023
 * Refer to <a href="https://github.com/CurtesMalteser">CurtesMalteser github</a>
 */
@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieDetailsUseCase: ISetupMovieDetailsUseCase,
) : ViewModel() {

    fun setupMovieDetailsFor(movieId: Long) {
        viewModelScope.launch {
            movieDetailsUseCase.setupMovieDetailsFor(movieId)
        }
    }
}