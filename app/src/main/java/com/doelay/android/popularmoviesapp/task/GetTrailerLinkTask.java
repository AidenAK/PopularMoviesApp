package com.doelay.android.popularmoviesapp.task;

import android.content.Context;
import android.os.AsyncTask;
import com.doelay.android.popularmoviesapp.JsonUtils;
import com.doelay.android.popularmoviesapp.NetworkUtils;
import com.doelay.android.popularmoviesapp.TMDb;
import java.net.URL;


/**
 *
 * This class fetch trailer links.
 */

public class GetTrailerLinkTask extends AsyncTask <String, Void, String[]> {

    private static final String TAG = GetTrailerLinkTask.class.getSimpleName();

    private final OnDataAvailable mCallback;

    public interface OnDataAvailable {
        void onTrailerLinkAvailable(String[] trailerLinks);
    }

    public GetTrailerLinkTask(Context context) {
        mCallback = (OnDataAvailable) context;
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

            return trailerList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String[] trailerLink) {
        mCallback.onTrailerLinkAvailable(trailerLink);
    }
}
