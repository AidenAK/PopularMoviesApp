package com.doelay.android.popularmoviesapp.db;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by doelay on 6/13/2017.
 */

public class MoivesContract {

    public static final String AUTHORITY = "com.doelay.android.popularmoviesapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content//" + AUTHORITY);
    public static final String PATH_FAVORITE_MOVIES = "favoriteMovies";

    public static final class MoviesEntry implements BaseColumns {
        //Content Uri => content//com.doelay.android.popularmoviesapp/favoriteMovies
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITE_MOVIES).build();
        //Table and column names
        public static final String TABEL_NAME = "favoriteMovies";
        public static final String MOVIE_ID = "movie_id";
        public static final String MOVIE_TITLE = "movie_title";
    }

}
