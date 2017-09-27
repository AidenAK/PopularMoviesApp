package com.doelay.android.popularmoviesapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doelay.android.popularmoviesapp.R;
import com.doelay.android.popularmoviesapp.model.Review;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by doelay on 6/19/2017.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder> {

    private List<Review> reviewList;



    @Override
    public ReviewAdapter.ReviewAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_view, parent, false);

        return new ReviewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewAdapter.ReviewAdapterViewHolder holder, int position) {
        Review review = reviewList.get(position);
        holder.author.setText(review.getAuthorName());
        holder.review.setText(review.getReview());
    }

    @Override
    public int getItemCount() {
        if (reviewList == null){
            return 0;
        }
        return reviewList.size();
    }

    public void setReviewData(List<Review> reviewList) {
        this.reviewList = reviewList;
        notifyDataSetChanged();
    }

    public class ReviewAdapterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_author) TextView author;
        @BindView(R.id.tv_review) TextView review;

        public ReviewAdapterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
