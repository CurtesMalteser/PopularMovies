package com.curtesmalteser.popularmoviesstage1.fragment

import androidx.fragment.app.viewModels
import com.curtesmalteser.popularmoviesstage1.fragment.ui.BaseMoviesFragment
import com.curtesmalteser.popularmoviesstage1.viewmodel.PopularMoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularMoviesFragment : BaseMoviesFragment() {

    override val viewModel: PopularMoviesViewModel by viewModels()

    companion object {

        fun newInstance(): PopularMoviesFragment = PopularMoviesFragment()

    }
}