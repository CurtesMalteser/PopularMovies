package com.curtesmalteser.popularmoviesstage1.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.curtesmalteser.popularmoviesstage1.BuildConfig;
import com.curtesmalteser.popularmoviesstage1.R;
import com.curtesmalteser.popularmoviesstage1.activity.MovieDetailsActivity;
import com.curtesmalteser.popularmoviesstage1.adapter.MoviesAdapter;
import com.curtesmalteser.popularmoviesstage1.utils.MoviesAPIClient;
import com.curtesmalteser.popularmoviesstage1.utils.MoviesAPIInterface;
import com.curtesmalteser.popularmoviesstage1.utils.MoviesModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TopRatedMoviesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TopRatedMoviesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopRatedMoviesFragment extends Fragment
        implements MoviesAdapter.ListItemClickListener {

    private static final String TAG = TopRatedMoviesFragment.class.getSimpleName();

    private OnFragmentInteractionListener mListener;

    private static final String PREFERENCES_NAME = "movies_preferences";
    private final String SELECTION = "selection";

    private MoviesAdapter moviesAdapter;

    private ConnectivityManager cm;
    private static final String SAVED_STATE_MOVIES_LIST = "moviesListSaved";

    private ArrayList<MoviesModel> mMoviesList;

    private Parcelable stateRecyclerView;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    public TopRatedMoviesFragment() {
        // Required empty public constructor
    }

    public static TopRatedMoviesFragment newInstance() {
        TopRatedMoviesFragment fragment = new TopRatedMoviesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies_layout, container, false);
        ButterKnife.bind(this, view);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), getResources().getInteger(R.integer.number_of_columns)));
        if (savedInstanceState == null) {
            makeMoviesQuery();
        } else {
            mMoviesList = savedInstanceState.getParcelableArrayList(SAVED_STATE_MOVIES_LIST);
            moviesAdapter = new MoviesAdapter(getContext(), mMoviesList, TopRatedMoviesFragment.this);
            mRecyclerView.setAdapter(moviesAdapter);
            mRecyclerView.getLayoutManager().onRestoreInstanceState(stateRecyclerView);
        }

        return view;
    }

    private void makeMoviesQuery() {
        MoviesAPIInterface apiInterface = MoviesAPIClient.getClient().create(MoviesAPIInterface.class);
        Call<MoviesModel> call;

        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {

            call = apiInterface.getTopRated(BuildConfig.API_KEY);
            call.enqueue(new Callback<MoviesModel>() {
                @Override
                public void onResponse(Call<MoviesModel> call, Response<MoviesModel> response) {
                    mMoviesList = response.body().getMoviesModels();
                    moviesAdapter = new MoviesAdapter(getContext(), mMoviesList, TopRatedMoviesFragment.this);
                    mRecyclerView.setAdapter(moviesAdapter);
                }

                @Override
                public void onFailure(Call<MoviesModel> call, Throwable t) {
                    Log.d(TAG, "onFailure:" + t.getMessage().toString());
                }
            });
        } else
            Toast.makeText(getContext(), R.string.check_internet_connection, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onListItemClick(MoviesModel moviesModel) {
        Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
        intent.putExtra(getResources().getString(R.string.string_extra), moviesModel);
        startActivity(intent);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnOverviewFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(MoviesModel moviesModel);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        stateRecyclerView = mRecyclerView.getLayoutManager().onSaveInstanceState();
        outState.putParcelableArrayList(SAVED_STATE_MOVIES_LIST, mMoviesList);
    }
}
