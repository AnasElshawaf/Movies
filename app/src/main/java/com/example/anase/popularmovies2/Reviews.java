package com.example.anase.popularmovies2;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by anase on 20/11/2016.
 */

public class Reviews implements Parcelable {

    private String auther;
    private String content;

    public Reviews(String auther, String content) {
        this.auther = auther;
        this.content = content;
    }

    protected Reviews(Parcel in) {
        auther = in.readString();
        content = in.readString();
    }

    public static final Creator<Reviews> CREATOR = new Creator<Reviews>() {
        @Override
        public Reviews createFromParcel(Parcel in) {
            return new Reviews(in);
        }

        @Override
        public Reviews[] newArray(int size) {
            return new Reviews[size];
        }
    };

    public String getAuther() {
        return auther;
    }

    public void setAuther(String auther) {
        this.auther = auther;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(auther);
        parcel.writeString(content);
    }
}
