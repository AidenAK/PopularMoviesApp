package com.doelay.android.popularmoviesapp.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.doelay.android.popularmoviesapp.activity.FavoriteMovieActivity;
import com.doelay.android.popularmoviesapp.activity.MainActivity;
import com.doelay.android.popularmoviesapp.model.Movies;
import com.doelay.android.popularmoviesapp.utils.JsonUtils;
import com.doelay.android.popularmoviesapp.utils.NetworkUtils;

import java.net.URL;
import java.util.List;

/**
 * Created by doelay on 6/28/2017.
 */

public class FetchMoviesDataTask extends AsyncTask<String, Void, List<Movies>> {
    private static final String TAG = FetchMoviesDataTask.class.getSimpleName();

    private OnMoviesDataAvailable mCallback;
    private Context mContext;

    public interface OnMoviesDataAvailable {
        void onMoviesDataAvailable(List<Movies> movieList);
    }

    public FetchMoviesDataTask(Context context) {
        try {
            if (context instanceof OnMoviesDataAvailable) {
                mCallback = (OnMoviesDataAvailable) context;
            }
        } catch (ClassCastException e) {
            throw new ClassCastException();
        }

    }

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
            URL url = NetworkUtils.buildUrl(null, strings[0]);
            String jsonString = NetworkUtils.getJsonData(url);
            List moviesList = JsonUtils.parseJsonString(mContext, jsonString);
            Log.d(TAG, "doInBackground: Movie list size "+ moviesList.size() );
            return moviesList;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Movies> movieList) {
        mCallback.onMoviesDataAvailable(movieList);


    }
}