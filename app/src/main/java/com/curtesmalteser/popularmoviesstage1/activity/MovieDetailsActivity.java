package com.curtesmalteser.popularmoviesstage1.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.viewpager.widget.ViewPager;

import com.curtesmalteser.popularmoviesstage1.R;
import com.curtesmalteser.popularmoviesstage1.adapter.SimpleFragmentPagerAdapter;
import com.curtesmalteser.popularmoviesstage1.db.MoviesContract;
import com.curtesmalteser.popularmoviesstage1.utils.MoviesModel;
import com.curtesmalteser.popularmoviesstage1.utils.NetworkUtils;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import static com.curtesmalteser.popularmoviesstage1.db.MoviesContract.MoviesEntry.COLUMN_NAME_ID;
import static com.curtesmalteser.popularmoviesstage1.db.MoviesContract.MoviesEntry.CONTENT_URI;

public class MovieDetailsActivity extends AppCompatActivity {

    private MoviesModel model;

    private boolean state;

    ImageView background;

    ImageView poster;

    TextView title;

    TextView voteAverage;

    TextView releaseDate;

    ViewPager viewPager;

    TabLayout tabLayout;

    ImageButton likeButton;

    TextView tvAddRemove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        background = findViewById(R.id.background);
        poster = findViewById(R.id.posterInDetailsActivity);
        title = findViewById(R.id.title);
        voteAverage = findViewById(R.id.voteAverage);
        releaseDate = findViewById(R.id.releaseDate);
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.sliding_tabs);
        likeButton = findViewById(R.id.likeButton);
        tvAddRemove = findViewById(R.id.tvAddRemove);

        background.setColorFilter(Color.argb(220, 0, 0, 0));

        if (getIntent().hasExtra(getResources().getString(R.string.string_extra))) {
            model = getIntent().getParcelableExtra(getResources().getString(R.string.string_extra));

            getPoster();

            title.setText(model.getTitle());

            voteAverage.setText(String.format(getString(R.string.string_vote_average), model.getVoteAverage()));
            releaseDate.setText(model.getReleaseDate());

            SimpleFragmentPagerAdapter adapter =
                    new SimpleFragmentPagerAdapter(this, getSupportFragmentManager());

            viewPager.setAdapter(adapter);

            tabLayout.setupWithViewPager(viewPager);
        }

        likeButton.setOnClickListener(v -> {
            if (!state) {
                long movieSaved = addFavoriteMovie();
                if (movieSaved > 0) {
                    likeButton.setImageResource(R.drawable.ic_heart_red);
                    tvAddRemove.setText(R.string.string_remove_fav);
                    Toast.makeText(MovieDetailsActivity.this, "You like this movie!", Toast.LENGTH_SHORT).show();
                    state = true;
                } else {
                    Toast.makeText(MovieDetailsActivity.this, R.string.string_error_add_fav, Toast.LENGTH_SHORT).show();
                }
            } else {
                int deleted = removeFavoriteMovie();
                if (deleted > 0) {
                    likeButton.setImageResource(R.drawable.ic_heart_white);
                    tvAddRemove.setText(R.string.string_add_fav);
                    Toast.makeText(MovieDetailsActivity.this, "You don't like this movie!", Toast.LENGTH_SHORT).show();
                    state = false;
                }
            }
        });

        Cursor cursor = checkFavoriteMovie();

        if (cursor.getCount() > 0) {
            tvAddRemove.setText(R.string.string_remove_fav);
            likeButton.setImageResource(R.drawable.ic_heart_red);
            state = true;
        } else {
            tvAddRemove.setText(R.string.string_add_fav);
            likeButton.setImageResource(R.drawable.ic_heart_white);
            state = false;
        }
        cursor.close();
    }

    private void getPoster() {
        Picasso.get()
                .load(NetworkUtils.getPosterUrl(getResources().getString(R.string.poster_width_segment), model.getPosterPath()))
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(poster, new Callback() {
                    @Override
                    public void onSuccess() {
                        getBackground();
                    }

                    @Override
                    public void onError(Exception e) {
                        //Try again online if cache failed
                        Picasso.get()
                                .load(NetworkUtils.getPosterUrl(getResources().getString(R.string.poster_width_segment), model.getPosterPath()))
                                .error(R.drawable.ic_heart_white)
                                .into(poster, new Callback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError(Exception e) {
                                        Log.v("Picasso", "Could not fetch image");
                                    }
                                });
                    }
                });
    }

    private void getBackground() {
        Picasso.get()
                .load(NetworkUtils.getPosterUrl(getResources().getString(R.string.poster_width_segment), model.getBackdropPath()))
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(background, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        //Try again online if cache failed
                        Picasso.get()
                                .load(NetworkUtils.getPosterUrl(getResources().getString(R.string.poster_width_segment), model.getBackdropPath()))
                                .error(R.drawable.drawable_background)
                                .into(background, new Callback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError(Exception e) {
                                        Log.v("Picasso", "Could not fetch image");
                                    }
                                });
                    }
                });
    }


    private int addFavoriteMovie() {
        ContentValues cv = new ContentValues();
        cv.put(MoviesContract.MoviesEntry.COLUMN_NAME_ID, model.getId());
        cv.put(MoviesContract.MoviesEntry.COLUMN_NAME_VOTE_AVERAGE, model.getVoteAverage());
        cv.put(MoviesContract.MoviesEntry.COLUMN_NAME_TITLE, model.getTitle());
        cv.put(MoviesContract.MoviesEntry.COLUMN_NAME_POSTER_PATH, model.getPosterPath());
        cv.put(MoviesContract.MoviesEntry.COLUMN_NAME_BACKDROP_PATH, model.getBackdropPath());
        cv.put(MoviesContract.MoviesEntry.COLUMN_NAME_OVERVIEW, model.getOverview());
        cv.put(MoviesContract.MoviesEntry.COLUMN_NAME_RELEASE_DATE, model.getReleaseDate());

        Uri uri = getContentResolver().insert(MoviesContract.MoviesEntry.CONTENT_URI, cv);

        if (uri != null) {
            return 1;
        } else return 0;
    }

    private int removeFavoriteMovie() {
        Uri uri = CONTENT_URI;
        uri = uri.buildUpon().appendPath(String.valueOf(model.getId())).build();

        return getContentResolver().delete(uri, null, null);

    }

    private Cursor checkFavoriteMovie() {

        String[] selectionArgs = new String[]{String.valueOf(model.getId())};
        return getContentResolver().query(
                CONTENT_URI,
                null,
                COLUMN_NAME_ID + " = ? ",
                selectionArgs,
                null
        );
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
