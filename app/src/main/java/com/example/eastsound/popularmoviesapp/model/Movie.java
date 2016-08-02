package com.example.eastsound.popularmoviesapp.model;

/**
 * Created by EAST SOUND on 02/08/2016.
 */
public class Movie {
    private String posterUrl;

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = "http://image.tmdb.org/t/p/w185/" + posterUrl;
    }

}
