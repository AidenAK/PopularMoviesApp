package com.doelay.android.popularmoviesapp.model;

/**
 * Created by doelay on 6/15/2017.
 */

public class MovieReview {

    private String author;
    private String review;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public MovieReview(String author, String review) {

        this.author = author;
        this.review = review;
    }
}
