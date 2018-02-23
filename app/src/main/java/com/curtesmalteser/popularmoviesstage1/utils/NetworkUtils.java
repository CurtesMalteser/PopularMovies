package com.curtesmalteser.popularmoviesstage1.utils;

import android.net.Uri;
import android.util.Log;

import com.curtesmalteser.popularmoviesstage1.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by António "Curtes Malteser" Bastião on 17/02/2018.
 */


public class NetworkUtils {

    final static String POSTER_URL = "http://image.tmdb.org/t/p";

    public static Uri getPosterUrl(String width, String poster) {

        Uri buildUri = Uri.parse(POSTER_URL).buildUpon()
                .appendEncodedPath(width)
                .appendEncodedPath(poster)
                .build();
        Log.d("AJDB", "getPosterUrl: " + buildUri.toString());
        return buildUri;
    }
}