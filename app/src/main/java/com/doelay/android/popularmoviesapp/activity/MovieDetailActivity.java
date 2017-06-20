package com.doelay.android.popularmoviesapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import com.doelay.android.popularmoviesapp.R;
import com.doelay.android.popularmoviesapp.adapter.ReviewAdapter;
import com.doelay.android.popularmoviesapp.adapter.TrailerAdapter;
import com.doelay.android.popularmoviesapp.model.Movies;
import com.doelay.android.popularmoviesapp.model.Review;
import com.doelay.android.popularmoviesapp.model.Trailer;
import com.doelay.android.popularmoviesapp.task.GetReviewTask;
import com.doelay.android.popularmoviesapp.task.GetTrailerLinkTask;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieDetailActivity extends AppCompatActivity
        implements GetTrailerLinkTask.OnTrailerDataAvailable, TrailerAdapter.OnTrailerSelectedListener, GetReviewTask.OnReviewAvailable {

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
    private RecyclerView reviewRecyclerView;
    private ReviewAdapter reviewAdapter;

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
        reviewRecyclerView = (RecyclerView) findViewById(R.id.rv_review);

        Intent intent = getIntent();
        if (intent == null) {
            return;
        }

        movieSelected = intent.getParcelableExtra("MovieDetail");
        String movieIdString = String.valueOf(movieSelected.getId());

        //fetch trailer links
        new GetTrailerLinkTask(this).execute(movieIdString);

        //fetch movie review
        new GetReviewTask(this).execute(movieIdString);

        //set up trailer recycler view
        LinearLayoutManager trailerLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        trailerRecyclerView.setLayoutManager(trailerLayoutManager);
        trailerRecyclerView.setHasFixedSize(true);

        trailerAdapter = new TrailerAdapter(this);
        trailerRecyclerView.setAdapter(trailerAdapter);

        //set up movie review recycler view
        LinearLayoutManager reviewLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        reviewRecyclerView.setLayoutManager(reviewLayoutManager);
        reviewRecyclerView.setNestedScrollingEnabled(false);
        reviewAdapter = new ReviewAdapter();
        reviewRecyclerView.setAdapter(reviewAdapter);


        // TODO: 6/19/2017 need to get trailer thunbnail for trailer recycler view

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
    public void onTrailerDataAvailable(String[] trailerLinks) {
//        //save the trailer paths
//        mTrailer = new Trailer(null, null, trailerLinks);
//        movieSelected.setTrailerLinks(mTrailer);

        trailerAdapter.setTrailerData(trailerLinks);

    }

    @Override
    public void onReviewAvailable(List<Review> reviewList) {
        reviewAdapter.setReviewData(reviewList);
    }

    @Override
    public void onTrailerSelected() {

    }
}
