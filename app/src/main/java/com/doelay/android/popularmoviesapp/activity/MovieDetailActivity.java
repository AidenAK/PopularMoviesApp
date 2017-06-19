package com.doelay.android.popularmoviesapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.doelay.android.popularmoviesapp.R;
import com.doelay.android.popularmoviesapp.adapter.TrailerAdapter;
import com.doelay.android.popularmoviesapp.model.Movies;
import com.doelay.android.popularmoviesapp.model.Trailer;
import com.doelay.android.popularmoviesapp.task.GetTrailerLinkTask;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity
        implements GetTrailerLinkTask.OnDataAvailable, TrailerAdapter.OnTrailerSelectedListener {
    private static final String TAG = MovieDetailActivity.class.getSimpleName();

    private TextView movieTitle;
    private TextView voteAverage;
    private TextView releaseDate;
    private TextView plot;
    private ImageView moviePoster;
    private ImageView backdrop;
    private Movies movieSelected;
    private Trailer mTrailer;
    private RecyclerView trailerRecyclerView;
    private TrailerAdapter trailerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        movieTitle = (TextView) findViewById(R.id.tv_movie_title);
        voteAverage = (TextView) findViewById(R.id.tv_vote_average);
        releaseDate = (TextView) findViewById(R.id.tv_release_date);
        plot = (TextView) findViewById(R.id.tv_overview);
        moviePoster = (ImageView) findViewById(R.id.iv_movie_poster);
        backdrop = (ImageView) findViewById(R.id.iv_backdrop);
        trailerRecyclerView = (RecyclerView) findViewById(R.id.rv_trailer_view);

        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        //fetch video links
        movieSelected = intent.getParcelableExtra("MovieDetail");
        String movieIdString = String.valueOf(movieSelected.getId());
        new GetTrailerLinkTask(this).execute(movieIdString);

        //set up recycler view for trailer
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        trailerRecyclerView.setLayoutManager(layoutManager);
        trailerRecyclerView.setHasFixedSize(true);
        trailerAdapter = new TrailerAdapter(this);
        trailerRecyclerView.setAdapter(trailerAdapter);

        // TODO: 6/14/2017 fetch reviews

        String originalTitle = movieSelected.getOriginalTitle();
        movieTitle.setText(originalTitle);

        String overview = movieSelected.getOverview();
        plot.setText(overview);

        String Date = movieSelected.getReleaseDate();
        releaseDate.setText(Date);

        String rating = Double.toString(movieSelected.getRating());
        voteAverage.setText(rating);

        String posterPath = movieSelected.getPosterPath();
        Picasso.with(this)
                .load(posterPath)
                .into(moviePoster);

        String backdropLink = movieSelected.getPosterPath();
        Picasso.with(this)
                .load(backdropLink)
                .into(backdrop);

    }

    /**
     * A callback after the trailer links are downloaded.
     * Trailer links are added to movie object.
     * @param trailerLinks  videos urls
     */
    @Override
    public void onTrailerLinkAvailable(String[] trailerLinks) {
        //save the trailer paths
        mTrailer = new Trailer(null, null, trailerLinks);
        movieSelected.setTrailerLinks(mTrailer);

        trailerAdapter.setTrailerData(trailerLinks);

    }

    @Override
    public void onTrailerSelected() {

    }
}
