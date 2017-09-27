package com.doelay.android.popularmoviesapp.activity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.doelay.android.popularmoviesapp.R;
import com.doelay.android.popularmoviesapp.adapter.FavoriteMovieAdapter;
import com.doelay.android.popularmoviesapp.db.MoivesContract;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by doelay on 6/20/2017.
 */
// TODO: 7/8/2017 add remove from favorite functionality
public class FavoriteMovieActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.tv_no_favorite) TextView noFavorite;
    @BindView(R.id.rv_favorite_movies) RecyclerView favoriteMovieRecyclerView;

    private static final String TAG = FavoriteMovieActivity.class.getSimpleName();

    public static final String FAVORITE_RECYCLER_VIEW_STATE = "favorite_recycler_view_state";
    private static final int FAVORITE_MOVIE_LOADER_ID = 1;

    private FavoriteMovieAdapter mFavoriteMovieAdapter;
    private LinearLayoutManager recyclerLayoutManager;
    private Parcelable recyclerState;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        recyclerLayoutManager = new LinearLayoutManager(this);
        favoriteMovieRecyclerView.setLayoutManager(recyclerLayoutManager);
        mFavoriteMovieAdapter = new FavoriteMovieAdapter();
        favoriteMovieRecyclerView.setAdapter(mFavoriteMovieAdapter);

        //add swipe listener
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int movieId = (int) viewHolder.itemView.getTag();

                String movieIdString = Integer.toString(movieId);
                Uri uri = MoivesContract.MoviesEntry.CONTENT_URI;
                uri = uri.buildUpon().appendPath(movieIdString).build();

                getContentResolver().delete(uri, null, null);
                getSupportLoaderManager().restartLoader(FAVORITE_MOVIE_LOADER_ID, null, FavoriteMovieActivity.this);
            }
        }).attachToRecyclerView(favoriteMovieRecyclerView);

        getSupportLoaderManager().initLoader(FAVORITE_MOVIE_LOADER_ID, null, this);

        if(savedInstanceState != null) {
            recyclerState = savedInstanceState.getParcelable(FAVORITE_RECYCLER_VIEW_STATE);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        recyclerState = recyclerLayoutManager.onSaveInstanceState();
        outState.putParcelable(FAVORITE_RECYCLER_VIEW_STATE, recyclerState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();

        getSupportLoaderManager().restartLoader(FAVORITE_MOVIE_LOADER_ID, null, this);
        if (recyclerState != null) {
            recyclerLayoutManager.onRestoreInstanceState(recyclerState);
        }
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
