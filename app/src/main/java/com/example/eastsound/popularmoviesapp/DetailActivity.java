package com.example.eastsound.popularmoviesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.eastsound.popularmoviesapp.R;
import com.example.eastsound.popularmoviesapp.model.Movie;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Movie movie = getIntent().getParcelableExtra("movie");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.detailContainer, DetailFragment.newInstance(movie))
                .commit();
    }
}
