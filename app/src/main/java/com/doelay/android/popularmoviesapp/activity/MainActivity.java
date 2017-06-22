package com.doelay.android.popularmoviesapp.activity;

import android.content.Intent;
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
import com.doelay.android.popularmoviesapp.utils.JsonUtils;
import com.doelay.android.popularmoviesapp.adapter.MovieAdapter;
import com.doelay.android.popularmoviesapp.utils.NetworkUtils;
import com.doelay.android.popularmoviesapp.R;
import com.doelay.android.popularmoviesapp.TMDb;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements MovieAdapter.OnMovieSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String MOVIE_RECYCLER_VIEW_STATE = "movie_recycler_view_state";
    public static final String DOWNLOADED_MOVIE_LIST = "downloaded_movie_list";

    private MovieAdapter movieAdapter;
    private ProgressBar loadingBar;
    private TextView errorMessage;
    private RecyclerView movieRecyclerView;
    private GridLayoutManager gridLayoutManager;
    private Parcelable recyclerViewState;
    private List<Movies> downloadedMovieList;


    // TODO: 5/17/2017 Save the data on screen rotation
    // TODO: 5/17/2017 layout for landscape with more than 2 columns

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieRecyclerView = (RecyclerView) findViewById(R.id.rv_movies);
        errorMessage = (TextView) findViewById(R.id.tv_error_message);
        loadingBar = (ProgressBar) findViewById(R.id.pb_loading);

        gridLayoutManager = new GridLayoutManager(MainActivity.this,2);
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
                new FetchMoviesDataTask().execute(TMDb.POPULAR);
                return true;
            case R.id.action_rating :
                new FetchMoviesDataTask().execute(TMDb.TOP_RATED);
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
        showMovieData();
        new FetchMoviesDataTask().execute(TMDb.POPULAR);
    }

    private void showMovieData() {
        errorMessage.setVisibility(View.INVISIBLE);
        movieRecyclerView.setVisibility(View.VISIBLE);
    }
    private void showErrorMessage() {
        movieRecyclerView.setVisibility(View.INVISIBLE);
        errorMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void onMovieSelectedListener(Movies movieSelected) {

        Intent intentToStartMovieDetailActivity = new Intent(this, MovieDetailActivity.class);
        intentToStartMovieDetailActivity.putExtra("MovieDetail", movieSelected);
        startActivity(intentToStartMovieDetailActivity);

    }

    public class FetchMoviesDataTask extends AsyncTask<String, Void, List<Movies>>{

        @Override
        protected void onPreExecute() {
            loadingBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }


        @Override
        protected List<Movies> doInBackground(String... strings) {

            if (strings.length == 0){
                return null;
            }
            try {
                URL url = NetworkUtils.buildUrl(null, strings[0]);
                String jsonString = NetworkUtils.getJsonData(url);
                List moviesList = JsonUtils.parseJsonString( MainActivity.this, jsonString);
                Log.d(TAG, "doInBackground: Movie list size "+ moviesList.size() );
                return moviesList;
            } catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Movies> movieList) {
            if (movieList != null) {
                downloadedMovieList = movieList;
                loadingBar.setVisibility(View.INVISIBLE);
                movieAdapter.setMovieData(movieList);
            } else {
                showErrorMessage();
            }


        }
    }
}
