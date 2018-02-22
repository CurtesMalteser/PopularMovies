package com.curtesmalteser.popularmoviesstage1.utils;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by António "Curtes Malteser" Bastião on 16/02/2018.
 */


public class Result implements Parcelable {
    @SerializedName("vote_average")
    @Expose
    private String voteAverage; // vote average
    @SerializedName("title")
    @Expose
    private String title; //title
    @SerializedName("poster_path")
    @Expose
    private String posterPath; // movie poster
    @SerializedName("overview")
    @Expose
    private String overview; // plot synopsis
    @SerializedName("release_date")
    @Expose
    private String releaseDate; // release date

    public Result(String voteAverage, String title, String posterPath, String overview, String releaseDate) {
        this.voteAverage = voteAverage;
        this.title = title;
        this.posterPath = posterPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }

    protected Result(Parcel in) {
        voteAverage = in.readString();
        title = in.readString();
        posterPath = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
    }

    public static final Creator<Result> CREATOR = new Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel in) {
            return new Result(in);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(voteAverage);
        dest.writeString(title);
        dest.writeString(posterPath);
        dest.writeString(overview);
        dest.writeString(releaseDate);
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

}
