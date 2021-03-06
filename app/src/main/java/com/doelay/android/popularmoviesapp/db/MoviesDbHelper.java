package com.doelay.android.popularmoviesapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by doelay on 6/13/2017.
 */

public class MoviesDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "moviesDb.db";
    private static final int DATABASE_VERSION = 2;

    public MoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String CREATE_TABLE = "CREATE TABLE " + MoivesContract.MoviesEntry.TABLE_NAME + " (" +
                MoivesContract.MoviesEntry.MOVIE_ID + " INTEGER PRIMARY KEY, " +
                MoivesContract.MoviesEntry.MOVIE_TITLE + " TEXT NOT NULL, " +
                MoivesContract.MoviesEntry.MOVIE_OVERVIEW + " TEXT, " +
                MoivesContract.MoviesEntry.MOVIE_RELEASE_DATE + " TEXT NOT NULL, " +
                MoivesContract.MoviesEntry.MOVIE_POSTER_PATH + " TEXT NOT NULL, " +
                MoivesContract.MoviesEntry.MOVIE_BACKDROP_PATH + " TEXT NOT NULL);";
        sqLiteDatabase.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        if(oldVersion < 2) {
            sqLiteDatabase.execSQL(
                    "ALTER TABLE " + MoivesContract.MoviesEntry.TABLE_NAME +
                    " ADD COLUMN " + MoivesContract.MoviesEntry.MOVIE_BACKDROP_PATH + " TEXT;");
        }
    }
}
