package com.curtesmalteser.popularmoviesstage1.screen.details

import androidx.lifecycle.ViewModel
import com.curtesmalteser.presentation.IMovieDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by António Bastião on 21.10.2023
 * Refer to <a href="https://github.com/CurtesMalteser">CurtesMalteser github</a>
 */
@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieDetailsUseCase: IMovieDetailsUseCase,
): ViewModel(){

}