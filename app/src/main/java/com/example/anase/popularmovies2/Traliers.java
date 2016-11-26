package com.example.anase.popularmovies2;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by anase on 02/11/2016.
 */

public class Traliers implements Parcelable {

    private String name;
    private String key;

    public Traliers(String name, String key) {
        this.name = name;
        this.key = key;
    }

    protected Traliers(Parcel in) {
        name = in.readString();
        key = in.readString();
    }

    public static final Creator<Traliers> CREATOR = new Creator<Traliers>() {
        @Override
        public Traliers createFromParcel(Parcel in) {
            return new Traliers(in);
        }

        @Override
        public Traliers[] newArray(int size) {
            return new Traliers[size];
        }
    };

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(key);
    }
}
