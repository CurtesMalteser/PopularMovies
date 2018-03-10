package com.curtesmalteser.popularmoviesstage1.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import com.curtesmalteser.popularmoviesstage1.R;
import com.curtesmalteser.popularmoviesstage1.activity.MovieDetailsActivity;
import com.curtesmalteser.popularmoviesstage1.adapter.MoviesAdapter;
import com.curtesmalteser.popularmoviesstage1.db.MoviesContract;
import com.curtesmalteser.popularmoviesstage1.db.MoviesDbHelper;
import com.curtesmalteser.popularmoviesstage1.utils.MoviesModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FavoriteMoviesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FavoriteMoviesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteMoviesFragment extends Fragment
        implements MoviesAdapter.ListItemClickListener {

    private OnFragmentInteractionListener mListener;

    private static final String PREFERENCES_NAME = "movies_preferences";
    private final String SELECTION = "selection";

    private SQLiteDatabase mDb;


    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private MoviesAdapter moviesAdapter;

    private ArrayList<MoviesModel> mMoviesList = new ArrayList<>();

    private Parcelable stateRecyclerView;

    public FavoriteMoviesFragment() {
        // Required empty public constructor
    }

    public static FavoriteMoviesFragment newInstance() {
        FavoriteMoviesFragment fragment = new FavoriteMoviesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        // TODO: 10/03/2018 remove SQLdbHelper and replace with ContenProvider
        MoviesDbHelper dbHelper = new MoviesDbHelper(getContext());
        mDb = dbHelper.getReadableDatabase();
        Cursor cursor = getAllGuests();

        for (int i = 0; i < cursor.getCount(); i++) {
            if (!cursor.moveToPosition(i))
                return;
            else {
                mMoviesList.add(new MoviesModel(cursor.getInt(cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_NAME_ID)),
                        cursor.getString(cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_NAME_VOTE_AVERAGE)),
                        cursor.getString(cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_NAME_TITLE)),
                        cursor.getString(cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_NAME_POSTER_PATH)),
                        cursor.getString(cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_NAME_BACKDROP_PATH)),
                        cursor.getString(cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_NAME_OVERVIEW)),
                        cursor.getString(cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_NAME_RELEASE_DATE))));

                Log.d("AJDB", "Poster: " + mMoviesList.get(i).getTitle());
            }
        }
        cursor.close();
    }

    private Cursor getAllGuests() {

        return mDb.query(
                MoviesContract.MoviesEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                MoviesContract.MoviesEntry._ID
        );
    }


    @Override
    public void onListItemClick(MoviesModel moviesModel) {
        if (mListener != null) {
            mListener.onFragmentInteraction(moviesModel);
            Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
            intent.putExtra(getResources().getString(R.string.string_extra), moviesModel);
            startActivity(intent);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies_layout, container, false);
        ButterKnife.bind(this, view);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), getResources().getInteger(R.integer.number_of_columns)));
        //if (savedInstanceState == null) {
           // makeMoviesQuery();
       // } else {
          //  mMoviesList = savedInstanceState.getParcelableArrayList(SAVED_STATE_MOVIES_LIST);
            Log.d("AJDB", "mMoviesList: " + mMoviesList.size());
            moviesAdapter = new MoviesAdapter(getContext(), mMoviesList, FavoriteMoviesFragment.this);
            mRecyclerView.setAdapter(moviesAdapter);
            //mRecyclerView.getLayoutManager().onRestoreInstanceState(stateRecyclerView);
      //  }

        return view;
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
}
