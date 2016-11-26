package com.example.anase.popularmovies2;

import android.os.Parcel;
import android.os.Parcelable;

import ckm.simple.sql_provider.annotation.SimpleSQLColumn;
import ckm.simple.sql_provider.annotation.SimpleSQLTable;

/**
 * Created by anase on 23/10/2016.
 */
@SimpleSQLTable(table = "MovieDB", provider = "PopularMovies")
public class Movies implements Parcelable {

    @SimpleSQLColumn("id")
    private String id;
    @SimpleSQLColumn("poster_path")
    private String image;
    @SimpleSQLColumn("title")
    private String titeMovie;
    @SimpleSQLColumn("release_date")
    private String dateMovie;
    @SimpleSQLColumn("rateMovie")
    private String rateMovie;
    @SimpleSQLColumn("overviewMovie")
    private String overviewMovie;
    @SimpleSQLColumn(value = "posterPath")
    private String posterPath;


    public Movies() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTiteMovie() {
        return titeMovie;
    }

    public void setTiteMovie(String titeMovie) {
        this.titeMovie = titeMovie;
    }

    public String getDateMovie() {
        return dateMovie;
    }

    public void setDateMovie(String dateMovie) {
        this.dateMovie = dateMovie;
    }

    public String getRateMovie() {
        return rateMovie;
    }

    public void setRateMovie(String rateMovie) {
        this.rateMovie = rateMovie;
    }

    public String getOverviewMovie() {
        return overviewMovie;
    }

    public void setOverviewMovie(String overviewMovie) {
        this.overviewMovie = overviewMovie;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public Movies(String id, String image, String titeMovie, String dateMovie, String rateMovie, String overviewMovie, String posterPath) {
        this.id = id;
        this.image = image;
        this.titeMovie = titeMovie;
        this.dateMovie = dateMovie;
        this.rateMovie = rateMovie;
        this.overviewMovie = overviewMovie;
        this.posterPath = posterPath;
    }

    protected Movies(Parcel in) {
        id = in.readString();
        image = in.readString();
        titeMovie = in.readString();
        dateMovie = in.readString();
        rateMovie = in.readString();
        overviewMovie = in.readString();
        posterPath = in.readString();
    }

    public static final Creator<Movies> CREATOR = new Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel in) {
            return new Movies(in);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(image);
        parcel.writeString(titeMovie);
        parcel.writeString(dateMovie);
        parcel.writeString(rateMovie);
        parcel.writeString(overviewMovie);
        parcel.writeString(posterPath);
    }
}