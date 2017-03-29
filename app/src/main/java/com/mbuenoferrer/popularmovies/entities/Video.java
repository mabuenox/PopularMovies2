package com.mbuenoferrer.popularmovies.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Video implements Parcelable { // TODO: parcelable makes sense here?
    private final String id;
    private final String name;
    private final String key;
    private final String site;


    public Video(String id, String name, String key, String site) {
        this.id = id;
        this.name = name;
        this.key = key;
        this.site = site;
    }

    public Video(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.key = in.readString();
        this.site = in.readString();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public String getSite() {
        return site;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(key);
        dest.writeString(site);
    }

    @Override
    public int describeContents() {
        return 0;
    }


    static final Creator<Video> CREATOR
            = new Creator<Video>() {

        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        public Video[] newArray(int size) {
            return new Video[size];
        }
    };
}
