package com.curtesmalteser.popularmoviesstage1.activity;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.curtesmalteser.popularmoviesstage1.BuildConfig;
import com.curtesmalteser.popularmoviesstage1.MoviesModel;
import com.curtesmalteser.popularmoviesstage1.R;
import com.curtesmalteser.popularmoviesstage1.adapter.MoviesAdapter;
import com.curtesmalteser.popularmoviesstage1.utils.JsonUtils;
import com.curtesmalteser.popularmoviesstage1.utils.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<MoviesModel>>, AdapterView.OnItemSelectedListener, MoviesAdapter.ListItemClickListener {

    private static final String PREFERENCES_NAME = "movies_preferences";
    private final String SELECTION = "selection";

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private MoviesAdapter moviesAdapter;
    private List<MoviesModel> resultList = new ArrayList<>();

    ConnectivityManager cm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        recyclerView.setHasFixedSize(true);

        boolean tablet = getResources().getBoolean(R.bool.is_tablet);
        boolean isTen = getResources().getBoolean(R.bool.is_ten);
        boolean island = getResources().getBoolean(R.bool.is_landscape);

        if (tablet && isTen && island)
            recyclerView.setLayoutManager(new GridLayoutManager(this, 5));
        else if (tablet && isTen)
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        else if (tablet && island)
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        else if (tablet)
            recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        else
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void makeMoviesQuery(int index) {
        URL urlPopularMovies = NetworkUtils.buildUrlPopularMovies(BuildConfig.API_KEY);
        URL urlTopRatedMovies = NetworkUtils.buildUrlTopRated(BuildConfig.API_KEY);
        MoviesQuery moviesQuery = new MoviesQuery();

        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            switch (index) {
                case 0:
                    moviesQuery.execute(urlPopularMovies);
                    break;
                case 1:
                    moviesQuery.execute(urlTopRatedMovies);
                    break;
                default:
                    Toast.makeText(this, "The url isn't valid!", Toast.LENGTH_SHORT).show();
                    break;
            }
        } else
            Toast.makeText(this, R.string.check_internet_connection, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onListItemClick(MoviesModel moviesModel) {
        Intent intent = new Intent(MainActivity.this, MovieDetailsActivity.class);
        intent.putExtra(getResources().getString(R.string.string_extra), moviesModel);
        startActivity(intent);
    }

    @Override
    public Loader<List<MoviesModel>> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<MoviesModel>> loader, List<MoviesModel> data) {

    }

    @Override
    public void onLoaderReset(Loader<List<MoviesModel>> loader) {

    }

    public class MoviesQuery extends AsyncTask<URL, Void, List<MoviesModel>> {

        @Override
        protected List<MoviesModel> doInBackground(URL... urls) {
            String jsonResponse;
            List<MoviesModel> moviesResults = null;

            try {
                jsonResponse = NetworkUtils.getResponseFromHttpUrl(urls[0]);
                moviesResults = JsonUtils.parseMoviews(jsonResponse);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return moviesResults;
        }

        @Override
        protected void onPostExecute(List<MoviesModel> model) {
            resultList = model;

            moviesAdapter = new MoviesAdapter(MainActivity.this, resultList, MainActivity.this);
            recyclerView.setAdapter(moviesAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) item.getActionView();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.menu_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_item);

        spinner.setAdapter(adapter);

        SharedPreferences sharedPreferences = this.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        int selection = sharedPreferences.getInt(SELECTION, 0);

        spinner.setSelection(selection);

        spinner.setOnItemSelectedListener(this);

        makeMoviesQuery(selection);

        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        SharedPreferences sharedPreferences = this.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        String base = parent.getItemAtPosition(position).toString();
        int sharedPrefVal = sharedPreferences.getInt(PREFERENCES_NAME, 0);
        if (!base.equals(sharedPrefVal)) {
            sharedPreferences(position);
            makeMoviesQuery(position);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void sharedPreferences(int selection) {
        SharedPreferences.Editor sharedPreferences = this.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE).edit();
        sharedPreferences.putInt(SELECTION, selection);
        sharedPreferences.commit();
    }
}
