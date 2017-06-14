package com.doelay.android.popularmoviesapp.db;

import android.net.Uri;
import android.provider.BaseColumns;


public class MoivesContract {

    public static final String AUTHORITY = "com.doelay.android.popularmoviesapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content//" + AUTHORITY);
    public static final String PATH_FAVORITE_MOVIES = "FavoriteMovies";

    public static final class MoviesEntry implements BaseColumns {
        //Content Uri => content//com.doelay.android.popularmoviesapp/FavoriteMovies
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITE_MOVIES).build();
        //Table and column names
        public static final String TABLE_NAME = "FavoriteMovies";
        public static final String MOVIE_ID = "movie_id";
        public static final String MOVIE_TITLE = "movie_title";
        public static final String MOVIE_OVERVIEW = "movie_overview";
        public static final String MOVIE_RELEASE_DATE = "movie_release_date";
        public static final String MOVIE_POSTER_PATH = "movie_poster_path";


    }

}
