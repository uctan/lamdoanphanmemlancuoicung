package com.example.lamdoanphanmemlancuoicung.admin.admindanhgiasanpham;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lamdoanphanmemlancuoicung.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterReviews extends RecyclerView.Adapter<AdapterReviews.ViewHolder> {

    private List<ReviewItem> reviewItems;
    private FirebaseFirestore firestore;


    public AdapterReviews(List<ReviewItem> reviewItems, FirebaseFirestore firestore) {
        this.reviewItems = reviewItems;
        this.firestore = firestore;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.danhgia, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReviewItem item = reviewItems.get(position);

        holder.reviewTextView.setText(item.getReview());
        holder.ratingBar.setRating(item.getRating());
        holder.user.setText(item.getFullName());


    }

    @Override
    public int getItemCount() {
        return reviewItems.size();
    }
    public void addReview(ReviewItem reviewItem) {
        reviewItems.add(reviewItem);
        notifyDataSetChanged();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        RatingBar ratingBar;
        TextView reviewTextView,user;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ratingBar = itemView.findViewById(R.id.ratingBar);
            reviewTextView = itemView.findViewById(R.id.showcmt);
            user =itemView.findViewById(R.id.userdanhgia);
        }
    }


}