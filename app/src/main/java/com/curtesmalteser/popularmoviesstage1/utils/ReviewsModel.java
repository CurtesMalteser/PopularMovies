package com.curtesmalteser.popularmoviesstage1.utils;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by António "Curtes Malteser" Bastião on 24/02/2018.
 */

public class ReviewsModel implements Parcelable {
    @SerializedName("results")
    @Expose
    private ArrayList<ReviewsModel> reviewsModels;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("content")
    @Expose
    private String content;

    public static final Creator<ReviewsModel> CREATOR = new Creator<ReviewsModel>() {
        @Override
        public ReviewsModel createFromParcel(Parcel in) {
            return new ReviewsModel(in);
        }

        @Override
        public ReviewsModel[] newArray(int size) {
            return new ReviewsModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    protected ReviewsModel(Parcel in) {
        author = in.readString();
        content = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(author);
        dest.writeString(content);
    }

    public ArrayList<ReviewsModel> getReviewsModels() {
        return reviewsModels;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}
