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

    boolean favorite;
    private ArrayList<Trailer> trailers;

    private ArrayList<Review> reviews;

    public Movie() {
    }

    public Movie(int id, String posterUrl, String title, double vote, String releaseDate, String overview, boolean favorite) {
        this.id = id;
        this.posterUrl = posterUrl;
        this.title = title;
        this.vote = vote;
        this.releaseDate = releaseDate;
        this.overview = overview;
        this.favorite = favorite;
    }

    public String getPosterUrl() {
       if(posterUrl != null) {
           return posterUrl;
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
        return releaseDate;
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

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
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

    @Override
    public boolean equals(Object o) {
        boolean flag = false;
        if (o != null && o instanceof Movie){
            Movie movie = (Movie) o;
            if(this.posterUrl.equals(movie.getPosterUrl()) && this.getTitle().equals(movie.getTitle())
                    && this.getVote() != movie.getVote() && this.getOverview().equals(movie.getOverview()))
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "overview='" + overview + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", vote=" + vote +
                ", title='" + title + '\'' +
                ", posterUrl='" + posterUrl + '\'' +
                ", id=" + id +
                '}';
    }
}
