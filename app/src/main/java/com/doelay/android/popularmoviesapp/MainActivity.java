package com.doelay.android.popularmoviesapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.net.URL;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private MovieAdapter movieAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.rv_movies);

        GridLayoutManager gridLayoutManager
                = new GridLayoutManager(MainActivity.this,2);
        recyclerView.setLayoutManager(gridLayoutManager);

//        LinearLayoutManager linearLayoutManager
//                = new GridLayoutManager(this, 2);
//        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setHasFixedSize(true);

        movieAdapter = new MovieAdapter(this);
        recyclerView.setAdapter(movieAdapter);

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
                new FetchMoviesDataTask().execute("popular");
                return true;
            case R.id.action_rating :
                new FetchMoviesDataTask().execute("top_rated");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    private void loadMovieData() {
        new FetchMoviesDataTask().execute("popular");
    }

    private void showMovieData() {
        // TODO: 5/15/2017
    }
    private void showErrorMessage() {
        // TODO: 5/15/2017
    }

    public class FetchMoviesDataTask extends AsyncTask<String, Void, List<Movies>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected List<Movies> doInBackground(String... strings) {

            if (strings.length == 0){
                return null;
            }
            try {
                URL url = NetworkUtils.buildUrl(strings[0]);
                String jsonString = NetworkUtils.getJsonData(url);
                List moviesList = JsonUtils.parseJsonString(MainActivity.this,jsonString);
                Log.d(TAG, "doInBackground: Movie list size "+ moviesList.size() );
                return moviesList;
            } catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Movies> movieList) {
            movieAdapter.setMovieData(movieList);

        }
    }
}
