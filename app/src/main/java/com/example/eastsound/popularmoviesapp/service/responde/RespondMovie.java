package com.example.eastsound.popularmoviesapp.service.responde;

import com.example.eastsound.popularmoviesapp.model.Movie;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

/**
 * Created by Mohamed Fakhry on 27/08/2016.
 */
public class RespondMovie {
    @SerializedName("results")
    ArrayList<Movie> movies;

    public ArrayList<Movie> getMovies() {
        return movies;
    }
}
