package com.doelay.android.popularmoviesapp;


import android.os.Parcel;
import android.os.Parcelable;


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
    private String[] trailerLinks;


    public Movies(String originalTitle,
                  String overview,
                  String releaseDate,
                  double rating,
                  String posterPath,
                  long id,
                  String[] links) {

        this.originalTitle = originalTitle;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.posterPath = posterPath;
        this.id = id;
        this.trailerLinks = links;
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
        trailerLinks = in.createStringArray();
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
        out.writeArray(trailerLinks);

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

    public String[] getTrailerLinks() {
        return trailerLinks;
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

    public void setTrailerLinks(String[] links) {
        this.trailerLinks = links;
    }

}
