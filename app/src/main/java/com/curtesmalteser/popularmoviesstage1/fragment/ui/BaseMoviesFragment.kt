package com.curtesmalteser.popularmoviesstage1.fragment.ui

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.curtesmalteser.popularmoviesstage1.R
import com.curtesmalteser.popularmoviesstage1.activity.MovieDetailsActivity
import com.curtesmalteser.popularmoviesstage1.adapter.EndlessScrollListener
import com.curtesmalteser.popularmoviesstage1.adapter.MoviesAdapter
import com.curtesmalteser.popularmoviesstage1.databinding.FragmentMoviesLayoutBinding
import com.curtesmalteser.popularmoviesstage1.utils.MoviesModel
import com.curtesmalteser.popularmoviesstage1.viewmodel.MoviesViewModel
import kotlinx.coroutines.flow.collect

/**
 * Created by António Bastião on 29.09.21
 * Refer to <a href="https://github.com/CurtesMalteser">CurtesMalteser github</a>
 */
abstract class BaseMoviesFragment : Fragment(), MoviesAdapter.ListItemClickListener {

    private var mMoviesList: ArrayList<MoviesModel>? = ArrayList()

    private var cm: ConnectivityManager? = null
    private var moviesAdapter: MoviesAdapter? = null

    private var _binding: FragmentMoviesLayoutBinding? = null
    private val binding: FragmentMoviesLayoutBinding get() = _binding!!

    abstract val viewModel: MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cm = requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesLayoutBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.recyclerView
        recyclerView.setHasFixedSize(true)
        val layoutManager =
            GridLayoutManager(context, resources.getInteger(R.integer.number_of_columns))
        recyclerView.layoutManager = layoutManager

        if (savedInstanceState == null) {
            viewModel.makeMoviesQuery(viewModel.pageNumber)
        } else {
            mMoviesList = savedInstanceState.getParcelableArrayList(SAVED_STATE_MOVIES_LIST)
            recyclerView.layoutManager?.onRestoreInstanceState(viewModel.stateRecyclerView)
        }
        moviesAdapter = MoviesAdapter(context, mMoviesList, this)

        recyclerView.adapter = moviesAdapter

        recyclerView.addOnScrollListener(object : EndlessScrollListener(layoutManager) {
            override fun onLoadMore(current_page: Int) {
                viewModel.makeMoviesQuery(current_page)
                viewModel.pageNumber = current_page
            }
        })

        lifecycleScope.launchWhenResumed {
            viewModel.moviesList.collect { moviesModel ->
                mMoviesList!!.addAll(moviesModel)
                moviesAdapter!!.notifyItemInserted(mMoviesList!!.size + 1)
            }
        }

    }

    override fun onListItemClick(moviesModel: MoviesModel) {
        val intent = Intent(activity, MovieDetailsActivity::class.java)
        intent.putExtra(resources.getString(R.string.string_extra), moviesModel)
        startActivity(intent)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.stateRecyclerView = binding.recyclerView.layoutManager?.onSaveInstanceState()
        outState.putParcelableArrayList(SAVED_STATE_MOVIES_LIST, mMoviesList)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        private const val SAVED_STATE_MOVIES_LIST = "moviesListSaved"
    }
}