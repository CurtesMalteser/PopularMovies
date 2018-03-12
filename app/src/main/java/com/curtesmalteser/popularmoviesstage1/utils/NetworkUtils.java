package com.curtesmalteser.popularmoviesstage1.utils;

import android.net.Uri;

/**
 * Created by António "Curtes Malteser" Bastião on 17/02/2018.
 */

public class NetworkUtils {

    final static String POSTER_URL = "http://image.tmdb.org/t/p";

    final static String BASE_THUMBNAIL_URL = "https://img.youtube.com/vi";
    final static String THUMBNAIL_SIZE_URL = "0.jpg";


    public static Uri getPosterUrl(String width, String poster) {
        return Uri.parse(POSTER_URL).buildUpon()
                .appendEncodedPath(width)
                .appendEncodedPath(poster)
                .build();
    }

    public static Uri getThumbnail(String key) {
        return Uri.parse(BASE_THUMBNAIL_URL).buildUpon()
                .appendEncodedPath(key)
                .appendEncodedPath(THUMBNAIL_SIZE_URL)
                .build();
    }
}