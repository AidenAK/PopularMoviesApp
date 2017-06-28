package com.doelay.android.popularmoviesapp.task;

import android.content.Context;
import android.os.AsyncTask;

import com.doelay.android.popularmoviesapp.model.Review;
import com.doelay.android.popularmoviesapp.utils.JsonUtils;
import com.doelay.android.popularmoviesapp.utils.NetworkUtils;
import com.doelay.android.popularmoviesapp.TMDb;

import java.net.URL;
import java.util.List;

/**
 * Created by doelay on 6/14/2017.
 */

public class GetReviewTask extends AsyncTask<String, Void, List<Review>> {

    private OnReviewAvailable mCallback;

    public interface OnReviewAvailable {
        void onReviewAvailable(List<Review> reviewList);
    }

    public GetReviewTask (Context context) {
        try {
            if (context instanceof OnReviewAvailable) {
                mCallback = (OnReviewAvailable) context;
            }
        } catch (ClassCastException e) {
            throw new ClassCastException();
        }

    }
    @Override
    protected List doInBackground(String... movieId) {

        if(movieId.length == 0) {
            return null;
        }
        try {

            URL reviewUrl = NetworkUtils.buildUrl(movieId[0], TMDb.REVIEWS);
            String reviewJsonString = NetworkUtils.getJsonData(reviewUrl);
            List<Review> reviewList = JsonUtils.parseJsonForReview(reviewJsonString);


            return reviewList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Review> reviewList) {
        mCallback.onReviewAvailable(reviewList);
    }
}
