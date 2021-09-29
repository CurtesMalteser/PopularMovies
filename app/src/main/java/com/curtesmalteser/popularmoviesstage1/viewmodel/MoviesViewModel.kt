package com.curtesmalteser.popularmoviesstage1.viewmodel

import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.curtesmalteser.popularmoviesstage1.repository.IMoviesRepository
import com.curtesmalteser.popularmoviesstage1.utils.MoviesModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * Created by António Bastião on 29.09.21
 * Refer to <a href="https://github.com/CurtesMalteser">CurtesMalteser github</a>
 */
open class MoviesViewModel(
    private val moviesRepository: IMoviesRepository,
    private val savedStateHandle: SavedStateHandle,
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
            moviesRepository.fetchMovies(page).fold(
                onSuccess = {
                    Log.d("makeMoviesQuery", it.toString())
                }, onFailure = {
                    Log.e("makeMoviesQuery", it.toString())
                }
            )
        }
    }

    companion object {

        private const val PAGE_NUMBER_KEY = "pageNumber"

    }

}