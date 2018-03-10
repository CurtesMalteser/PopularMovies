package com.curtesmalteser.popularmoviesstage1.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.curtesmalteser.popularmoviesstage1.R;
import com.curtesmalteser.popularmoviesstage1.fragment.FavoriteMoviesFragment;
import com.curtesmalteser.popularmoviesstage1.fragment.PopularMoviesFragment;
import com.curtesmalteser.popularmoviesstage1.fragment.TopRatedMoviesFragment;
import com.curtesmalteser.popularmoviesstage1.utils.MoviesModel;
import com.facebook.stetho.Stetho;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements PopularMoviesFragment.OnPopularMoviesConfigChangesListener,
        TopRatedMoviesFragment.OnFragmentInteractionListener,
        FavoriteMoviesFragment.OnFragmentInteractionListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String PREFERENCES_NAME = "movies_preferences";
    private final String SELECTION = "selected_fragment";

    @BindView(R.id.bottomNavigationMenu)
    BottomNavigationView bottomNavigationMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Stetho
        Stetho.initializeWithDefaults(this);

        ButterKnife.bind(this);

        bottomNavigationMenu.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        return setFragment(item.getItemId());
                    }
                });

        if (savedInstanceState == null) {
            Log.d("AJDB", "the Frgament is null");

            setFragment(readPreferences());
        }

    }

    private boolean setFragment(int id) {
        Fragment selectedFragment = null;
        switch (id) {
            case R.id.action_popular_movies:
                selectedFragment = PopularMoviesFragment.newInstance();
                savePreferences(R.id.action_popular_movies);
                break;
            case R.id.action_top_rated_movies:
                selectedFragment = TopRatedMoviesFragment.newInstance();
                savePreferences(R.id.action_top_rated_movies);
                break;
            case R.id.action_favorite_movies:
                selectedFragment = FavoriteMoviesFragment.newInstance();
                savePreferences(R.id.action_favorite_movies);
                break;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, selectedFragment);
        transaction.commit();
        return true;
    }

    @Override
    public void onFragmentInteraction(MoviesModel moviesModel) {

    }

    private void savePreferences(int selection) {
        SharedPreferences.Editor sharedPreferences = this.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE).edit();
        sharedPreferences.putInt(SELECTION, selection);
        sharedPreferences.commit();
    }

    private int readPreferences() {
        SharedPreferences sharedPreferences = this.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        return sharedPreferences.getInt(SELECTION, R.id.action_popular_movies);
    }

    @Override
    public void onPopularMoviesConfigChangesListener(ArrayList<MoviesModel> moviesList) {
    }
}