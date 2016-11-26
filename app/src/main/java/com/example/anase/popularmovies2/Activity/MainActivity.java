package com.example.anase.popularmovies2.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.anase.popularmovies2.Movies;
import com.example.anase.popularmovies2.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainActivityFragment.Callback{


    private boolean twoPane;
    private DetailActivityFragment details_Fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().hide();
        if (findViewById(R.id.fragment_details) != null) {
            twoPane = true;
        } else {
            twoPane = false;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public void displayMovies(Movies movie) {

        if (twoPane) {
            details_Fragment = DetailActivityFragment.newInstance(movie);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_details, details_Fragment).commit();

        } else {
            Intent i = new Intent(MainActivity.this, DetailActivity.class);
            i.putExtra("movie", movie);
            startActivity(i);
        }
    }
}
