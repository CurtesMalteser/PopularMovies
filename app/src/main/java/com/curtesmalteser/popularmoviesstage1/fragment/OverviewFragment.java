package com.curtesmalteser.popularmoviesstage1.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.curtesmalteser.popularmoviesstage1.R;
import com.curtesmalteser.popularmoviesstage1.utils.MoviesModel;

public class OverviewFragment extends Fragment {

    TextView tvOverview;

    public OverviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overview, container, false);

        MoviesModel model = getActivity().getIntent().getParcelableExtra(getResources().getString(R.string.string_extra));
        tvOverview = view.findViewById(R.id.overview);
        tvOverview.setText(model.getOverview());

        return view;
    }
}
