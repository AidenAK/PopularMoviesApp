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

        public MovieAdapterViewHolder (View view) {
            super(view);
            posterView = (ImageView) view.findViewById(R.id.iv_movie_poster);
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            callback.onMovieSelectedListener(mMoviesList.get(adapterPosition));
        }
    }
}
