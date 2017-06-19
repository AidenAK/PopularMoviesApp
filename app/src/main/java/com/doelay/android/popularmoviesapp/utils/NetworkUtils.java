package com.doelay.android.popularmoviesapp.utils;

import android.net.Uri;

import com.doelay.android.popularmoviesapp.TMDb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 */

public final class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String BASE_URI = TMDb.BASE_URL;
    private final static String API_KEY = "api_key";

    // TODO: 5/16/2017 Need to build Url to extract more than 20 movies
    /**
     * Build URL base on the parameters passed in.
     * @param movieId
     * @param searchCriteria
     * @return URL
     */
    public static URL buildUrl (String movieId, String searchCriteria) {
        Uri buildUri;
        URL url = null;
        //build Url without movie id
        if (searchCriteria == TMDb.POPULAR || searchCriteria == TMDb.TOP_RATED) {
            buildUri = Uri.parse(BASE_URI).buildUpon()
                    .appendPath(searchCriteria)
                    .appendQueryParameter(API_KEY, TMDb.API_KEY)
                    .build();
            url = UriToUrl(buildUri);

        }
        //build Url with movie id and search criteria such as reviews
        if(movieId != null && searchCriteria != null) {
            buildUri = Uri.parse(BASE_URI).buildUpon()
                    .appendPath(movieId)
                    .appendPath(searchCriteria)
                    .appendQueryParameter(API_KEY, TMDb.API_KEY)
                    .build();
            url = UriToUrl(buildUri);
        }
        return url;
    }

    private static URL UriToUrl(Uri uri) {
        URL url;
        try {
            url = new URL(uri.toString());
            return url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String getJsonData (URL url) {
        HttpURLConnection httpURLConnection = null;

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();

            int  responseCode = httpURLConnection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                return null;
            }

            InputStreamReader in = new InputStreamReader(httpURLConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(in);

            StringBuilder stringBuilder = new StringBuilder();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            return stringBuilder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }

        return null;
    }

}
