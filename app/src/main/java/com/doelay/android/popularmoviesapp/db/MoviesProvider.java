package com.doelay.android.popularmoviesapp.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by doelay on 6/13/2017.
 */

public class MoviesProvider extends ContentProvider {

    private static final int FAVORITE_MOVIES = 500;
    private static final int FAVORITE_MOVIES_WITH_ID = 501;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MoviesDbHelper mMoviesDbHelper;

    private static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        //match Uri for FavoriteMovies table
        uriMatcher.addURI(
                MoivesContract.AUTHORITY,
                MoivesContract.PATH_FAVORITE_MOVIES,
                FAVORITE_MOVIES);
        //match Uri for each row in FavoriteMovies table
        uriMatcher.addURI(
                MoivesContract.AUTHORITY,
                MoivesContract.PATH_FAVORITE_MOVIES + "/#",
                FAVORITE_MOVIES_WITH_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mMoviesDbHelper = new MoviesDbHelper(context);
        return true;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        return super.bulkInsert(uri, values);
    }
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = mMoviesDbHelper.getWritableDatabase();
        Uri returnUri;
        int match = sUriMatcher.match(uri);
        switch(match) {
            case FAVORITE_MOVIES:
                long rowId = db.insert(MoivesContract.MoviesEntry.TABLE_NAME, null, contentValues);
                if(rowId > 0) {
                    returnUri = ContentUris.withAppendedId(MoivesContract.MoviesEntry.CONTENT_URI, rowId);
                    getContext().getContentResolver().notifyChange(uri, null);
                    return returnUri;
                } else {
                    throw new android.database.SQLException("Problem inserting into " + uri);
                }
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] columns, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = mMoviesDbHelper.getReadableDatabase();
        Cursor returnCursor;
        int match = sUriMatcher.match(uri);
        switch (match) {
            case FAVORITE_MOVIES:
                returnCursor = db.query(
                        MoivesContract.MoviesEntry.TABLE_NAME,
                        columns,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri: "+ uri);
        }
        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return returnCursor;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mMoviesDbHelper.getWritableDatabase();
        int rowDeleted;
        int match = sUriMatcher.match(uri);
        switch(match) {
            case FAVORITE_MOVIES_WITH_ID:
                String movieIdString = uri.getLastPathSegment();
                rowDeleted = db.delete(
                        MoivesContract.MoviesEntry.TABLE_NAME,
                        "movie_id=?",
                        new String[] {movieIdString});
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri: "+ uri);
        }

        if( rowDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);

        }
        return rowDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }
}
