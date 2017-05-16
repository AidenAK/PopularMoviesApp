package com.doelay.android.popularmoviesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    TextView movieTitle;
    TextView voteAverage;
    TextView releaseDate;
    TextView plot;
    ImageView moviePoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        movieTitle = (TextView) findViewById(R.id.tv_movie_title);
        voteAverage = (TextView) findViewById(R.id.tv_vote_average);
        releaseDate = (TextView) findViewById(R.id.tv_release_date);
        plot = (TextView) findViewById(R.id.tv_overview);
        moviePoster = (ImageView) findViewById(R.id.iv_movie_poster);

        Intent intent = getIntent();
        if (intent != null) {
            Movies movieSelected = intent.getParcelableExtra("MovieDetail");

            String originalTitle = movieSelected.getOriginalTitle();
            movieTitle.setText(originalTitle);

            String overview = movieSelected.getOverview();
            plot.setText(overview);

            String Date = movieSelected.getReleaseDate();
            releaseDate.setText("Release date " + Date);

            String rating = Double.toString(movieSelected.getRating());
            voteAverage.setText(rating);

            String posterPath = movieSelected.getPosterPath();
            Picasso.with(this)
                    .load(posterPath)
                    .into(moviePoster);
        }

    }
}
