package com.codepath.flickster;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.codepath.flickster.adapters.MovieArrayAdapter;
import com.codepath.flickster.models.Movie;
import com.codepath.flickster.models.Trailer;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieActivity extends AppCompatActivity {

    ArrayList<Movie> movies;
    ArrayList<Trailer> trailers;
    MovieArrayAdapter movieAdapter;
    ListView lvitems;
    SwipeRefreshLayout swipeContainer;
    Movie movie_obj;
    String movie_trailer_url, video_src;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        lvitems = (ListView) findViewById(R.id.lvMovies);
        movies = new ArrayList<>();
        movieAdapter = new MovieArrayAdapter(this,movies);
        lvitems.setAdapter(movieAdapter);

        final String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        final AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray movieJsonResults = null;

                try {
                    movieJsonResults = response.getJSONArray("results");
                    movies.addAll(Movie.fromJSONArray(movieJsonResults));
                    movieAdapter.notifyDataSetChanged();
                    Log.d("DEBUG", movieJsonResults.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });

        lvitems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                movie_obj = movieAdapter.getItem(position);
                movie_trailer_url = String.format("https://api.themoviedb.org/3/movie/%s/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed",movie_obj.getId());
                client.get(movie_trailer_url, new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        JSONArray trailerJsonResults = null;

                        try {
                            trailerJsonResults = response.getJSONArray("results");
                            trailers = new ArrayList<>();
                            try {
                                Trailer trailer = new Trailer(trailerJsonResults.getJSONObject(0));
                                video_src = trailer.getKey();
                                detailsOfMovie(movie_obj);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Log.d("DEBUG", trailerJsonResults.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                    }
                });

            }
        });

        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeContainer.setRefreshing(true);
                    client.get(url, new JsonHttpResponseHandler() {

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            JSONArray movieJsonResults = null;

                            try {
                                movieJsonResults = response.getJSONArray("results");
                                movies.clear();
                                movies.addAll(Movie.fromPopularJSONArray(movieJsonResults));
                                // Now we call setRefreshing(false) to signal refresh has finished
                                swipeContainer.setRefreshing(false);
                                movieAdapter.notifyDataSetChanged();
                                Log.d("DEBUG", movieJsonResults.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            super.onFailure(statusCode, headers, responseString, throwable);
                        }
                    });
                }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    }

    public void detailsOfMovie(Movie movie){

        Intent i;
        if(movie.getVoteAverage()>5){
            i = new Intent(MovieActivity.this, MoviePlayActivity.class);
        }
        else {
            i = new Intent(MovieActivity.this, MovieDetailsActivity.class);
            i.putExtra("movie", movie);
        }
        i.putExtra("video_src", video_src);
        startActivity(i);
    }



}
