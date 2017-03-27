package com.mbuenoferrer.popularmovies.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    private final int id;
    private final String title;
    private final String poster;
    private final String synopsis;
    private final String releaseDate;
    private final double userRating;


    public Movie(int id, String title, String poster, String synopsis, String releaseDate, double userRating) {
        this.id = id;
        this.title = title;
        this.poster = poster;
        this.synopsis = synopsis;
        this.releaseDate = releaseDate;
        this.userRating = userRating;
    }

    public Movie(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.poster = in.readString();
        this.synopsis = in.readString();
        this.releaseDate = in.readString();
        this.userRating = in.readDouble();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster() {
        return poster;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public double getUserRating() {
        return userRating;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(poster);
        dest.writeString(synopsis);
        dest.writeString(releaseDate);
        dest.writeDouble(userRating);
    }

    @Override
    public int describeContents() {
        return 0;
    }


    static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {

        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
