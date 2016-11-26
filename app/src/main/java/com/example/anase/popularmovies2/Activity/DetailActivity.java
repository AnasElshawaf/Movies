package com.example.anase.popularmovies2.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.anase.popularmovies2.Movies;
import com.example.anase.popularmovies2.R;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().hide();


        Movies movie = getIntent().getParcelableExtra("movie");
        DetailActivityFragment details_Fragment = DetailActivityFragment.newInstance(movie);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_details, details_Fragment).commit();

    }

}
