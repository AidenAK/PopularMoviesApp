package com.doelay.android.popularmoviesapp.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.doelay.android.popularmoviesapp.utils.JsonUtils;
import com.doelay.android.popularmoviesapp.utils.NetworkUtils;
import com.doelay.android.popularmoviesapp.TMDb;
import java.net.URL;


/**
 *
 * This class fetch trailer links.
 */

public class GetTrailerLinkTask extends AsyncTask <String, Void, String[]> {

    private static final String TAG = GetTrailerLinkTask.class.getSimpleName();

    private final OnTrailerDataAvailable mCallback;

    public interface OnTrailerDataAvailable {
        void onTrailerDataAvailable(String[] trailerLinks);
    }

    public GetTrailerLinkTask(Context context) {
        mCallback = (OnTrailerDataAvailable) context;
    }

    @Override
    protected String[] doInBackground(String... movieId) {

        if(movieId.length == 0) {
            return null;
        }
        try {
            //get the trailer links
            URL trailerUrl = NetworkUtils.buildUrl(movieId[0], TMDb.VIDEOS);
            String trailerJsonString = NetworkUtils.getJsonData(trailerUrl);
            String[] trailerList = JsonUtils.parseJsonForTrailer(trailerJsonString);
            Log.d(TAG, "doInBackground: "+ trailerList);
            // TODO: 6/19/2017 need to grab trailer thumbnail
            return trailerList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String[] trailerLink) {
        Log.d(TAG, "onPostExecute: " + trailerLink);
        mCallback.onTrailerDataAvailable(trailerLink);
    }
}
