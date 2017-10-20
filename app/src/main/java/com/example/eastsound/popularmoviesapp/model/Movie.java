package com.example.eastsound.popularmoviesapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Movie implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.posterUrl);
        dest.writeString(this.title);
        dest.writeDouble(this.vote);
        dest.writeString(this.releaseDate);
        dest.writeString(this.overview);
        dest.writeByte(this.favorite ? (byte) 1 : (byte) 0);
        dest.writeTypedList(this.trailers);
        dest.writeTypedList(this.reviews);
    }

    protected Movie(Parcel in) {
        this.id = in.readInt();
        this.posterUrl = in.readString();
        this.title = in.readString();
        this.vote = in.readDouble();
        this.releaseDate = in.readString();
        this.overview = in.readString();
        this.favorite = in.readByte() != 0;
        this.trailers = in.createTypedArrayList(Trailer.CREATOR);
        this.reviews = in.createTypedArrayList(Review.CREATOR);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
