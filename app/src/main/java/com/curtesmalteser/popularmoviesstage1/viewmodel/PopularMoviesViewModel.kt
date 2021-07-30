package com.curtesmalteser.popularmoviesstage1.viewmodel

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
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
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val moviesList: Flow<List<MoviesModel>> = moviesRepository.moviesList

    var pageNumber: Int
        get() = savedStateHandle.get<Int>(PAGE_NUMBER_KEY) ?: 1
        set(value) = savedStateHandle.set(PAGE_NUMBER_KEY, value)

    private var _stateRecyclerView: Parcelable? = null
    var stateRecyclerView: Parcelable?
        get() = _stateRecyclerView
        set(value) {
            _stateRecyclerView = value
        }


    // TODO: 30/07/2021 Handle error and no connection
    fun makeMoviesQuery(page: Int) {
        viewModelScope.launch {
            moviesRepository.fetchMovies(page)
        }
    }

    companion object {

        private const val SAVED_STATE_MOVIES_LIST = "moviesListSaved"
        private const val PAGE_NUMBER_KEY = "pageNumber"

    }

}