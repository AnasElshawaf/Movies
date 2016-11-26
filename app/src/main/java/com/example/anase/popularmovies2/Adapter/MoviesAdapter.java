package com.example.anase.popularmovies2.Adapter;

/**
 * Created by anase on 23/10/2016.
 */

 import android.app.Activity;
        import android.content.Context;
        import android.content.Intent;
        import android.support.v7.widget.RecyclerView;
 import android.util.Log;
 import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
 import android.widget.LinearLayout;

 import com.example.anase.popularmovies2.Activity.DetailActivity;
 import com.example.anase.popularmovies2.Activity.MainActivity;
 import com.example.anase.popularmovies2.Movies;
 import com.example.anase.popularmovies2.R;
 import com.squareup.picasso.Picasso;

        import java.util.List;


public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {


    private List<Movies> moviesList;
    private  Context mcontext;
    private Movies movie;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public LinearLayout linearLayout;

        public MyViewHolder(View view) {
            super(view);

            imageView= (ImageView) view.findViewById(R.id.imageview_id);
            linearLayout= (LinearLayout) view.findViewById(R.id.parent_item);

        }
    }


    public MoviesAdapter( List<Movies> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

       mcontext=parent.getContext();
        View itemView = LayoutInflater.from(mcontext)
                .inflate(R.layout.movie_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    public List<Movies> getMoviesList() {
        return moviesList;
    }

    public void setMoviesList(List<Movies> moviesList) {
        this.moviesList = moviesList;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
          movie = moviesList.get(position);
        Log.e("aaaaa",movie.getImage());
        System.out.println("++>>"+movie.getImage());
        Picasso.with(mcontext).load(movie.getImage()+"").into(holder.imageView);
//        holder.imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                movie = moviesList.get(position);
//
//                //Pass the image title and url to DetailsActivity
//                Intent intent = new Intent(activity, DetailActivity.class);
//                intent.putExtra("movie", movie);
//
//                activity.displayMovies(moviesList.get(position));
//
//
//
//
//            }
//        });
//
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}