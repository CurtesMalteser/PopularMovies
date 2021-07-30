package com.curtesmalteser.popularmoviesstage1.fragment

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.curtesmalteser.popularmoviesstage1.R
import com.curtesmalteser.popularmoviesstage1.activity.MovieDetailsActivity
import com.curtesmalteser.popularmoviesstage1.adapter.EndlessScrollListener
import com.curtesmalteser.popularmoviesstage1.adapter.MoviesAdapter
import com.curtesmalteser.popularmoviesstage1.databinding.FragmentMoviesLayoutBinding
import com.curtesmalteser.popularmoviesstage1.utils.MoviesModel
import com.curtesmalteser.popularmoviesstage1.viewmodel.PopularMoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.util.*

@AndroidEntryPoint
class PopularMoviesFragment : Fragment(), MoviesAdapter.ListItemClickListener {

    private var mMoviesList: ArrayList<MoviesModel>? = ArrayList()
    private var stateRecyclerView: Parcelable? = null
    private var pageNumber = 1
    private var cm: ConnectivityManager? = null
    private var moviesAdapter: MoviesAdapter? = null

    private var _binding: FragmentMoviesLayoutBinding? = null
    private val binding: FragmentMoviesLayoutBinding get() = _binding!!

    private val viewModel: PopularMoviesViewModel by viewModels()

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
            viewModel.makeMoviesQuery(pageNumber)
        } else {
            mMoviesList = savedInstanceState.getParcelableArrayList(SAVED_STATE_MOVIES_LIST)
            pageNumber = savedInstanceState.getInt(PAGE_NUMBER_KEY)
            recyclerView.layoutManager?.onRestoreInstanceState(stateRecyclerView)
        }
        moviesAdapter = MoviesAdapter(context, mMoviesList, this)

        recyclerView.adapter = moviesAdapter

        recyclerView.addOnScrollListener(object : EndlessScrollListener(layoutManager) {
            override fun onLoadMore(current_page: Int) {
                viewModel.makeMoviesQuery(current_page)
                pageNumber = current_page
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
        stateRecyclerView = binding.recyclerView.layoutManager?.onSaveInstanceState()
        outState.putInt(PAGE_NUMBER_KEY, pageNumber)
        outState.putParcelableArrayList(SAVED_STATE_MOVIES_LIST, mMoviesList)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {

        private const val SAVED_STATE_MOVIES_LIST = "moviesListSaved"
        private const val PAGE_NUMBER_KEY = "pageNumber"

        fun newInstance(): PopularMoviesFragment = PopularMoviesFragment()

    }
}