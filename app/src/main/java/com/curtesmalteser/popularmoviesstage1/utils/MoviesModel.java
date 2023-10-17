package com.curtesmalteser.popularmoviesstage1.utils;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by António "Curtes Malteser" Bastião on 16/02/2018.
 */

public class MoviesModel implements Parcelable {

    @SerializedName("results")
    @Expose
    private ArrayList<MoviesModel> moviesModels;
    @SerializedName("id")
    @Expose
    private final int id;
    @SerializedName("vote_average")
    @Expose
    private final String voteAverage; // vote average
    @SerializedName("title")
    @Expose
    private final String title; //title
    @SerializedName("poster_path")
    @Expose
    private final String posterPath; // movie poster
    @SerializedName("backdrop_path")
    @Expose
    private final String backdropPath;
    @SerializedName("overview")
    @Expose
    private final String overview; // plot synopsis
    @SerializedName("release_date")
    @Expose
    private final String releaseDate; // release date

    public MoviesModel(int id, String voteAverage, String title, String posterPath, String backdropPath, String overview, String releaseDate) {
        this.id = id;
        this.voteAverage = voteAverage;
        this.title = title;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }

    protected MoviesModel(Parcel in) {
        id = in.readInt();
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
        dest.writeInt(id);
        dest.writeString(voteAverage);
        dest.writeString(title);
        dest.writeString(posterPath);
        dest.writeString(backdropPath);
        dest.writeString(overview);
        dest.writeString(releaseDate);
    }

    public ArrayList<MoviesModel> getMoviesModels() {
        return moviesModels;
    }

    public int getId() {
        return id;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public String getTitle() {
        return title;
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
