package com.curtesmalteser.popularmoviesstage1.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.curtesmalteser.popularmoviesstage1.R;
import com.curtesmalteser.popularmoviesstage1.adapter.SimpleFragmentPagerAdapter;
import com.curtesmalteser.popularmoviesstage1.db.MoviesContract;
import com.curtesmalteser.popularmoviesstage1.db.MoviesDbHelper;
import com.curtesmalteser.popularmoviesstage1.utils.MoviesModel;
import com.curtesmalteser.popularmoviesstage1.utils.NetworkUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.curtesmalteser.popularmoviesstage1.db.MoviesContract.MoviesEntry.COLUMN_NAME_ID;
import static com.curtesmalteser.popularmoviesstage1.db.MoviesContract.MoviesEntry.CONTENT_URI;

public class MovieDetailsActivity extends AppCompatActivity {

    private MoviesModel model;

    private SQLiteDatabase mDb;

    private boolean state;

    @BindView(R.id.background)
    ImageView backgroung;

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

    @BindView(R.id.tvAddRemove)
    TextView tvAddRemove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        backgroung.setColorFilter(Color.argb(230, 0, 0, 0));

        // TODO: 23/02/2018 check internet connection..........................................
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

        MoviesDbHelper dbHelper = new MoviesDbHelper(this);
        mDb = dbHelper.getWritableDatabase();

        Cursor cursor = checkFavoriteMovie();

        if (cursor.getCount() > 0) {
            Log.d("AJDB", "Count: " + cursor.getCount());
            tvAddRemove.setText(R.string.string_remove_fav);
            likeButton.setImageResource(R.drawable.ic_heart_red);
            state = true;
        } else {
            tvAddRemove.setText(R.string.string_add_fav);
            likeButton.setImageResource(R.drawable.ic_heart_white);
            Log.d("AJDB", "false ");
            state = false;
        }
        cursor.close();
    }

    private void getPoster() {
        Picasso.with(this)
                .load(NetworkUtils.getPosterUrl(getResources().getString(R.string.poster_width_segment), model.getPosterPath()))
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(poster, new Callback() {
                    @Override
                    public void onSuccess() {
                        getBackground();
                    }

                    @Override
                    public void onError() {
                        //Try again online if cache failed
                        Picasso.with(getApplicationContext())
                                .load(NetworkUtils.getPosterUrl(getResources().getString(R.string.poster_width_segment), model.getPosterPath()))
                                .error(R.drawable.ic_heart_white)
                                .into(poster, new Callback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError() {
                                        Log.v("Picasso", "Could not fetch image");
                                    }
                                });
                    }
                });
    }

    private void getBackground() {
        Picasso.with(this)
                .load(NetworkUtils.getPosterUrl(getResources().getString(R.string.poster_width_segment), model.getBackdropPath()))
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(backgroung, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        //Try again online if cache failed
                        Picasso.with(getApplicationContext())
                                .load(NetworkUtils.getPosterUrl(getResources().getString(R.string.poster_width_segment), model.getBackdropPath()))
                                .error(R.drawable.drawable_background)
                                .into(backgroung, new Callback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError() {
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

        return getContentResolver().delete(uri,null,null);

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
