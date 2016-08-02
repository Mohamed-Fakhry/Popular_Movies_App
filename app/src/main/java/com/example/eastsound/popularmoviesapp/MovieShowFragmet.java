package com.example.eastsound.popularmoviesapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.eastsound.popularmoviesapp.adapter.MovieAdapter;
import com.example.eastsound.popularmoviesapp.model.Movie;
import com.example.eastsound.popularmoviesapp.service.MovieDataParser;
import java.util.ArrayList;
import butterknife.Bind;
import butterknife.ButterKnife;


public class MovieShowFragmet extends Fragment {

    @Bind(R.id.moviesRV)
        RecyclerView movieRV;

    public ArrayList<Movie> movies = new ArrayList<>();

    public MovieAdapter movieAdapter;

    public MovieShowFragmet() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_fragmet, container, false);
        ButterKnife.bind(this, view);
        getData();
        return view;
    }

    private void getData() {
        MovieDataParser movieDataParser = new MovieDataParser();
        movieDataParser.execute("popular", this);
        movieRV.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        movieAdapter = new MovieAdapter(getActivity(), movies);
        movieRV.setAdapter(movieAdapter);
    }

}
