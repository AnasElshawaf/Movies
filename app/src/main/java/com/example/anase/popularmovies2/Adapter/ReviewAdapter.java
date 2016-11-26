package com.example.anase.popularmovies2.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.anase.popularmovies2.R;
import com.example.anase.popularmovies2.Reviews;

import java.util.ArrayList;

/**
 * Created by anase on 20/11/2016.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolderReview>{

      ArrayList<Reviews>reviewsArrayList;
     Activity activity;
     Context context;



    public ReviewAdapter(ArrayList<Reviews> reviewsArrayList, Activity activity) {
        this.reviewsArrayList = reviewsArrayList;
        this.activity = activity;
    }

    public static class ViewHolderReview extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView author_txt;
        TextView content_txt;

        public ViewHolderReview(View itemView) {
            super(itemView);

            cardView= (CardView) itemView.findViewById(R.id.review_card_id);
            author_txt= (TextView) itemView.findViewById(R.id.reviews_auther);
            content_txt= (TextView) itemView.findViewById(R.id.reviews_content);

        }
    }
    @Override
    public ViewHolderReview onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.review_item, parent, false);
        ViewHolderReview tr = new ViewHolderReview(v);
        return tr;
    }

    @Override
    public void onBindViewHolder(ViewHolderReview holder, int position) {

        holder.author_txt.setText(reviewsArrayList.get(position).getAuther());
        holder.content_txt.setText(reviewsArrayList.get(position).getContent());

    }

    @Override
    public int getItemCount() {
        return reviewsArrayList.size();
    }

//   public static Reviews getitem(int position) {
//
//   return reviewsArrayList.get(position);
//}


}
