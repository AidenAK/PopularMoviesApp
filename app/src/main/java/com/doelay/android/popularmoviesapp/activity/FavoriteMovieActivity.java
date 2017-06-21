package com.doelay.android.popularmoviesapp.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.doelay.android.popularmoviesapp.R;
import com.doelay.android.popularmoviesapp.adapter.FavoriteMovieAdapter;
import com.doelay.android.popularmoviesapp.db.MoivesContract;

/**
 * Created by doelay on 6/20/2017.
 */

public class FavoriteMovieActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = FavoriteMovieActivity.class.getSimpleName();
    private static final int FAVORITE_MOVIE_LOADER_ID = 1;

    private FavoriteMovieAdapter mFavoriteMovieAdapter;
    private RecyclerView favoriteMovieRecyclerView;
    private TextView noFavorite;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        noFavorite = (TextView) findViewById(R.id.tv_no_favorite);

        favoriteMovieRecyclerView = (RecyclerView) findViewById(R.id.rv_favorite_movies);
        favoriteMovieRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFavoriteMovieAdapter = new FavoriteMovieAdapter();
        favoriteMovieRecyclerView.setAdapter(mFavoriteMovieAdapter);

        getSupportLoaderManager().initLoader(FAVORITE_MOVIE_LOADER_ID, null, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(FAVORITE_MOVIE_LOADER_ID, null, this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<Cursor>(this) {

            Cursor favoriteMovieData = null;

            @Override
            protected void onStartLoading() {
                if( favoriteMovieData != null) {
                    deliverResult(favoriteMovieData);
                } else {
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {

                try {
                    return getContentResolver().query(MoivesContract.MoviesEntry.CONTENT_URI,
                            null,
                            null,
                            null,
                            null);


                } catch (Exception e) {
                    e.printStackTrace();
                    return null;

                }
            }

            public void deliverResult(Cursor data) {
                favoriteMovieData = data;
                super.deliverResult(data);

            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d(TAG, "onLoadFinished: " + data.getCount());
        if(data.getCount() == 0) {
            showNoFavoriteError();
        }
        mFavoriteMovieAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mFavoriteMovieAdapter.swapCursor(null);
    }

    private void showNoFavoriteError() {
        favoriteMovieRecyclerView.setVisibility(View.INVISIBLE);
        noFavorite.setVisibility(View.VISIBLE);
    }
}
