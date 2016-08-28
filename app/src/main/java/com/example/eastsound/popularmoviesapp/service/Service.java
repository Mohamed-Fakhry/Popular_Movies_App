package com.example.eastsound.popularmoviesapp.service;

import com.example.eastsound.popularmoviesapp.service.responde.RespondMovie;
import com.example.eastsound.popularmoviesapp.service.responde.RespondReview;
import com.example.eastsound.popularmoviesapp.service.responde.RespondTrailer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Mohamed Fakhry on 27/08/2016.
 */
public interface Service {
    @GET("{type}?api_key=" + Constant.API_KEY)
    Call<RespondMovie> getMovies(@Path("type") String type);

//    @GET("/popular?api_key=" + Constant.API_KEY)
//    Call<RespondMovie> getPopularMovie();

    @GET("{id}/reviews?api_key=" + Constant.API_KEY)
    Call<RespondReview> getReviwes(@Path("id") int id);

    @GET("{id}/videos?api_key=" + Constant.API_KEY)
    Call<RespondTrailer> getTrailer(@Path("id") int id);
}
