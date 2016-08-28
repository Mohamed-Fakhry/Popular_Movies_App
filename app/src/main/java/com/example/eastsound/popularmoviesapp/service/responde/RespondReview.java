package com.example.eastsound.popularmoviesapp.service.responde;

import com.example.eastsound.popularmoviesapp.model.Review;
import com.example.eastsound.popularmoviesapp.model.Trailer;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

/**
 * Created by Mohamed Fakhry on 27/08/2016.
 */
public class RespondReview {
    @SerializedName("results")
    ArrayList<Review> reviews;

    public ArrayList<Review> getReviews() {
        return reviews;
    }
}
