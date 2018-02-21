package com.curtesmalteser.popularmoviesstage1.utils;

import com.curtesmalteser.popularmoviesstage1.MoviesModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by António "Curtes Malteser" Bastião on 17/02/2018.
 */

public class JsonUtils {

    private static final String RESULTS = "results";
    private static final String VOTE_AVERAGE = "vote_average"; // vote average
    private static final String TITLE = "title"; //title
    private static final String POSTER_PATH = "poster_path"; // movie poster
    private static final String OVERVIEW = "overview"; // plot synopsis
    private static final String RELEASE_DATE = "release_date";  // release date

    public static List<MoviesModel> parseMoviews(String json) throws JSONException {

        List<MoviesModel> results = new ArrayList<>();

        JSONObject baseObject = new JSONObject(json);

        JSONArray jsonResults = getList(RESULTS, baseObject);

        for(int i = 0; i < jsonResults.length(); i++) {

            results.add(new MoviesModel(
                    getString(VOTE_AVERAGE, jsonResults.getJSONObject(i)),
                    getString(TITLE,  jsonResults.getJSONObject(i)),
                    getString(POSTER_PATH,  jsonResults.getJSONObject(i)),
                    getString(OVERVIEW,  jsonResults.getJSONObject(i)),
                    getString(RELEASE_DATE, jsonResults.getJSONObject(i)))
            );
        }

        return results;
    }

    private static Double getDouble(String tagName, JSONObject jsonObject) throws JSONException {
        return jsonObject.optDouble(tagName);
    }

    private static String getString(String tagName, JSONObject jsonObject) throws JSONException {
        return jsonObject.optString(tagName);
    }

    private static JSONArray getList(String tagName, JSONObject jsonObject) throws JSONException {
        return jsonObject.optJSONArray(tagName);
    }
}