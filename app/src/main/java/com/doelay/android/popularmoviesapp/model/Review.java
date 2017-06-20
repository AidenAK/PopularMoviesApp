package com.doelay.android.popularmoviesapp.model;

/**
 * Created by doelay on 6/19/2017.
 */

public class Review {

    private String authorName;
    private String review;

    public Review(String authorName, String review) {
        this.authorName = authorName;
        this.review = review;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getReview() {
        return review;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
