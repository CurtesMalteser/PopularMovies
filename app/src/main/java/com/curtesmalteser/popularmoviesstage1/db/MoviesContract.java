package com.curtesmalteser.popularmoviesstage1.db;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by António "Curtes Malteser" Bastião on 27/02/2018.
 */

public class MoviesContract {

    public static final String AUTHORITY = "com.curtesmalteser.popularmoviesstage1";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_MOVIES = "favorite_movies";

    private MoviesContract() {
        // Intentionally empty
    }

    public static final class MoviesEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();

        public static final String TABLE_NAME = "favorite_movies";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_VOTE_AVERAGE = "vote_average";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_POSTER_PATH = "poster_path";
        public static final String COLUMN_NAME_BACKDROP_PATH = "backdrop_path";
        public static final String COLUMN_NAME_OVERVIEW = "overview";
        public static final String COLUMN_NAME_RELEASE_DATE = "release_date";

    }
}
