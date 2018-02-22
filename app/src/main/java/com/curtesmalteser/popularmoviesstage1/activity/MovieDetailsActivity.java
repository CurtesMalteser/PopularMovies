package com.curtesmalteser.popularmoviesstage1.activity;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.curtesmalteser.popularmoviesstage1.utils.Result;
import com.curtesmalteser.popularmoviesstage1.R;
import com.curtesmalteser.popularmoviesstage1.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailsActivity extends AppCompatActivity {

    @BindView(R.id.posterInDetailsActivity)
    ImageView poster;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.voteAverage)
    TextView voteAverage;

    @BindView(R.id.releaseDate)
    TextView releaseDate;

    @BindView(R.id.overview)
    TextView overview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        if (getIntent().hasExtra(getResources().getString(R.string.string_extra))) {
            Result model = getIntent().getParcelableExtra(getResources().getString(R.string.string_extra));

            Picasso.with(this)
                    .load(NetworkUtils.getPosterUrl(model.getPosterPath()))
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(poster);

            title.setText(model.getTitle());

            voteAverage.setText(String.format(getString(R.string.string_vote_average), model.getVoteAverage()));
            releaseDate.setText(model.getReleaseDate());
            overview.setText(model.getOverview());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
