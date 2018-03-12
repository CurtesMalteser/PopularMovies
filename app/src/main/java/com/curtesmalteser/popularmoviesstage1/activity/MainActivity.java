package com.curtesmalteser.popularmoviesstage1.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.curtesmalteser.popularmoviesstage1.R;
import com.curtesmalteser.popularmoviesstage1.fragment.FavoriteMoviesFragment;
import com.curtesmalteser.popularmoviesstage1.fragment.PopularMoviesFragment;
import com.curtesmalteser.popularmoviesstage1.fragment.TopRatedMoviesFragment;
import com.curtesmalteser.popularmoviesstage1.utils.MoviesModel;
import com.facebook.stetho.Stetho;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String PREFERENCES_NAME = "movies_preferences";
    private final String SELECTION = "selected_fragment";

    @BindView(R.id.bottomNavigationMenu)
    BottomNavigationView bottomNavigationMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Stetho.initializeWithDefaults(this);

        ButterKnife.bind(this);

        bottomNavigationMenu.setOnNavigationItemSelectedListener
                (item -> setFragment(item.getItemId()));


        if (savedInstanceState == null) {
            setFragment(readPreferences());
        bottomNavigationMenu.setSelectedItemId(readPreferences());

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

    private void savePreferences(int selection) {
        SharedPreferences.Editor sharedPreferences = this.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE).edit();
        sharedPreferences.putInt(SELECTION, selection);
        sharedPreferences.apply();
    }

    private int readPreferences() {
        SharedPreferences sharedPreferences = this.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        return sharedPreferences.getInt(SELECTION, R.id.action_popular_movies);
    }
}