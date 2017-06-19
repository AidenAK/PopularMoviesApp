package com.doelay.android.popularmoviesapp.task;

import android.os.AsyncTask;

import com.doelay.android.popularmoviesapp.utils.NetworkUtils;
import com.doelay.android.popularmoviesapp.TMDb;

import java.net.URL;

/**
 * Created by doelay on 6/14/2017.
 */

public class GetReviewTask extends AsyncTask<String, Void, String[]> {

    // TODO: 6/15/2017 finish this class
    @Override
    protected String[] doInBackground(String... movieId) {

        if(movieId.length == 0) {
            return null;
        }
        try {

            URL reviewUrl = NetworkUtils.buildUrl(movieId[0], TMDb.REVIEWS);


            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
