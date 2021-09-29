package com.curtesmalteser.popularmoviesstage1.fragment

import androidx.fragment.app.viewModels
import com.curtesmalteser.popularmoviesstage1.fragment.ui.BaseMoviesFragment
import com.curtesmalteser.popularmoviesstage1.viewmodel.TopRatedMoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopRatedMoviesFragment : BaseMoviesFragment() {

    override val viewModel: TopRatedMoviesViewModel by viewModels()

    companion object {

        fun newInstance(): TopRatedMoviesFragment = TopRatedMoviesFragment()

    }
}