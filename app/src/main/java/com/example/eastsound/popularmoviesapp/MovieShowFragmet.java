package com.example.eastsound.popularmoviesapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.example.eastsound.popularmoviesapp.adapter.MovieAdapter;
import com.example.eastsound.popularmoviesapp.database.MovieDB;
import com.example.eastsound.popularmoviesapp.model.Movie;
import com.example.eastsound.popularmoviesapp.model.Review;
import com.example.eastsound.popularmoviesapp.model.Trailer;
import com.example.eastsound.popularmoviesapp.service.SetupService;
import com.example.eastsound.popularmoviesapp.service.responde.RespondMovie;
import com.example.eastsound.popularmoviesapp.service.responde.RespondReview;
import com.example.eastsound.popularmoviesapp.service.responde.RespondTrailer;

import java.util.ArrayList;
import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieShowFragmet extends Fragment {

    @Bind(R.id.moviesRV)
        RecyclerView movieRV;

    ArrayList<Movie> movies = new ArrayList<>();

    private MovieAdapter movieAdapter;
    boolean flag = false;

    @Override
    public void onStart() {
        super.onStart();
        if(flag) {
            getData();
            flag = false;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        int size = movies.size();

        if(size != 0) {
            for (int i = 0; i < size; i++) {
                movies.remove(0);
            }
            movieAdapter.notifyItemRangeRemoved(0, size);
        }
        flag = true;
        if(id == R.id.setting){
            startActivity(new Intent(getActivity(), Setting.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_fragmet, container, false);
        ButterKnife.bind(this, view);
        setRV();
        getData();
        return view;
    }

    private void setRV() {
        movieRV.setItemAnimator(new SlideInUpAnimator());
        movieRV.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        movieAdapter = new MovieAdapter(getActivity(), movies);
        movieRV.setAdapter(movieAdapter);
    }

    public void notifyARV(ArrayList<Movie> movies) {
        if (movies != null) {
            for (int i = 0;i < movies.size();i++) {
                Movie movie = movies.get(i);
                if(movie.getPosterUrl() == null)
                    continue;
                this.movies.add(movie);
                movieAdapter.notifyItemInserted(i);
            }
            movieRV.setHasFixedSize(true);
        }
    }

    private void getData() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String type = preferences.getString("type", "popular");
        if(type.equals("Fovrite")) {
            getFovriteMovies();
        } else
            getMovies(type);
        setTitle(type);
    }

    private void getFovriteMovies() {
        MovieDB movieDB = new MovieDB(getActivity());
        movieDB.open();
        notifyARV(movieDB.getMovies());
        movieDB.close();
    }

    private void getMovies(String type) {
        SetupService.getServiceMovies.getMovies(type).enqueue(new Callback<RespondMovie>() {
            @Override
            public void onResponse(Call<RespondMovie> call, Response<RespondMovie> response) {
                if(response.body() != null) {
                    ArrayList<Movie> resopnedMovies = response.body().getMovies();
                    notifyARV(resopnedMovies);
                }
            }

            @Override
            public void onFailure(Call<RespondMovie> call, Throwable t) {}
        });
    }

    private void setTitle(String type) {
        String packageName = getActivity().getPackageName();
        int id = getResources().getIdentifier(type, "string", packageName);
        getActivity().setTitle(getString(id));
    }
}
