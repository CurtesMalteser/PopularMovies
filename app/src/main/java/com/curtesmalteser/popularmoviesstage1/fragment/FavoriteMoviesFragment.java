package com.curtesmalteser.popularmoviesstage1.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.curtesmalteser.popularmoviesstage1.R;
import com.curtesmalteser.popularmoviesstage1.activity.MovieDetailsActivity;
import com.curtesmalteser.popularmoviesstage1.adapter.MoviesAdapter;
import com.curtesmalteser.popularmoviesstage1.db.MoviesContract;
import com.curtesmalteser.popularmoviesstage1.utils.MoviesModel;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.curtesmalteser.popularmoviesstage1.db.MoviesContract.MoviesEntry.CONTENT_URI;

public class FavoriteMoviesFragment extends Fragment
        implements MoviesAdapter.ListItemClickListener,
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = FavoriteMoviesFragment.class.getSimpleName();

    private static final int TASK_LOADER_ID = 0;

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
    public void onListItemClick(MoviesModel moviesModel) {
            Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
            intent.putExtra(getResources().getString(R.string.string_extra), moviesModel);
            startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();

        // re-queries for all tasks
        getActivity().getSupportLoaderManager().restartLoader(TASK_LOADER_ID, null, this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(true);
        View view = inflater.inflate(R.layout.fragment_movies_layout, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerView);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), getResources().getInteger(R.integer.number_of_columns)));
        moviesAdapter = new MoviesAdapter(getContext(), mMoviesList, FavoriteMoviesFragment.this);
        mRecyclerView.setAdapter(moviesAdapter);

        getActivity().getSupportLoaderManager().initLoader(TASK_LOADER_ID, null, this);
        return view;
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<Cursor>(getContext()) {

            Cursor mMoviesData = null;

            @Override
            protected void onStartLoading() {
                if (mMoviesData != null)
                    deliverResult(mMoviesData);
                else
                    forceLoad();
            }

            @Nullable
            @Override
            public Cursor loadInBackground() {
                try {
                    return getContext().getContentResolver().query(
                            CONTENT_URI,
                            null,
                            null,
                            null,
                            _ID
                    );
                } catch (Exception e) {
                    Log.d(TAG, "Failed to asynchronously load data.");
                    return null;
                }
            }

            public void deliverResult(Cursor data) {
                mMoviesData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mMoviesList.clear();
        for (int i = 0; i < data.getCount(); i++) {
            if (!data.moveToPosition(i))
                return;
            else {
                mMoviesList.add(new MoviesModel(data.getInt(data.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_NAME_ID)),
                        data.getString(data.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_NAME_VOTE_AVERAGE)),
                        data.getString(data.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_NAME_TITLE)),
                        data.getString(data.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_NAME_POSTER_PATH)),
                        data.getString(data.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_NAME_BACKDROP_PATH)),
                        data.getString(data.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_NAME_OVERVIEW)),
                        data.getString(data.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_NAME_RELEASE_DATE))));
            }
        }
        moviesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
