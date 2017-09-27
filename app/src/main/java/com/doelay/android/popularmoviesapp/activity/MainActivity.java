package com.doelay.android.popularmoviesapp.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.doelay.android.popularmoviesapp.model.Movies;
import com.doelay.android.popularmoviesapp.task.FetchMoviesDataTask;
import com.doelay.android.popularmoviesapp.utils.JsonUtils;
import com.doelay.android.popularmoviesapp.adapter.MovieAdapter;
import com.doelay.android.popularmoviesapp.utils.NetworkUtils;
import com.doelay.android.popularmoviesapp.R;
import com.doelay.android.popularmoviesapp.TMDb;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity
        implements MovieAdapter.OnMovieSelectedListener, FetchMoviesDataTask.OnMoviesDataAvailable {

    @BindView(R.id.tv_error_message) TextView errorMessage;
    @BindView(R.id.pb_loading) ProgressBar loadingBar;
    @BindView(R.id.rv_movies) RecyclerView movieRecyclerView;

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String MOVIE_RECYCLER_VIEW_STATE = "movie_recycler_view_state";
    public static final String DOWNLOADED_MOVIE_LIST = "downloaded_movie_list";

    private MovieAdapter movieAdapter;
    private GridLayoutManager gridLayoutManager;
    private Parcelable recyclerViewState;
    private List<Movies> downloadedMovieList;


    // TODO: 5/17/2017 layout for landscape with more than 2 columns

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        //set the number of column
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            gridLayoutManager = new GridLayoutManager(MainActivity.this,2);
        } else {
            gridLayoutManager = new GridLayoutManager(MainActivity.this,3);
        }
        movieRecyclerView.setLayoutManager(gridLayoutManager);

        movieRecyclerView.setHasFixedSize(true);

        movieAdapter = new MovieAdapter(this);
        movieRecyclerView.setAdapter(movieAdapter);

        if (savedInstanceState == null) {
            loadMovieData();
        } else {
            //retrieve movie list and pass it to the adapter
            List<Movies> list = savedInstanceState.getParcelableArrayList(DOWNLOADED_MOVIE_LIST);
            downloadedMovieList = list;
            movieAdapter.setMovieData(list);
            //retrieve recycler scroll position
            recyclerViewState = savedInstanceState.getParcelable(MOVIE_RECYCLER_VIEW_STATE);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState: called");
        super.onSaveInstanceState(outState);
        //save the scroll position of the recycler view
        recyclerViewState = gridLayoutManager.onSaveInstanceState();
        outState.putParcelable(MOVIE_RECYCLER_VIEW_STATE, recyclerViewState);
        //save the downloaded movie list
        outState.putParcelableArrayList(DOWNLOADED_MOVIE_LIST, (ArrayList<Movies>) downloadedMovieList);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, "onRestoreInstanceState: called");
        super.onRestoreInstanceState(savedInstanceState);


    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: called");
        super.onResume();
        //restore the scroll position
        if (recyclerViewState != null) {
            gridLayoutManager.onRestoreInstanceState(recyclerViewState);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemClicked = item.getItemId();
        switch (menuItemClicked) {
            case R.id.action_popularity :
                showLoadingBar();
                new FetchMoviesDataTask(this).execute(TMDb.POPULAR);
                return true;
            case R.id.action_rating :
                showLoadingBar();
                new FetchMoviesDataTask(this).execute(TMDb.TOP_RATED);
                return true;
            case R.id.action_favorite :
                Intent intent = new Intent(this, FavoriteMovieActivity.class);
                startActivity(intent);

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /**
     * Load the movie data when the app starts.
     */
    private void loadMovieData() {
        showLoadingBar();
        new FetchMoviesDataTask(this).execute(TMDb.POPULAR);
    }

    private void showMovieData() {
        errorMessage.setVisibility(View.INVISIBLE);
        movieRecyclerView.setVisibility(View.VISIBLE);
    }
    private void showErrorMessage() {
        movieRecyclerView.setVisibility(View.INVISIBLE);
        errorMessage.setVisibility(View.VISIBLE);
    }
    private void showLoadingBar() {
        loadingBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onMoviesDataAvailable(List<Movies> movieList) {
        if (movieList != null) {
            downloadedMovieList = movieList;//save a copy for onSaveInstanceState()
            loadingBar.setVisibility(View.INVISIBLE);
            movieAdapter.setMovieData(movieList);
            showMovieData();
        } else {
            showErrorMessage();
        }
    }

    @Override
    public void onMovieSelectedListener(Movies movieSelected) {

        Intent intentToStartMovieDetailActivity = new Intent(this, MovieDetailActivity.class);
        intentToStartMovieDetailActivity.putExtra("MovieDetail", movieSelected);
        startActivity(intentToStartMovieDetailActivity);

    }


}
