package com.curtesmalteser.popularmoviesstage1.utils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by António "Curtes Malteser" Bastião on 23/02/2018.
 */

public class VideosModel {

    @SerializedName("results")
    @Expose
    private List<VideosModel> videosModels;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("type")
    @Expose
    private String type;

    public List<VideosModel> getVideosModels() {
        return videosModels;
    }

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
