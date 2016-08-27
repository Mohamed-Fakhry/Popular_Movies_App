package com.example.eastsound.popularmoviesapp.model;

import java.io.Serializable;

/**
 * Created by Mohamed Fakhry on 02/08/2016.
 */
public class Movie implements Serializable{

    private int id;
    private String posterUrl;
    private String title;
    private double vote;
    private String releaseDate;
    private String overview;

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = "http://image.tmdb.org/t/p/w185/" + posterUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getVote() {
        return vote;
    }

    public void setVote(double vote) {
        this.vote = vote;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate.split("-")[0];
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}
