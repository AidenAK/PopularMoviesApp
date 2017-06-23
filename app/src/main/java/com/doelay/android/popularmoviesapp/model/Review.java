package com.doelay.android.popularmoviesapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by doelay on 6/19/2017.
 */

public class Review implements Parcelable{

    private String authorName;
    private String review;

    public Review(String authorName, String review) {
        this.authorName = authorName;
        this.review = review;
    }

    private Review(Parcel in) {
        authorName = in.readString();
        review = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(authorName);
        out.writeString(review);
    }

    public static final Parcelable.Creator<Review> CREATOR
            = new Parcelable.Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel parcel) {
            return new Review(parcel);
        }

        @Override
        public Review[] newArray(int i) {
            return new Review[0];
        }
    };



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
