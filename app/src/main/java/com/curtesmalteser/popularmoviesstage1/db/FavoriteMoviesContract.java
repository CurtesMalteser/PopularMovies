package com.curtesmalteser.popularmoviesstage1.db;

import android.provider.BaseColumns;

/**
 * Created by António "Curtes Malteser" Bastião on 27/02/2018.
 */

public class FavoriteMoviesContract {

    private FavoriteMoviesContract() {
        // Intentionally empty
    }

    public class MoviesEntry implements BaseColumns {
        public static final String TABLE_NAME = "favorite_movies";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_OVERVIEW = "overview";
        public static final String COLUMN_NAME_VOTE_AVERAGE = "vote_average";
        public static final String COLUMN_NAME_RELEASE_DATE = "release_date";
        public static final String COLUMN_NAME_POSTER_PATH = "poster_path";
    }
}
