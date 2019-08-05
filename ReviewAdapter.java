package com.example.fortouristsbytourists;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * managing the reviews ArrayList in the RecyclerView widget.
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private static final String TAG = "ReviewAdapter";  //for testing only

    private ArrayList<Review> reviewList = new ArrayList<>();  //arrayList with selected reviews

    public ReviewAdapter(Context context, ArrayList<Review> reviewList) {
        this.reviewList = reviewList;
    }

    @NonNull
    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.review_item, viewGroup, false);
        ReviewAdapter.ViewHolder holder = new ReviewAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ViewHolder holder, final int position) {
        // describing the connection between ArrayList element and a line in the RecyclerView/holder

        Log.d(TAG, "onBindViewHolder: called.");  //for testing only

        holder.tvRating.setText(reviewList.get(position).getRating()+"");
        holder.tvUserName.setText(reviewList.get(position).getUserName()+":");
        holder.tvUserReview.setText(reviewList.get(position).getReview());

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // activity with that link, if necessary
            }
        });
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {  //describe one element from the RcyclerReview(review_item.xml)

        TextView tvUserName,tvRating,tvUserReview;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            tvUserName = itemView.findViewById(R.id.user_name);
            tvRating = itemView.findViewById(R.id.user_rating);
            tvUserReview = itemView.findViewById(R.id.user_review);
        }
    }


}
