package com.example.anase.popularmovies2.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anase.popularmovies2.R;
import com.example.anase.popularmovies2.Traliers;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by anase on 02/11/2016.
 */

public class TralierAdapter extends RecyclerView.Adapter<TralierAdapter.tralierViewHolder> {

    public static ArrayList<Traliers> items;
    public Activity activity;
    private Context context;

    public TralierAdapter() {
        items = new ArrayList<>();
    }

    public static class tralierViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView image_film;
        TextView txt_film;


        tralierViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.videos_card_view);
            image_film = (ImageView) itemView.findViewById(R.id.image_tralier);
            txt_film = (TextView) itemView.findViewById(R.id.text_tralier);
        }


    }


    public TralierAdapter(Activity activity, ArrayList<Traliers> items) {
        this.activity=activity;
        this.items = items;
    }

    public ArrayList<Traliers> getItems() {
        return items;
    }

    public void setItems(ArrayList<Traliers> items) {
        this.items = items;
        this.notifyDataSetChanged();
    }
    public static Traliers  getItem(int position) {
        return items.get(position);
    }


    @Override
    public tralierViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.tralier_item, parent, false);
        tralierViewHolder tr = new tralierViewHolder(v);
        return tr;
    }


    @Override
    public void onBindViewHolder(tralierViewHolder holder, int position) {
        holder.txt_film.setText(items.get(position).getName());
        Picasso.with(context).load("http://img.youtube.com/vi/" + items.get(position).getKey() + "/0.jpg").into(holder.image_film);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}
