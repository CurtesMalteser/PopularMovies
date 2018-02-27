package com.curtesmalteser.popularmoviesstage1.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.curtesmalteser.popularmoviesstage1.adapter.SimpleFragmentPagerAdapter;
import com.curtesmalteser.popularmoviesstage1.fragment.ReviewsFragment;
import com.curtesmalteser.popularmoviesstage1.utils.MoviesModel;
import com.curtesmalteser.popularmoviesstage1.R;
import com.curtesmalteser.popularmoviesstage1.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailsActivity extends AppCompatActivity{

    @BindView(R.id.posterInDetailsActivity)
    ImageView poster;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.voteAverage)
    TextView voteAverage;

    @BindView(R.id.releaseDate)
    TextView releaseDate;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.sliding_tabs)
    TabLayout tabLayout;

    @BindView(R.id.likeButton)
    ImageButton likeButton;

    private boolean state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        // TODO: 23/02/2018 check internet connection..........................................
        if (getIntent().hasExtra(getResources().getString(R.string.string_extra))) {
            MoviesModel model = getIntent().getParcelableExtra(getResources().getString(R.string.string_extra));

            Picasso.with(this)
                    .load(NetworkUtils.getPosterUrl(getResources().getString(R.string.poster_width_segment), model.getPosterPath()))

                    .into(poster);

            title.setText(model.getTitle());

            voteAverage.setText(String.format(getString(R.string.string_vote_average), model.getVoteAverage()));
            releaseDate.setText(model.getReleaseDate());

            SimpleFragmentPagerAdapter adapter =
                    new SimpleFragmentPagerAdapter(this, getSupportFragmentManager());

            viewPager.setAdapter(adapter);

            tabLayout.setupWithViewPager(viewPager);
        }

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state == false ) {
                    state = true;
                Toast.makeText(MovieDetailsActivity.this, "You like this movie!", Toast.LENGTH_SHORT).show();
                likeButton.setImageResource(R.drawable.ic_heart_red);
                } else {
                    state = false;
                    Toast.makeText(MovieDetailsActivity.this, "You don't like this movie!", Toast.LENGTH_SHORT).show();
                    likeButton.setImageResource(R.drawable.ic_heart_white);
                }
            }
        });
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
