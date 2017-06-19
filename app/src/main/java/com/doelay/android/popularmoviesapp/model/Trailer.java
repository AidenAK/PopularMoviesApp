package com.doelay.android.popularmoviesapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by doelay on 6/15/2017.
 */

public class Trailer implements Parcelable{

    private String trailerTitle;
    private String trailerThumbnailPath;
    private String[] trailerPath;

    public Trailer(String trailerTitle, String trailerThumbnailPath, String[] trailerPath) {

        this.trailerTitle = trailerTitle;
        this.trailerThumbnailPath = trailerThumbnailPath;
        this.trailerPath = trailerPath;
    }
    private Trailer (Parcel in) {

        trailerTitle = in.readString();
        trailerThumbnailPath = in.readString();
        trailerPath = in.createStringArray();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(trailerTitle);
        out.writeString(trailerThumbnailPath);
        out.writeArray(trailerPath);
    }

    public static final Parcelable.Creator<Trailer> CREATOR
            = new Parcelable.Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel parcel) {
            return new Trailer(parcel);
        }

        @Override
        public Trailer[] newArray(int i) {
            return new Trailer[0];
        }
    };
    public String getTrailerTitle() {
        return trailerTitle;
    }

    public void setTrailerTitle(String trailerTitle) {
        this.trailerTitle = trailerTitle;
    }

    public String getTrailerThumbnailPath() {
        return trailerThumbnailPath;
    }

    public void setTrailerThumbnailPath(String trailerThumbnailPath) {
        this.trailerThumbnailPath = trailerThumbnailPath;
    }

    public String[] getTrailerPath() {
        return trailerPath;
    }

    public void setTrailerPath(String[] trailerPath) {
        this.trailerPath = trailerPath;
    }


}
