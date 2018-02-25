package com.curtesmalteser.popularmoviesstage1.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.curtesmalteser.popularmoviesstage1.BuildConfig;
import com.curtesmalteser.popularmoviesstage1.R;
import com.curtesmalteser.popularmoviesstage1.utils.MoviesAPIClient;
import com.curtesmalteser.popularmoviesstage1.utils.MoviesAPIInterface;
import com.curtesmalteser.popularmoviesstage1.utils.MoviesModel;
import com.curtesmalteser.popularmoviesstage1.utils.ReviewsModel;
import com.curtesmalteser.popularmoviesstage1.utils.VideosModel;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private ConnectivityManager cm;

    public ReviewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReviewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReviewsFragment newInstance(String param1, String param2) {
        ReviewsFragment fragment = new ReviewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
        cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        MoviesModel model = getActivity().getIntent().getParcelableExtra(getResources().getString(R.string.string_extra));
        makeReviewsQuery(model.getId());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reviews, container, false);
        return view;
    }

    private void makeReviewsQuery(String movieId) {
        MoviesAPIInterface apiInterface = MoviesAPIClient.getClient().create(MoviesAPIInterface.class);
        Call<ReviewsModel> call;

        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {

            call = apiInterface.getReviews(movieId, BuildConfig.API_KEY);
            call.enqueue(new Callback<ReviewsModel>() {
                @Override
                public void onResponse(Call<ReviewsModel> call, Response<ReviewsModel> response) {
                    Log.d("AJDB", ""+response.body().getReviewsModels().size());
                    // moviesAdapter = new MoviesAdapter(getContext(), response.body().getMoviesModels(), TopRatedMoviesFragment.this);
                    //mRecyclerView.setAdapter(moviesAdapter);
                }

                @Override
                public void onFailure(Call<ReviewsModel> call, Throwable t) {
                    Log.d("AJDB", "onFailure:" + t.getMessage().toString());
                }
            });
        } else
            Toast.makeText(getContext(), R.string.check_internet_connection, Toast.LENGTH_SHORT).show();
    }

}
