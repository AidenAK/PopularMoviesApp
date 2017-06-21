package com.doelay.android.popularmoviesapp.adapter;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doelay.android.popularmoviesapp.R;
import com.doelay.android.popularmoviesapp.db.MoivesContract;

/**
 * Created by doelay on 6/20/2017.
 */

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.FavoriteMovieViewHolder> {

    private Cursor mCursor;

    @Override
    public FavoriteMovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_movie_view, parent, false);
        return new FavoriteMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavoriteMovieViewHolder holder, int position) {
        int titleIndex = mCursor.getColumnIndex(MoivesContract.MoviesEntry.MOVIE_TITLE);

        mCursor.moveToPosition(position);
        String movieTitle = mCursor.getString(titleIndex);
        holder.favoriteMovieTitle.setText(movieTitle);
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }

    public Cursor swapCursor(Cursor newCursor) {
        if (mCursor == newCursor) {
            return null;
        }
        Cursor temp = mCursor;
        mCursor = newCursor;
        if ( mCursor != null) {
            notifyDataSetChanged();
        }
        return temp;
    }

    class FavoriteMovieViewHolder extends RecyclerView.ViewHolder {

        private TextView favoriteMovieTitle;

        public FavoriteMovieViewHolder(View view) {
            super(view);
            favoriteMovieTitle = (TextView) view.findViewById(R.id.tv_favorite_movie_title);
        }
    }
}
