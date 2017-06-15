package com.doelay.android.popularmoviesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.doelay.android.popularmoviesapp.task.GetTrailerLinkTask;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity
        implements GetTrailerLinkTask.OnDataAvailable {

    private TextView movieTitle;
    private TextView voteAverage;
    private TextView releaseDate;
    private TextView plot;
    private ImageView moviePoster;
    private Movies movieSelected;

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

            movieSelected = intent.getParcelableExtra("MovieDetail");

            String movieIdString = String.valueOf(movieSelected.getId());
            //fetch video links
            new GetTrailerLinkTask(this).execute(movieIdString);

            // TODO: 6/14/2017 fetch reviews

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

    /**
     * A callback after the trailer links are downloaded.
     * Trailer links are added to movie object.
     * @param trailerLinks  videos urls
     */
    @Override
    public void onTrailerLinkAvailable(String[] trailerLinks) {
        movieSelected.setTrailerLinks(trailerLinks);
    }
}
