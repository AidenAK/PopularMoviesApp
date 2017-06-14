package com.doelay.android.popularmoviesapp;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.doelay.android.popularmoviesapp.db.MoivesContract;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by doelay on 5/11/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {
    private static final String TAG = MovieAdapter.class.getSimpleName();

    private List<Movies> mMoviesList;
    private OnMovieSelectedListener callback;
    private Context mContext;

    public interface OnMovieSelectedListener {
        void onMovieSelectedListener (Movies movie);
    }

    public MovieAdapter (Context context) {
        callback = (OnMovieSelectedListener) context;
        mContext = context;
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movies_views, parent, false);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {
        // TODO: 6/14/2017 add placeholder
        if (mMoviesList != null && mMoviesList.size() != 0) {
            Movies movies = mMoviesList.get(position);
            Picasso.with(mContext)
                    .load(movies.getPosterPath())
                    .into(holder.posterView);
            Log.d(TAG, "onBindViewHolder: poster path is "+ movies.getPosterPath());
        }
    }

    @Override
    public int getItemCount() {
       if (mMoviesList == null){
           return 0;
       }
        return mMoviesList.size();
    }
    public void setMovieData(List<Movies> movieData) {
        mMoviesList = movieData;
        notifyDataSetChanged();
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        private ImageView posterView;
        private ImageView favoriteStar;

        public MovieAdapterViewHolder (View view) {
            super(view);
            posterView = (ImageView) view.findViewById(R.id.iv_movie_poster);
            posterView.setOnClickListener(this);
//            favoriteStar = (ImageView) view.findViewById(R.id.iv_favorite_star_off);
//            favoriteStar.setOnClickListener(this);
        }

        // TODO: 6/14/2017 need to fix favorite on/off
        @Override
        public void onClick(View view) {
            Movies moviesSelected;
            int adapterPosition = getAdapterPosition();

            switch(view.getId()) {
                case R.id.iv_favorite_star_off:
                    //set favorite star on
                    ImageView getViewOff = (ImageView) view;
                    getViewOff.setVisibility(View.INVISIBLE);
                    getViewOff.setImageResource(android.R.drawable.btn_star_big_on);
                    getViewOff.setVisibility(View.VISIBLE);

                    //extract movie data and store in content provider
                    moviesSelected = mMoviesList.get(adapterPosition);

                    ContentValues values = new ContentValues();
                    values.put(MoivesContract.MoviesEntry.MOVIE_ID, moviesSelected.getId());
                    values.put(MoivesContract.MoviesEntry.MOVIE_TITLE, moviesSelected.getOriginalTitle());
                    values.put(MoivesContract.MoviesEntry.MOVIE_OVERVIEW, moviesSelected.getOverview());
                    values.put(MoivesContract.MoviesEntry.MOVIE_RELEASE_DATE, moviesSelected.getReleaseDate());
                    values.put(MoivesContract.MoviesEntry.MOVIE_POSTER_PATH, moviesSelected.getPosterPath());

                    Uri uri = mContext.getContentResolver()
                            .insert(MoivesContract.MoviesEntry.CONTENT_URI, values);
                    if(uri != null) {
                        Toast.makeText(mContext,"Added to Favorite Movies!", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.iv_favorite_star_on:
                    ImageView getViewOn = (ImageView) view;
                    getViewOn.setVisibility(View.INVISIBLE);
                    getViewOn.setImageResource(android.R.drawable.btn_star_big_off);
                    getViewOn.setVisibility(View.VISIBLE);
                    Toast.makeText(mContext,"Favorite icon off!", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.iv_movie_poster:
                    callback.onMovieSelectedListener(mMoviesList.get(adapterPosition));
            }


        }
    }
}
