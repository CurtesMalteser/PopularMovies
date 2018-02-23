package com.curtesmalteser.popularmoviesstage1.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.curtesmalteser.popularmoviesstage1.R;
import com.curtesmalteser.popularmoviesstage1.utils.MoviesModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OverviewFragment extends Fragment {

    @BindView(R.id.overview)
    TextView tvOverview;

    public OverviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overview, container, false);

        ButterKnife.bind(this, view);
        MoviesModel model = getActivity().getIntent().getParcelableExtra(getResources().getString(R.string.string_extra));
        tvOverview.setText(model.getOverview());

        return view;
    }
}
