package com.doelay.android.popularmoviesapp;

import android.net.Uri;
import android.util.Log;
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

    public static URL buildUrl (String searchCriteria) {
        Uri buildUri = Uri.parse(BASE_URI).buildUpon()
                .appendPath(searchCriteria)
                .appendQueryParameter(API_KEY, TMDb.API_KEY)
                .build();
        URL url = null;
        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "buildUrl: URL is "+ url);
        return url;
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
