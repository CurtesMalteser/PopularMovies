package com.curtesmalteser.popularmoviesstage1.utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by António "Curtes Malteser" Bastião on 17/02/2018.
 */


public class NetworkUtils {

    final static String BASE_URL = "https://api.themoviedb.org/3";

    final static String POSTER_URL = "http://image.tmdb.org/t/p/w185/";

    final static String MOVIE_POPULAR = "movie/popular";

    final static String MOVIE_TOP_RATED = "movie/top_rated";

    final static String API = "api_key";


    public static URL buildUrlPopularMovies(String key) {

        Uri buildUri = Uri.parse(BASE_URL).buildUpon()
                .appendEncodedPath(MOVIE_POPULAR)
                .appendQueryParameter(API, key)
                .build();

        URL url = null;

        try {
            url = new URL(buildUri.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL buildUrlTopRated(String key) {

        Uri buildUri = Uri.parse(BASE_URL).buildUpon()
                .appendEncodedPath(MOVIE_TOP_RATED)
                .appendQueryParameter(API, key)
                .build();

        URL url = null;

        try {
            url = new URL(buildUri.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }

        } finally {
            urlConnection.disconnect();
        }
    }

    public static String getPosterUrl(String poster) {

        return POSTER_URL + poster;
    }

}
