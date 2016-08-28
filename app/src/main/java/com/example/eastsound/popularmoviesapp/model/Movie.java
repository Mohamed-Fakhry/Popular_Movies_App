package com.example.eastsound.popularmoviesapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Mohamed Fakhry on 02/08/2016.
 */
public class Movie implements Serializable {
    @SerializedName("id")
    private int id;

    @SerializedName("poster_path")
    private String posterUrl;

    @SerializedName("title")
    private String title;

    @SerializedName("vote_average")
    private double vote;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("overview")
    private String overview;

    private ArrayList<Trailer> trailers;

    private ArrayList<Review> reviews;

    public String getPosterUrl() {
       if(posterUrl != null) {
           return "http://image.tmdb.org/t/p/w185/" + posterUrl;
       }
        return null;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
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
        return releaseDate.split("-")[0];
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public ArrayList<Trailer> getTrailers() {
        return trailers;
    }

    public void setTrailers(ArrayList<Trailer> trailers) {
        this.trailers = trailers;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }
}
