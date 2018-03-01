package com.curtesmalteser.popularmoviesstage1.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.curtesmalteser.popularmoviesstage1.BuildConfig;
import com.curtesmalteser.popularmoviesstage1.R;
import com.curtesmalteser.popularmoviesstage1.adapter.VideosAdapter;
import com.curtesmalteser.popularmoviesstage1.utils.MoviesAPIClient;
import com.curtesmalteser.popularmoviesstage1.utils.MoviesAPIInterface;
import com.curtesmalteser.popularmoviesstage1.utils.MoviesModel;
import com.curtesmalteser.popularmoviesstage1.utils.VideosModel;
import com.google.android.youtube.player.YouTubeStandalonePlayer;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.provider.MediaStore.Video.Thumbnails.VIDEO_ID;

public class VideosFragment extends Fragment
        implements VideosAdapter.ListItemClickListener {

    private static final String TAG = VideosFragment.class.getSimpleName();

    @BindView(R.id.videosRecyclerView)
    RecyclerView mRecyclerView;

    private VideosAdapter mVideosAdapter;

    private List<VideosModel> mVideosList;

    private ConnectivityManager cm;

    public VideosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        /** TODO: 01/03/2018 fix this fragment lifecycle
         * missing fill onSaveInstanceState and recover it in onCreateView
        **/
        cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        MoviesModel model = getActivity().getIntent().getParcelableExtra(getResources().getString(R.string.string_extra));
        makeVideosQuery(model.getId());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_videos, container, false);
        ButterKnife.bind(this, view);
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        mDividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.item_decoration));
        mRecyclerView.addItemDecoration(mDividerItemDecoration);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        return view;
    }

    private void makeVideosQuery(String movieId) {
        MoviesAPIInterface apiInterface = MoviesAPIClient.getClient().create(MoviesAPIInterface.class);
        Call<VideosModel> call;

        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {

            call = apiInterface.getVideos(movieId, BuildConfig.API_KEY);
            call.enqueue(new Callback<VideosModel>() {
                @Override
                public void onResponse(Call<VideosModel> call, Response<VideosModel> response) {
                    if (response.body().getVideosModels().size() != 0) {
                        Log.d(TAG, "getMovies called");
                        Log.d(TAG, "getMovies called");
                        mVideosList = response.body().getVideosModels();
                        mVideosAdapter = new VideosAdapter(getContext(), mVideosList, VideosFragment.this);
                        mRecyclerView.setAdapter(mVideosAdapter);
                    } else {
                        Log.d(TAG, "There are no videos for this movie.");
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
    }
}
