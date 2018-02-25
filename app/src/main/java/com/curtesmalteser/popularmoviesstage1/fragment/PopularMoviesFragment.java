package com.curtesmalteser.popularmoviesstage1.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PopularMoviesFragment extends Fragment
        implements MoviesAdapter.ListItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String RECYCLER_VIEW_STATE = "recyclerViewState";
    private static final String SAVED_STATE_MOVIES_LIST = "moviesListSaved";

    private OnFragmentInteractionListener mListener;

    private static final String PREFERENCES_NAME = "movies_preferences";
    private final String SELECTION = "selection";

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private MoviesAdapter moviesAdapter;

    private List<MoviesModel> mMoviesList;

    private ConnectivityManager cm;


    public PopularMoviesFragment() {
        // Required empty public constructor
    }

    public static PopularMoviesFragment newInstance() {
        PopularMoviesFragment fragment = new PopularMoviesFragment();
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
        cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        if(savedInstanceState != null) {
            if (savedInstanceState.containsKey(RECYCLER_VIEW_STATE)){
                String state = savedInstanceState.getString(RECYCLER_VIEW_STATE);
                Log.d("AJDB", state);
            }
        } else
        makeMoviesQuery();
    }

    private void makeMoviesQuery() {
        MoviesAPIInterface apiInterface = MoviesAPIClient.getClient().create(MoviesAPIInterface.class);
        Call<MoviesModel> call;

        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {

            call = apiInterface.getPopularMovies(BuildConfig.API_KEY);
            call.enqueue(new Callback<MoviesModel>() {
                @Override
                public void onResponse(Call<MoviesModel> call, Response<MoviesModel> response) {
                    mMoviesList = response.body().getMoviesModels();
                    moviesAdapter = new MoviesAdapter(getContext(), mMoviesList, PopularMoviesFragment.this);
                    mRecyclerView.setAdapter(moviesAdapter);
                }

                @Override
                public void onFailure(Call<MoviesModel> call, Throwable t) {
                    Log.d("AJDB", "onFailure:" + t.getMessage().toString());
                }
            });
        } else
            Toast.makeText(getContext(), R.string.check_internet_connection, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onListItemClick(MoviesModel moviesModel) {
        Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
        intent.putExtra(getResources().getString(R.string.string_extra), moviesModel);
        onButtonPressed(moviesModel);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies_layout, container, false);
        ButterKnife.bind(this, view);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), getResources().getInteger(R.integer.number_of_columns)));

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(MoviesModel moviesModel) {
        if (mListener != null) {
            mListener.onFragmentInteraction(moviesModel);
        }
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(MoviesModel uri);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(RECYCLER_VIEW_STATE, "onSaveInstanceState!");
    }
}
