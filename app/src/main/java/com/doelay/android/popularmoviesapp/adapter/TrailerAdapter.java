package com.doelay.android.popularmoviesapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.doelay.android.popularmoviesapp.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by doelay on 6/19/2017.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerAdapterViewHolder> {

    private OnTrailerSelectedListener callback;
    private Context mContext;
    private String[] trailerLinks;

    public interface OnTrailerSelectedListener {
        void onTrailerSelected(int position);
    }

    public TrailerAdapter (Context context) {
        mContext = context;
        callback = (OnTrailerSelectedListener) context;
    }

    @Override
    public TrailerAdapter.TrailerAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_view, parent, false);
        return new TrailerAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerAdapter.TrailerAdapterViewHolder holder, int position) {

        if(trailerLinks != null && trailerLinks.length > 0) {
            Picasso.with(mContext)
                    .load(R.drawable.youtube)
                    .into(holder.trailerView);
        }
    }

    @Override
    public int getItemCount() {
        if(trailerLinks == null) {
            return 0;
        }
        return trailerLinks.length;
    }
    public void setTrailerData(String[] trailerLinks) {
        this.trailerLinks = trailerLinks;
        notifyDataSetChanged();
    }

    public class TrailerAdapterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        @BindView(R.id.iv_trailer_view) ImageView trailerView;

        public TrailerAdapterViewHolder (View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            callback.onTrailerSelected(adapterPosition);
        }
    }
}
