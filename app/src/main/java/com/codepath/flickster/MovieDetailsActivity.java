package com.codepath.flickster;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.codepath.flickster.models.Movie;
import com.squareup.picasso.Picasso;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class MovieDetailsActivity extends AppCompatActivity {

    private TextView tvTitle;
    private TextView tvReleseDate;
    private TextView tvOverview;
    private RatingBar rbRatings;
    private ImageView ivMovieImage,ivPlayIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        ivMovieImage = (ImageView) findViewById(R.id.ivMovieImage);
        ivPlayIcon = (ImageView) findViewById(R.id.ivPlayIcon);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvReleseDate = (TextView) findViewById(R.id.tvReleaseDate);
        rbRatings = (RatingBar) findViewById(R.id.rbRatings);
        tvOverview = (TextView) findViewById(R.id.tvOverview);

        final Movie movie = (Movie) getIntent().getSerializableExtra("movie");
        tvTitle.setText(movie.getOriginalTitle());
        tvReleseDate.setText(movie.getReleaseDate());
        tvOverview.setText(movie.getOverview());
        rbRatings.setRating(movie.getVoteAverage());

        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            Picasso.with(this).load(movie.getBackdropPath()).transform(new RoundedCornersTransformation(8, 8)).placeholder(R.drawable.placehoder_backdrop).noFade().into(ivMovieImage);
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Picasso.with(this).load(movie.getBackdropPath()).transform(new RoundedCornersTransformation(8, 8)).into(ivMovieImage);
        }

        ivPlayIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MovieDetailsActivity.this, MoviePlayActivity.class);
                final String video_src = getIntent().getStringExtra("video_src");
                i.putExtra("video_src", video_src);
                // brings up the second activity
                startActivity(i);
            }
        });
    }

}
