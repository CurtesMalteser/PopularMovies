package com.curtesmalteser.popularmoviesstage1.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.curtesmalteser.popularmovies.data.ReviewsModelData;
import com.curtesmalteser.popularmovies.network.MoviesAPIInterface;
import com.curtesmalteser.popularmoviesstage1.BuildConfig;
import com.curtesmalteser.popularmoviesstage1.R;
import com.curtesmalteser.popularmoviesstage1.adapter.ReviewsAdapter;
import com.curtesmalteser.popularmoviesstage1.utils.MoviesAPIClient;
import com.curtesmalteser.popularmoviesstage1.utils.MoviesModel;
import com.curtesmalteser.popularmoviesstage1.utils.ReviewsModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewsFragment extends Fragment
        implements ReviewsAdapter.ListItemClickListener {

    private static final String TAG = ReviewsFragment.class.getSimpleName();

    private static final String SAVED_STATE_REVIEWS_LIST = "reviewsListSaved";

    private ReviewsAdapter mReviewsAdapter;

    private ArrayList<ReviewsModel> mReviewsList;

    private Parcelable stateRecyclerView;

    private ConnectivityManager cm;

    RecyclerView mRecyclerView;

    ImageButton reconnectButton;

    ImageView noReviewsImage;

    public ReviewsFragment() {
        // Required empty public constructor
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
        View view = inflater.inflate(R.layout.fragment_reviews, container, false);

        mRecyclerView = view.findViewById(R.id.reviewsRecyclerView);
                reconnectButton = view.findViewById(R.id.reconnectButton);
        noReviewsImage = view.findViewById(R.id.noReviewsImage);

        MoviesModel model = getActivity().getIntent().getParcelableExtra(getResources().getString(R.string.string_extra));

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        mDividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.item_decoration));
        mRecyclerView.addItemDecoration(mDividerItemDecoration);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            reconnectButton.setVisibility(View.GONE);
            noReviewsImage.setVisibility(View.GONE);


            if (savedInstanceState == null) {
                makeReviewsQuery(model.getId());
            } else {
                mReviewsList = savedInstanceState.getParcelableArrayList(SAVED_STATE_REVIEWS_LIST);
                if (mReviewsList != null) {
                    mReviewsAdapter = new ReviewsAdapter(mReviewsList, ReviewsFragment.this);
                    mRecyclerView.setAdapter(mReviewsAdapter);
                    mRecyclerView.getLayoutManager().onRestoreInstanceState(stateRecyclerView);
                } else {
                    noReviewsImage.setVisibility(View.VISIBLE);
                }
            }

        } else {
            reconnectButton.setVisibility(View.VISIBLE);
            noReviewsImage.setVisibility(View.GONE);
        }

        reconnectButton.setOnClickListener(v -> makeReviewsQuery(model.getId()));


        return view;
    }

    private void makeReviewsQuery(int movieId) {
        MoviesAPIInterface apiInterface = MoviesAPIClient.getClient().create(MoviesAPIInterface.class);
        Call<ReviewsModelData> call;

        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {

            //call = apiInterface.getReviews(String.valueOf(movieId), BuildConfig.API_KEY);
            /*call.enqueue(new Callback<ReviewsModelData>() {
                @Override
                public void onResponse(@NonNull Call<ReviewsModelData> call, @NonNull Response<ReviewsModelData> response) {
                    if (response.body().getReviewsModels().size() != 0) {

                        reconnectButton.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);

                        mReviewsList = new ArrayList<>();
                        mReviewsAdapter = new ReviewsAdapter(mReviewsList, ReviewsFragment.this);
                        mRecyclerView.setAdapter(mReviewsAdapter);

                    } else {
                        noReviewsImage.setVisibility(View.VISIBLE);
                        reconnectButton.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ReviewsModelData> call, @NonNull Throwable t) {
                    Log.d(TAG, "onFailure:" + t.getMessage());
                }
            })*/;
        } else
            Toast.makeText(getContext(), R.string.check_internet_connection, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onListItemClick(ReviewsModel reviewsModel) {
        // Nothing implemented for now
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mRecyclerView != null && mReviewsList != null) {
            stateRecyclerView = mRecyclerView.getLayoutManager().onSaveInstanceState();
            outState.putParcelableArrayList(SAVED_STATE_REVIEWS_LIST, mReviewsList);
        }
    }
}
