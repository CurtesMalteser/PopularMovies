package com.curtesmalteser.popularmoviesstage1.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.curtesmalteser.popularmoviesstage1.db.MoviesContract.MoviesEntry.COLUMN_NAME_BACKDROP_PATH;
import static com.curtesmalteser.popularmoviesstage1.db.MoviesContract.MoviesEntry.COLUMN_NAME_ID;
import static com.curtesmalteser.popularmoviesstage1.db.MoviesContract.MoviesEntry.COLUMN_NAME_OVERVIEW;
import static com.curtesmalteser.popularmoviesstage1.db.MoviesContract.MoviesEntry.COLUMN_NAME_POSTER_PATH;
import static com.curtesmalteser.popularmoviesstage1.db.MoviesContract.MoviesEntry.COLUMN_NAME_RELEASE_DATE;
import static com.curtesmalteser.popularmoviesstage1.db.MoviesContract.MoviesEntry.COLUMN_NAME_TITLE;
import static com.curtesmalteser.popularmoviesstage1.db.MoviesContract.MoviesEntry.COLUMN_NAME_VOTE_AVERAGE;
import static com.curtesmalteser.popularmoviesstage1.db.MoviesContract.MoviesEntry.TABLE_NAME;
import static com.curtesmalteser.popularmoviesstage1.db.MoviesContract.MoviesEntry._ID;

/**
 * Created by António "Curtes Malteser" Bastião on 04/03/2018.
 */

public class MoviesDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 1;

    public MoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_FAVORITE_MOVIES_TABLE = "CREATE TABLE  " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME_ID + " INTEGER NOT NULL, " +
                COLUMN_NAME_VOTE_AVERAGE + " TEXT NOT NULL, " +
                COLUMN_NAME_TITLE + " TEXT NOT NULL, " +
                COLUMN_NAME_POSTER_PATH + " TEXT NOT NULL, " +
                COLUMN_NAME_BACKDROP_PATH + " TEXT NOT NULL, " +
                COLUMN_NAME_OVERVIEW + " TEXT NOT NULL, " +
                COLUMN_NAME_RELEASE_DATE + " TEXT NOT NULL" +
                ");";
        db.execSQL(SQL_CREATE_FAVORITE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
