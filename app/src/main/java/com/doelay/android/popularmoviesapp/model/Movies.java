package com.doelay.android.popularmoviesapp.model;


import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;


/**
 *
 */

public class Movies implements Parcelable {

    private String originalTitle;
    private String overview;
    private String releaseDate;
    private double rating;
    private String posterPath;
    private long id;
    private Trailer trailerLinks;
    private List<Review> review;


    public Movies(String originalTitle,
                  String overview,
                  String releaseDate,
                  double rating,
                  String posterPath,
                  long id,
                  Trailer links,
                  List<Review> review) {

        this.originalTitle = originalTitle;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.posterPath = posterPath;
        this.id = id;
        this.trailerLinks = links;
        this.review = review;
    }

    /**
     * Constructor used to recreate the object by CREATER
     */
    private Movies (Parcel in) {
        originalTitle = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        rating = in.readDouble();
        posterPath = in.readString();
        id = in.readLong();

        Bundle linkBundle = in.readBundle(getClass().getClassLoader());
        trailerLinks = linkBundle.getParcelable("trailerPath");

//        Bundle reviewBundle = in.readBundle(getClass().getClassLoader());
//        review = reviewBundle.getParcelableArrayList("review");
        review = new ArrayList<Review>();
        in.readTypedList(review, Review.CREATOR);
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
        out.writeLong(id);

        Bundle linkBundle = new Bundle();
        linkBundle.putParcelable("trailerPath", trailerLinks);
        out.writeBundle(linkBundle);

//        Bundle reviewBundle = new Bundle();
//        reviewBundle.putParcelableArrayList("review", (ArrayList<Review>) review);
//        out.writeBundle(reviewBundle);
        out.writeList(review);
}

    /**
     * Used when unpacking the parcel - creating the object from parcel
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

    public long getId() {
        return id;
    }

    public Trailer getTrailer() {
        return trailerLinks;
    }

    public List<Review> getReview() {
        return review;
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

    public void setId(long id) {
        this.id = id;
    }

    public void setTrailer(Trailer links) {
        this.trailerLinks = links;
    }

    public void setReview(List<Review> review) {
        this.review = review;
    }

}
