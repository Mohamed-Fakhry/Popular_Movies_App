package com.example.eastsound.popularmoviesapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.eastsound.popularmoviesapp.R;
import com.example.eastsound.popularmoviesapp.model.Movie;

import java.util.ArrayList;

/**
 * Created by Mohamed Fakhry on 02/08/2016.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    ArrayList<Movie> movies;
    Activity movieActivity;

    public MovieAdapter(Activity activity, ArrayList<Movie> movies) {
        super();
        this.movies = movies;
        this.movieActivity = activity;
    }


    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MovieViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_movie,null), movieActivity);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.setMovieView(movies.get(position));
    }

    @Override
    public int getItemCount(){
        if(movies != null) return movies.size();
        else return 0;
    }
}
