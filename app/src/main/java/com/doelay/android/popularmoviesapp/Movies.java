package com.doelay.android.popularmoviesapp;

/**
 * 
 */

public class Movies {

    private String originalTitle;
    private String overview;
    private String releaseDate;
    private float rating;
    private String posterPath;


    public Movies(String originalTitle, String overview, String releaseDate, float rating, String posterPath) {
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

    public float getRating() {
        return rating;
    }

    public String getPosterPath() {
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

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}
