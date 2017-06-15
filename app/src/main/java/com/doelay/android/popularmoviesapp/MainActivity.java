package com.doelay.android.popularmoviesapp;

import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.Toast;

import java.net.URL;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements MovieAdapter.OnMovieSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private MovieAdapter movieAdapter;
    private ProgressBar loadingBar;
    private TextView errorMessage;
    private RecyclerView movieRecyclerView;


    // TODO: 5/17/2017 Save the data on screen rotation
    // TODO: 5/17/2017 layout for landscape with more than 2 columns
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieRecyclerView = (RecyclerView) findViewById(R.id.rv_movies);
        errorMessage = (TextView) findViewById(R.id.tv_error_message);
        loadingBar = (ProgressBar) findViewById(R.id.pb_loading);

        GridLayoutManager gridLayoutManager
                = new GridLayoutManager(MainActivity.this,2);
        movieRecyclerView.setLayoutManager(gridLayoutManager);

        movieRecyclerView.setHasFixedSize(true);

        movieAdapter = new MovieAdapter(this);
        movieRecyclerView.setAdapter(movieAdapter);
        loadMovieData();
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
                loadingBar.setVisibility(View.INVISIBLE);
                movieAdapter.setMovieData(movieList);
            } else {
                showErrorMessage();
            }


        }
    }
}
