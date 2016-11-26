package com.example.anase.popularmovies2.Activity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.anase.popularmovies2.MovieDBTable;
import com.example.anase.popularmovies2.Movies;
import com.example.anase.popularmovies2.R;
import com.example.anase.popularmovies2.RecyclerItemClickListener;
import com.example.anase.popularmovies2.Adapter.ReviewAdapter;
import com.example.anase.popularmovies2.Reviews;
import com.example.anase.popularmovies2.Adapter.TralierAdapter;
import com.example.anase.popularmovies2.Traliers;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    private static DetailActivityFragment details_Fragment;
    Movies movie;
    Reviews reviews;

    TextView titleDetail;
    TextView rateDetail;
    TextView dateDetail;
    TextView overviewDetail;
    ImageView imageDetail;
    FloatingActionButton favourite;
    boolean aBoolean;

    private ReviewAdapter reviewAdapter;
    private ArrayList<Reviews>reviewsArrayList;
    private RecyclerView recyclerView_reviews;


    private TralierAdapter tralierAdapter;
    private ArrayList<Traliers> list;

    private RecyclerView recyclerView_tralier;
    private RecyclerView.LayoutManager videosLayoutManager;
    private LinearLayoutManager reviewsLayoutManager;

    public DetailActivityFragment() {
    }

    public static DetailActivityFragment newInstance(Movies movie) {

        details_Fragment = new DetailActivityFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("movie", movie);
        details_Fragment.setArguments(bundle);
        return details_Fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_detail, container, false);


        titleDetail= (TextView) view.findViewById(R.id.title_id);
        rateDetail= (TextView) view.findViewById(R.id.rate_id);
        dateDetail= (TextView) view.findViewById(R.id.date_id);
        overviewDetail= (TextView) view.findViewById(R.id.overview_id);
        imageDetail= (ImageView) view.findViewById(R.id.image_detail_id);
        favourite= (FloatingActionButton) view.findViewById(R.id.favourite);

        movie = details_Fragment.getArguments().getParcelable("movie");

        String date = movie.getDateMovie();
        String rate = movie.getRateMovie();
        String overview =movie.getOverviewMovie();
        String title   =movie.getTiteMovie();
        String image = movie.getImage()+"";

        titleDetail.setText(title);
        rateDetail.setText(rate);
        dateDetail.setText(date);
        overviewDetail.setText(overview);

        recyclerView_reviews = (RecyclerView) view.findViewById(R.id.reviews_RecyclerView);
        recyclerView_reviews.setHasFixedSize(true);
        reviewsLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView_reviews.setLayoutManager(reviewsLayoutManager);
        recyclerView_reviews.setNestedScrollingEnabled(false);


        recyclerView_tralier = (RecyclerView) view.findViewById(R.id.trailerRecycler_view);
        recyclerView_tralier.setHasFixedSize(true);
        videosLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView_tralier.setLayoutManager(videosLayoutManager);

        recyclerView_tralier.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.SimpleOnItemClickListener() {
                    @Override
                    public void onItemClick(View childView, int position) {
                        Traliers item = TralierAdapter.getItem(position);
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + item.getKey())));


                    }


                    @Override
                    public void onItemLongPress(View childView, int position) {
                        super.onItemLongPress(childView, position);

                    }
                }));



        Cursor cursor = getActivity().getContentResolver().query(MovieDBTable.CONTENT_URI, null, MovieDBTable.FIELD_ID + "=" + movie.getId(), null, null);
        ArrayList<Movies> movie_list = (ArrayList<Movies>) MovieDBTable.getRows(cursor, false);
        if (movie_list.size() == 0) {
            aBoolean = false;
            favourite.setImageDrawable(ContextCompat.getDrawable(getContext(), android.R.drawable.star_big_off));
        } else {
            aBoolean = true;
             favourite.setImageDrawable(ContextCompat.getDrawable(getContext(), android.R.drawable.star_big_on));
        }

        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!aBoolean) {
                    favourite.setImageDrawable(ContextCompat.getDrawable(getContext(), android.R.drawable.star_big_on));
                    Bitmap bitmap = ((BitmapDrawable) imageDetail.getDrawable()).getBitmap();

                    movie.setPosterPath(saveImage(bitmap, movie.getImage()));
                    getActivity().getContentResolver().insert(MovieDBTable.CONTENT_URI, MovieDBTable.getContentValues(movie, false));
                    Toast.makeText(getActivity(), "Added to favorite", Toast.LENGTH_SHORT).show();
                    aBoolean = true;
                } else {
                    favourite.setImageDrawable(ContextCompat.getDrawable(getContext(), android.R.drawable.star_big_off));
                    getActivity().getContentResolver().delete(MovieDBTable.CONTENT_URI, MovieDBTable.FIELD_ID + "=" + movie.getId(), null);
                    deleteImage(movie.getImage());
                    movie.setPosterPath("");
                    Toast.makeText(getActivity(), "Removed from favorite", Toast.LENGTH_SHORT).show();
                    aBoolean = false;
                }

            }
        });

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();


        if (!movie.getPosterPath().isEmpty()) {
            Bitmap b = loadImage(movie.getPosterPath(), movie.getImage());
            imageDetail.setImageBitmap(b);
        } else {

            Picasso.with(getActivity()).load(movie.getImage()).into(imageDetail);

        }

        getTraliers();
        getReviews();
    }


    private String saveImage(Bitmap bitmapImage, String poster) {
        ContextWrapper contextWrapper = new ContextWrapper(getActivity().getApplicationContext());
        File directory = contextWrapper.getDir("image", Context.MODE_PRIVATE);
        File mypath = new File(directory, poster);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos!=null){
                    fos.close();}
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }
    private Bitmap loadImage(String path, String poster) {

        Bitmap b = null;
        try {
            File f = new File(path, poster);
            b = BitmapFactory.decodeStream(new FileInputStream(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return b;
    }

    private void deleteImage(String poster) {
        ContextWrapper contextWrapper = new ContextWrapper(getActivity().getApplicationContext());
        File directory = contextWrapper.getDir("image", Context.MODE_PRIVATE);
        File mypath = new File(directory, poster);
        try {
            mypath.delete();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }


    public void getReviews(){

        reviewsArrayList=new ArrayList<>() ;

        final String api_key = "b574dc4e5adb940782081702ef40436b";
        String url = "http://api.themoviedb.org/3/movie/" + movie.getId() + "/reviews?api_key=" + api_key;


        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response != null) {
                            try {


                                JSONObject object = new JSONObject(response);
                                JSONArray results = object.optJSONArray("results");


                                for (int i = 0; i < results.length(); i++) {
                                    JSONObject ob = results.optJSONObject(i);
                                    String author = ob.getString("author");
                                    String content =ob.getString("content");

                                    reviews =new Reviews(author,content);

                                    reviewsArrayList.add(reviews);

                                }

                                reviewAdapter=new ReviewAdapter(reviewsArrayList,getActivity());
                                recyclerView_reviews.setAdapter(reviewAdapter);


                            } catch (final JSONException e) {

                                e.printStackTrace();
                            }


                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("error == " + error);
                    }
                }
        ) {

        };

        queue.add(request);

    }


    public void getTraliers( ){

         list=new ArrayList<>() ;

        final String api_key = "b574dc4e5adb940782081702ef40436b";
        String url = "http://api.themoviedb.org/3/movie/" + movie.getId() + "/videos?api_key=" + api_key;

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("output == " + response);


                        if (response != null) {
                            try {


                                JSONObject object = new JSONObject(response);
                                JSONArray results = object.optJSONArray("results");

                                Traliers tralier;

                                for (int i = 0; i < results.length(); i++) {
                                    JSONObject ob = results.optJSONObject(i);
                                    String key = ob.getString("key");
                                    String name =ob.getString("name");

                                     tralier =new Traliers(name,key);

                                    list.add(tralier);

                                }

                                tralierAdapter=new TralierAdapter(getActivity(),list);
                                recyclerView_tralier.setAdapter(tralierAdapter);


                            } catch (final JSONException e) {

                                e.printStackTrace();
                            }


                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("error == " + error);
                    }
                }
        ) {

        };

        queue.add(request);

    }


}
