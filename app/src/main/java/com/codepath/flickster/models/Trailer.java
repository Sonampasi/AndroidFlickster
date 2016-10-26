package com.codepath.flickster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Sonam on 10/16/2016.
 */

public class Trailer {

    public String key;

    public Trailer(JSONObject jsonObject) throws JSONException {
        this.key = jsonObject.getString("key");
    }

    public String getKey(){

        return this.key;
    }
}
