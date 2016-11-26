package com.example.anase.popularmovies2.Activity;

import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.anase.popularmovies2.MovieDBTable;
import com.example.anase.popularmovies2.Movies;
import com.example.anase.popularmovies2.Adapter.MoviesAdapter;
import com.example.anase.popularmovies2.R;
import com.example.anase.popularmovies2.RecyclerItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private ArrayList<Movies> movieList;
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;

    private String url1 = "http://api.themoviedb.org/3/discover/movie?sort_by=";
    private String url2 = ".desc&api_key=b574dc4e5adb940782081702ef40436b";

    private String popular_sort = "popularity";
    private String highest_sort = "revenue";

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);


        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        movieList = new ArrayList<>();
        mAdapter = new MoviesAdapter(movieList);
        recyclerView.setAdapter(mAdapter);
        getMovieData("popularity");

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.SimpleOnItemClickListener() {
                    @Override
                    public void onItemClick(View childView, int position) {

                        ((MainActivity) getActivity()).displayMovies(movieList.get(position));

                    }


                    @Override
                    public void onItemLongPress(View childView, int position) {
                        super.onItemLongPress(childView, position);

                    }
                }));

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
//        getMovieData("popularity");
    }

    public interface Callback {
        public void displayMovies(Movies movie);
    }

    private void showContentBD() {
        Cursor cursor = getActivity().getContentResolver().query(MovieDBTable.CONTENT_URI, null, null, null, null);
        movieList.clear();
        movieList = (ArrayList<Movies>) MovieDBTable.getRows(cursor, false);
        mAdapter.setMoviesList(movieList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.Popularity) {
            System.out.println("=========== > ");
            getMovieData(popular_sort);
            return true;

        } else if (id == R.id.highest) {
            System.out.println("=========== > h ");

            getMovieData(highest_sort);
            return true;


        } else if (id == R.id.favourite) {
            showContentBD();
            return true;


        } else {


            return super.onOptionsItemSelected(item);
        }
    }

    public void getMovieData(String sort) {
        System.out.println("=========== > " + sort);
        String url = url1 + sort + url2;
        movieList.clear();
        mAdapter.notifyDataSetChanged();

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

                                Movies movie;

                                for (int i = 0; i < results.length(); i++) {
                                    JSONObject post = results.optJSONObject(i);

                                    String id = post.getString("id");
                                    String title = post.optString("title");
                                    String date = post.getString("release_date");
                                    String rate = post.getString("vote_average");
                                    String overview = post.getString("overview");

                                    String Date = "Expires :" + " " + date;

                                    String posterPath = post.getString("poster_path");
                                    String imageUrl = "http://image.tmdb.org/t/p/w185/".concat(posterPath);
                                    Log.e("movieurl", imageUrl);

                                    movie = new Movies(id, imageUrl, title, Date, rate, overview, "");

                                    movieList.add(movie);


                                }
                                mAdapter.setMoviesList(movieList);
                                mAdapter.notifyDataSetChanged();


                            } catch (final JSONException e) {

                                e.printStackTrace();
                            }


                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showContentBD();
                    }
                }
        ) {

        };

        queue.add(request);

    }


}
