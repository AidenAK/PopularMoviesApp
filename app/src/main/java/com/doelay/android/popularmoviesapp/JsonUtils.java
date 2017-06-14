package com.doelay.android.popularmoviesapp;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * This class handles JSON data from TMDb.
 */

public final class JsonUtils {

    private static final String TAG = JsonUtils.class.getSimpleName();

    public static List<Movies> parseJsonString (Context context, String jsonString)
            throws JSONException{

        List<Movies> moviesList = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray resultsArray = jsonObject.getJSONArray("results");

        for (int i=0; i < resultsArray.length(); i++) {
            JSONObject movieObject = resultsArray.getJSONObject(i);
            String originalTitle = movieObject.getString("original_title");
            String overview = movieObject.getString("overview");
            String releaseDate = movieObject.getString("release_date");
            double rating = movieObject.getDouble("vote_average");

            String posterPath = movieObject.getString("poster_path");
            String posterUri = buildPosterUri(posterPath);

            long movieId = movieObject.getLong("id");

            Movies movie = new Movies(originalTitle,
                            overview,
                            releaseDate,
                            rating,
                            posterUri,
                            movieId);
            moviesList.add(movie);
        }
        return moviesList;
    }
    public static String[] parseJsonForTrailer(String jsonString)
            throws JSONException {

        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray resultArray = jsonObject.getJSONArray("results");

        //create an array to store the trailer links
        String[] trailerList = new String[resultArray.length()];

        for (int i=0; i < resultArray.length(); i++) {
            JSONObject trailerObject = resultArray.getJSONObject(i);
            String key = trailerObject.getString("key");

            //create trailer url
            trailerList[i] = buildTrailerUri(key);
        }
        return trailerList;
    }

    /**
     * Build poster Uri needed to get the movie poster from TMDb.
     * @param posterPath Movie poster file path
     * @return The Uri to query the movie poster in specific size.
     */
    private static String buildPosterUri(String posterPath) {

        Uri uri = Uri.parse(TMDb.POSTER_BASE_URL)
                .buildUpon()
                .appendPath(TMDb.POSTER_SIZE)
                .appendEncodedPath(posterPath)
                .build();
        return uri.toString();
    }
    private static String buildTrailerUri(String trailerKey) {

        Uri uri = Uri.parse(TMDb.TRAILER_BASE_URL)
                .buildUpon()
                .appendEncodedPath("watch?v=" + trailerKey)
                .build();
        Log.d(TAG, "buildTrailerUri: Trailer link is "+ uri.toString());
        return uri.toString();

    }
}
