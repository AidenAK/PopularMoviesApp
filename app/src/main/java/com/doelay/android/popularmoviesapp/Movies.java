package com.doelay.android.popularmoviesapp;

import android.net.Uri;

import java.net.URL;

/**
 *
 */

public class Movies {

    private String originalTitle;
    private String overview;
    private String releaseDate;
    private double rating;
    private Uri posterPath;


    public Movies(String originalTitle, String overview, String releaseDate, double rating, Uri posterPath) {
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.posterPath = posterPath;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public double getRating() {
        return rating;
    }

    public Uri getPosterPath() {
        return posterPath;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setPosterPath(Uri posterPath) {
        this.posterPath = posterPath;
    }


}
