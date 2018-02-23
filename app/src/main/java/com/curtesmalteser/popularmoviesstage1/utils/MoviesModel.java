package com.curtesmalteser.popularmoviesstage1.utils;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by António "Curtes Malteser" Bastião on 16/02/2018.
 */


public class MoviesModel implements Parcelable {

    @SerializedName("results")
    @Expose
    private List<MoviesModel> moviesModels = null;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("vote_average")
    @Expose
    private String voteAverage; // vote average
    @SerializedName("title")
    @Expose
    private String title; //title
    @SerializedName("poster_path")
    @Expose
    private String posterPath; // movie poster
    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;
    @SerializedName("overview")
    @Expose
    private String overview; // plot synopsis
    @SerializedName("release_date")
    @Expose
    private String releaseDate; // release date

    protected MoviesModel(Parcel in) {
        id = in.readString();
        voteAverage = in.readString();
        title = in.readString();
        posterPath = in.readString();
        backdropPath = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
    }

    public static final Creator<MoviesModel> CREATOR = new Creator<MoviesModel>() {
        @Override
        public MoviesModel createFromParcel(Parcel in) {
            return new MoviesModel(in);
        }

        @Override
        public MoviesModel[] newArray(int size) {
            return new MoviesModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(voteAverage);
        dest.writeString(title);
        dest.writeString(posterPath);
        dest.writeString(backdropPath);
        dest.writeString(overview);
        dest.writeString(releaseDate);
    }

    public List<MoviesModel> getMoviesModels() {
        return moviesModels;
    }

    public String getId() {
        return id;
    }

    public String getVoteAverage() {
        return voteAverage;
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

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

}
