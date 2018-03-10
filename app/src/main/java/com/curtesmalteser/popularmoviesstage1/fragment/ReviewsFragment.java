package com.curtesmalteser.popularmoviesstage1.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.curtesmalteser.popularmoviesstage1.BuildConfig;
import com.curtesmalteser.popularmoviesstage1.R;
import com.curtesmalteser.popularmoviesstage1.adapter.ReviewsAdapter;
import com.curtesmalteser.popularmoviesstage1.utils.MoviesAPIClient;
import com.curtesmalteser.popularmoviesstage1.utils.MoviesAPIInterface;
import com.curtesmalteser.popularmoviesstage1.utils.MoviesModel;
import com.curtesmalteser.popularmoviesstage1.utils.ReviewsModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewsFragment extends Fragment
        implements ReviewsAdapter.ListItemClickListener {

    private static final String TAG = ReviewsFragment.class.getSimpleName();

    @BindView(R.id.reviewsRecyclerView)
    RecyclerView mRecyclerView;

    private ReviewsAdapter mReviewsAdapter;

    private List<ReviewsModel> mReviewsList;

    private ConnectivityManager cm;

    public ReviewsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        /** TODO: 01/03/2018
         * Fix the Frament Lifecycle -> All steps are missing
         * 1.onSaveInstanceState
         * 2.onCreateView
         **/
        cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        MoviesModel model = getActivity().getIntent().getParcelableExtra(getResources().getString(R.string.string_extra));
        makeReviewsQuery(model.getId());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reviews, container, false);
        ButterKnife.bind(this, view);
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        mDividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.item_decoration));
        mRecyclerView.addItemDecoration(mDividerItemDecoration);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    private void makeReviewsQuery(int movieId) {
        MoviesAPIInterface apiInterface = MoviesAPIClient.getClient().create(MoviesAPIInterface.class);
        Call<ReviewsModel> call;

        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {

            call = apiInterface.getReviews(String.valueOf(movieId), BuildConfig.API_KEY);
            call.enqueue(new Callback<ReviewsModel>() {
                @Override
                public void onResponse(Call<ReviewsModel> call, Response<ReviewsModel> response) {
                    if (response.body().getReviewsModels().size() != 0) {
                        mReviewsList = response.body().getReviewsModels();
                        mReviewsAdapter = new ReviewsAdapter(mReviewsList, ReviewsFragment.this);
                        mRecyclerView.setAdapter(mReviewsAdapter);
                    } else {
                        Log.d(TAG, "There are no reviews for this movie.");
                    }
                }

                @Override
                public void onFailure(Call<ReviewsModel> call, Throwable t) {
                    Log.d("AJDB", "onFailure:" + t.getMessage().toString());
                }
            });
        } else
            Toast.makeText(getContext(), R.string.check_internet_connection, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onListItemClick(ReviewsModel reviewsModel) {
        // Nothing implemented for now
    }
}
