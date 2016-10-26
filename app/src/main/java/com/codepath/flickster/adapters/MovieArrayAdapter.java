package com.codepath.flickster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.flickster.R;
import com.codepath.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by Sonam on 10/11/2016.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    public static class ViewHolderForLessPopularMovie {
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivImage;
    }

    public static class ViewHolderForPopularMovie {
        ImageView ivImage;
    }

    public MovieArrayAdapter(Context context, List<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);

    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).movieType.ordinal();
    }

    // Total number of types is the number of enum values
    @Override
    public int getViewTypeCount() {
        return Movie.MovieType.values().length;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int movieType = this.getItemViewType(position);
        //get data item for position
        Movie movie = getItem(position);
        int orientation;
        switch (movieType) {
            case 0:
                ViewHolderForLessPopularMovie lessPopularMovieHolder;

                if (convertView == null) {
                    lessPopularMovieHolder = new ViewHolderForLessPopularMovie();
                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    convertView = inflater.inflate(R.layout.item_movie, parent, false);
                    //find image view
                    lessPopularMovieHolder.ivImage = (ImageView) convertView.findViewById(R.id.ivMovieImage);
                    //clear our image from convertView
                    lessPopularMovieHolder.ivImage.setImageResource(0);

                    lessPopularMovieHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
                    lessPopularMovieHolder.tvOverview = (TextView) convertView.findViewById(R.id.tvOverview);
                    // Cache the viewHolder object inside the fresh view
                    convertView.setTag(lessPopularMovieHolder);
                } else {
                    // View is being recycled, retrieve the viewHolder object from tag
                    lessPopularMovieHolder = (ViewHolderForLessPopularMovie) convertView.getTag();
                }

                //populate data
                lessPopularMovieHolder.tvTitle.setText(movie.getOriginalTitle());
                lessPopularMovieHolder.tvOverview.setText(movie.getOverview());
                orientation = getContext().getResources().getConfiguration().orientation;
                if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                    Picasso.with(getContext()).load(movie.getPosterPath()).transform(new RoundedCornersTransformation(8, 8)).placeholder(R.drawable.placehoder_poster).into(lessPopularMovieHolder.ivImage);
                } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    Picasso.with(getContext()).load(movie.getPosterPath()).transform(new RoundedCornersTransformation(8, 8)).placeholder(R.drawable.placehoder_poster).into(lessPopularMovieHolder.ivImage);
                }
                //return the view
                return convertView;

            case 1:
                ViewHolderForLessPopularMovie popularMovieHolder;

                if (convertView == null) {
                    popularMovieHolder = new ViewHolderForLessPopularMovie();
                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    convertView = inflater.inflate(R.layout.item_popular_movie, parent, false);
                    //find image view
                    popularMovieHolder.ivImage = (ImageView) convertView.findViewById(R.id.ivMovieFullImage);
                    //clear our image from convertView
                    popularMovieHolder.ivImage.setImageResource(0);

                    // Cache the viewHolder object inside the fresh view
                    convertView.setTag(popularMovieHolder);
                } else {
                    // View is being recycled, retrieve the viewHolder object from tag
                    popularMovieHolder = (ViewHolderForLessPopularMovie) convertView.getTag();
                }

                //populate data

                orientation = getContext().getResources().getConfiguration().orientation;
                if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                    Picasso.with(getContext()).load(movie.getBackdropPath()).transform(new RoundedCornersTransformation(8, 8)).placeholder(R.drawable.placehoder_backdrop).into(popularMovieHolder.ivImage);
                } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    Picasso.with(getContext()).load(movie.getBackdropPath()).transform(new RoundedCornersTransformation(8, 8)).placeholder(R.drawable.placehoder_backdrop).into(popularMovieHolder.ivImage);
                }
                //return the view
                return convertView;
            default:
                //Throw exception, unknown data type

        }
        return convertView;
    }
}
