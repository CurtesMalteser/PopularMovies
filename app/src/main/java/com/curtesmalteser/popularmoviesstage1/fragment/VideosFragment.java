package com.curtesmalteser.popularmoviesstage1.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.curtesmalteser.popularmoviesstage1.BuildConfig;
import com.curtesmalteser.popularmoviesstage1.R;
import com.curtesmalteser.popularmoviesstage1.adapter.VideosAdapter;
import com.curtesmalteser.popularmoviesstage1.utils.MoviesAPIClient;
import com.curtesmalteser.popularmoviesstage1.utils.MoviesAPIInterface;
import com.curtesmalteser.popularmoviesstage1.utils.MoviesModel;
import com.curtesmalteser.popularmoviesstage1.utils.VideosModel;
import com.google.android.youtube.player.YouTubeStandalonePlayer;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideosFragment extends Fragment
        implements VideosAdapter.ListItemClickListener {

    private static final String TAG = VideosFragment.class.getSimpleName();

    private static final String SAVED_STATE_VIDEOS_LIST = "moviesListSaved";

    private VideosAdapter mVideosAdapter;

    private ArrayList<VideosModel> mVideosList;

    private Parcelable stateRecyclerView;

    private ConnectivityManager cm;

    @BindView(R.id.videosRecyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.reconnectButton)
    ImageButton reconnectButton;

    @BindView(R.id.noMoviesButton)
    ImageView noMoviesButton;

    public VideosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_videos, container, false);
        ButterKnife.bind(this, view);

        MoviesModel model = getActivity().getIntent().getParcelableExtra(getResources().getString(R.string.string_extra));

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        mDividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.item_decoration));
        mRecyclerView.addItemDecoration(mDividerItemDecoration);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            reconnectButton.setVisibility(View.GONE);
            noMoviesButton.setVisibility(View.GONE);

            if (savedInstanceState == null) {
                makeVideosQuery(model.getId());
            } else {
                mVideosList = savedInstanceState.getParcelableArrayList(SAVED_STATE_VIDEOS_LIST);
                if (mVideosList != null) {
                    mVideosAdapter = new VideosAdapter(getContext(), mVideosList, VideosFragment.this);
                    mRecyclerView.setAdapter(mVideosAdapter);
                    mRecyclerView.getLayoutManager().onRestoreInstanceState(stateRecyclerView);
                }
            }
        } else {
            reconnectButton.setVisibility(View.VISIBLE);
            noMoviesButton.setVisibility(View.GONE);
        }
        reconnectButton.setOnClickListener(v -> {
            makeVideosQuery(model.getId());
        });

        return view;
    }

    private void makeVideosQuery(int movieId) {
        MoviesAPIInterface apiInterface = MoviesAPIClient.getClient().create(MoviesAPIInterface.class);
        Call<VideosModel> call;

        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {

            call = apiInterface.getVideos(String.valueOf(movieId), BuildConfig.API_KEY);
            call.enqueue(new Callback<VideosModel>() {
                @Override
                public void onResponse(Call<VideosModel> call, Response<VideosModel> response) {
                    if (response.body().getVideosModels().size() != 0) {

                        reconnectButton.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);

                        mVideosList = response.body().getVideosModels();
                        mVideosAdapter = new VideosAdapter(getContext(), mVideosList, VideosFragment.this);
                        mRecyclerView.setAdapter(mVideosAdapter);

                    } else {
                        noMoviesButton.setVisibility(View.VISIBLE);
                        reconnectButton.setVisibility(View.GONE);

                   }
                }

                @Override
                public void onFailure(Call<VideosModel> call, Throwable t) {
                    Log.d(TAG, "onFailure:" + t.getMessage().toString());
                }
            });
        } else
            Toast.makeText(getContext(), R.string.check_internet_connection, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onListItemClick(VideosModel videosModel) {
        Intent intent = YouTubeStandalonePlayer.createVideoIntent(getActivity(), BuildConfig.YOUTUBE_KEY, videosModel.getKey());
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mRecyclerView != null && mVideosList != null) {
            stateRecyclerView = mRecyclerView.getLayoutManager().onSaveInstanceState();
            outState.putParcelableArrayList(SAVED_STATE_VIDEOS_LIST, mVideosList);
        }
    }
}
