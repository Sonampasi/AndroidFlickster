package com.codepath.flickster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Sonam on 10/11/2016.
 */

public class Movie implements Serializable{

    int id;
    String posterPath;
    String backdropPath;
    String originalTitle;
    String overview;
    String releaseDate;
    float vote_average;

    public enum MovieType {
        LESS_POPULAR, POPULAR
    }

    public MovieType movieType = MovieType.LESS_POPULAR;

    public Integer getId(){
        return id;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s",posterPath);
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s",backdropPath);
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() { return releaseDate; }

    public Float getVoteAverage() {
        return vote_average;
    }

    public Movie(JSONObject jsonObject) throws JSONException{

        this.id = jsonObject.getInt("id");
        this.posterPath = jsonObject.getString("poster_path");
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.originalTitle = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
        this.releaseDate = jsonObject.getString("release_date");
        this.vote_average = (float)jsonObject.getDouble("vote_average");

        if(this.vote_average > 5) {
            movieType = MovieType.POPULAR;
        }
    }

    public static ArrayList<Movie> fromJSONArray(JSONArray array){
        ArrayList<Movie> results = new ArrayList<>();

        for (int i = 0; i < array.length(); i++){
            try {
               results.add(new Movie(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }
    public static ArrayList<Movie> fromPopularJSONArray(JSONArray array){
        ArrayList<Movie> results = new ArrayList<>();

        for (int i = 0; i < array.length(); i++){
            try {
                Movie movie_obj = new Movie(array.getJSONObject(i));
                float rating = movie_obj.getVoteAverage();
                if(rating>5) {
                    results.add(movie_obj);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }


}
