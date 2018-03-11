package com.curtesmalteser.popularmoviesstage1.utils;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by António "Curtes Malteser" Bastião on 23/02/2018.
 */

public class VideosModel implements Parcelable{

    @SerializedName("results")
    @Expose
    private ArrayList<VideosModel> videosModels;
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

    public static final Creator<VideosModel> CREATOR = new Creator<VideosModel>() {
        @Override
        public VideosModel createFromParcel(Parcel in) {
            return new VideosModel(in);
        }

        @Override
        public VideosModel[] newArray(int size) {
            return new VideosModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    protected VideosModel(Parcel in) {
        id = in.readString();
        key = in.readString();
        name = in.readString();
        type = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(key);
        dest.writeString(name);
        dest.writeString(type);
    }

    public ArrayList<VideosModel> getVideosModels() {
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
