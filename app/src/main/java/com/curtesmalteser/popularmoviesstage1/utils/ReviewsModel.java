package com.curtesmalteser.popularmoviesstage1.utils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by António "Curtes Malteser" Bastião on 24/02/2018.
 */

public class ReviewsModel {
    @SerializedName("results")
    @Expose
    private List<ReviewsModel> reviewsModels;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("content")
    @Expose
    private String content;

    public List<ReviewsModel> getReviewsModels() {
        return reviewsModels;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}
