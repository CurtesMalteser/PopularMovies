package com.curtesmalteser.popularmoviesstage1.viewmodel

import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.curtesmalteser.popularmovies.data.MovieData
import com.curtesmalteser.popularmovies.repository.IMoviesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/**
 * Created by António Bastião on 29.09.21
 * Refer to <a href="https://github.com/CurtesMalteser">CurtesMalteser github</a>
 */
open class MoviesViewModel(
    private val moviesRepository: IMoviesRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _moviesList = MutableStateFlow<List<MovieData>>(emptyList())
    val moviesList: MutableStateFlow<List<MovieData>> = _moviesList

    var pageNumber: Int
        get() = savedStateHandle.get<Int>(PAGE_NUMBER_KEY) ?: 1
        set(value) = savedStateHandle.set(PAGE_NUMBER_KEY, value)

    private var _stateRecyclerView: Parcelable? = null
    var stateRecyclerView: Parcelable?
        get() = _stateRecyclerView
        set(value) {
            _stateRecyclerView = value
        }


    init {
        makeMoviesQuery(pageNumber)
        viewModelScope.launch {
            moviesRepository.moviesList.collect(_moviesList::emit)
        }
    }

    // TODO: 30/07/2021 Handle error and no connection
    fun makeMoviesQuery(page: Int) {
        viewModelScope.launch {
            moviesRepository.fetchMovies(page).fold(
                onSuccess = {
                    Log.d("makeMoviesQuery", it.moviesModels.first().title)
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