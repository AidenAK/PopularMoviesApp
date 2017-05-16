package com.doelay.android.popularmoviesapp;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.net.URL;

/**
 *
 */

public class Movies implements Parcelable {

    private String originalTitle;
    private String overview;
    private String releaseDate;
    private double rating;
    private String posterPath;


    public Movies(String originalTitle, String overview, String releaseDate, double rating, String posterPath) {
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.posterPath = posterPath;
    }

    /**
     * Consturctor used to create object by CREATER
     */
    private Movies (Parcel in) {
        originalTitle = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        rating = in.readDouble();
        posterPath = in.readString();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Write object values to parcel
     */
    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(originalTitle);
        out.writeString(overview);
        out.writeString(releaseDate);
        out.writeDouble(rating);
        out.writeString(posterPath);

    }

    /**
     * Used when unpacking the parcel - creating the object
     */
    public static final Parcelable.Creator<Movies> CREATOR
            = new Parcelable.Creator<Movies>() {

        @Override
        public Movies createFromParcel(Parcel parcel) {
            return new Movies(parcel);
        }

        @Override
        public Movies[] newArray(int i) {
            return new Movies[0];
        }
    };

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
