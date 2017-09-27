package com.doelay.android.popularmoviesapp.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.doelay.android.popularmoviesapp.R;
import com.doelay.android.popularmoviesapp.adapter.ReviewAdapter;
import com.doelay.android.popularmoviesapp.adapter.TrailerAdapter;
import com.doelay.android.popularmoviesapp.db.MoivesContract;
import com.doelay.android.popularmoviesapp.model.Movies;
import com.doelay.android.popularmoviesapp.model.Review;
import com.doelay.android.popularmoviesapp.model.Trailer;
import com.doelay.android.popularmoviesapp.task.GetReviewTask;
import com.doelay.android.popularmoviesapp.task.GetTrailerLinkTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity
        implements GetTrailerLinkTask.OnTrailerDataAvailable, TrailerAdapter.OnTrailerSelectedListener, GetReviewTask.OnReviewAvailable {

    @BindView(R.id.tv_movie_title) TextView movieTitle;
    @BindView(R.id.tv_vote_average) TextView voteAverage;
    @BindView(R.id.tv_release_date) TextView releaseDate;
    @BindView(R.id.tv_overview) TextView plot;
    @BindView(R.id.iv_movie_poster) ImageView moviePoster;
    @BindView(R.id.iv_backdrop) ImageView backdrop;
    @BindView(R.id.rv_trailer_view) RecyclerView trailerRecyclerView;
    @BindView(R.id.rv_review) RecyclerView reviewRecyclerView;
    @BindView(R.id.tb_favorite_star) ToggleButton favoriteButton;

    private static final String TAG = MovieDetailActivity.class.getSimpleName();
    private static final String MOVIE_SELECTED = "movie_selected";

    private Movies movieSelected;
    private Trailer trailer;
    private TrailerAdapter trailerAdapter;
    private ReviewAdapter reviewAdapter;
    private String[] trailerLinks;
    private List<Review> reviewList;
    private String movieIdString;
    LinearLayoutManager trailerLayoutManager;
    LinearLayoutManager reviewLayoutManager;

    // TODO: 6/29/2017 implement a collaspsing toolbar layout
    // TODO: 6/29/2017 edit scroll view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //find UI elements
        ButterKnife.bind(this);

        //set up trailer recycler view
        initializeTrailerRecyclerView();

        //set up movie review recycler view
        initializeReviewRecyclerView();

        //check for saved instance
        if(savedInstanceState == null) {
            Intent intent = getIntent();
            Bundle extras = intent.getExtras();
            if (extras == null) {
                return;
            } else {
                if (extras.containsKey("MovieDetail")) {
                    movieSelected = intent.getParcelableExtra("MovieDetail");
                    movieIdString = String.valueOf(movieSelected.getId());
                    //fetch trailer links
                    new GetTrailerLinkTask(this).execute(movieIdString);
                    //fetch movie review
                    new GetReviewTask(this).execute(movieIdString);
                }
            }
        } else {

            movieSelected = savedInstanceState.getParcelable(MOVIE_SELECTED);//retrieve the object
            movieIdString = String.valueOf(movieSelected.getId());
            //get the trailer paths
            Trailer trailer = movieSelected.getTrailer();
            trailerAdapter.setTrailerData(trailer.getTrailerPath());
            //get the review
            reviewAdapter.setReviewData(movieSelected.getReview());
        }

        //check whether it is favorite
        favoriteButton.setChecked(isFavorite(movieIdString));
        setFavoriteButtonListener();

        // TODO: 6/19/2017 need to get trailer thumbnail for trailer recycler view
        //display UI elements
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

        String backdropLink = movieSelected.getBackdropPath();
        Picasso.with(this)
                .load(backdropLink)
                .into(backdrop);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(MOVIE_SELECTED, movieSelected);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_movie_detail, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_favorite) {
            Intent intent = new Intent(this, FavoriteMovieActivity.class);
            startActivity(intent);
            return true;
        }
        if(id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initializeTrailerRecyclerView() {
        trailerLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        trailerRecyclerView.setLayoutManager(trailerLayoutManager);
        trailerRecyclerView.setHasFixedSize(true);
        trailerAdapter = new TrailerAdapter(MovieDetailActivity.this);
        trailerRecyclerView.setAdapter(trailerAdapter);
    }

    private void initializeReviewRecyclerView() {
        reviewLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        reviewRecyclerView.setLayoutManager(reviewLayoutManager);
        reviewRecyclerView.setNestedScrollingEnabled(false);
        reviewAdapter = new ReviewAdapter();
        reviewRecyclerView.setAdapter(reviewAdapter);
    }

    private boolean isFavorite(String movieId) {
        Cursor cursor = getContentResolver().query(
                buildUriWithMovieId(),
                null,
                null,
                null,
                null,
                null);
        Log.d(TAG, "isFavorite: "+ cursor.getCount());
        if(cursor.getCount() == 0) {
            return false;
        }
        return true;
    }

    private void setFavoriteButtonListener() {
        favoriteButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {

                    ContentValues values = new ContentValues();
                    values.put(MoivesContract.MoviesEntry.MOVIE_ID, movieSelected.getId());
                    values.put(MoivesContract.MoviesEntry.MOVIE_TITLE, movieSelected.getOriginalTitle());
                    values.put(MoivesContract.MoviesEntry.MOVIE_OVERVIEW, movieSelected.getOverview());
                    values.put(MoivesContract.MoviesEntry.MOVIE_RELEASE_DATE, movieSelected.getReleaseDate());
                    values.put(MoivesContract.MoviesEntry.MOVIE_POSTER_PATH, movieSelected.getPosterPath());


                    Uri uri = getContentResolver().insert(MoivesContract.MoviesEntry.CONTENT_URI, values);

                    if( uri != null) {
                        Toast.makeText(MovieDetailActivity.this,
                                "Added to favorites", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    int rowDeleted = getContentResolver().delete(buildUriWithMovieId(), null, null);
                    Log.d(TAG, "onCheckedChanged: "+ buildUriWithMovieId());
                    if(rowDeleted != 0) {
                        Toast.makeText(MovieDetailActivity.this, "Removed from favorites",Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
    }

    private Uri buildUriWithMovieId() {

        Uri uri = MoivesContract.MoviesEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(movieIdString).build();

        return uri;
    }

    /**
     * A callback after the trailer links are downloaded.
     * Trailer links are added to movie object.
     * @param trailerLinks  videos urls
     */
    @Override
    public void onTrailerDataAvailable(String[] trailerLinks) {
        Log.d(TAG, "onTrailerDataAvailable: called");
        //save the trailer paths
        trailer = new Trailer(null, null, trailerLinks);
        movieSelected.setTrailer(trailer);
        //pass the trailer to the adapter
        this.trailerLinks = trailerLinks;
        trailerAdapter.setTrailerData(trailerLinks);

    }

    @Override
    public void onTrailerSelected(int position) {
        Uri trailerUri = Uri.parse(trailerLinks[position]);
        Intent intent = new Intent(Intent.ACTION_VIEW, trailerUri);

        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public void onReviewAvailable(List<Review> review) {
        Log.d(TAG, "onReviewAvailable: called");

        movieSelected.setReview(review);
        reviewAdapter.setReviewData(review);
    }
}

