package com.doelay.android.popularmoviesapp;

import android.content.Context;
import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles JSON data from TMDb.
 */

public final class JsonUtils {

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
            Uri posterUri = buildPosterUri(posterPath);

            Movies movie = new Movies(originalTitle,
                            overview,
                            releaseDate,
                            rating,
                            posterUri);
            moviesList.add(movie);
        }
        return moviesList;
    }

    /**
     * Build poster Uri needed to get the movie poster from TMDb.
     * @param posterPath Movie poster file path
     * @return The Uri to query the movie poster in specific size.
     */
    private static Uri buildPosterUri(String posterPath) {

        return Uri.parse(TMDb.POSTER_BASE_URL).buildUpon()
                .appendPath(TMDb.POSTER_SIZE)
                .appendPath(posterPath)
                .build();


    }
}
