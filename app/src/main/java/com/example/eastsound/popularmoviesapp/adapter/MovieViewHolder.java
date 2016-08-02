package com.example.eastsound.popularmoviesapp.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.example.eastsound.popularmoviesapp.R;
import com.example.eastsound.popularmoviesapp.model.Movie;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Mohamed Fakhry on 02/08/2016.
 */
public class MovieViewHolder extends RecyclerView.ViewHolder {

    Activity activity;
    @Bind(R.id.movieImage)
    ImageView posterIV;
    int i = 0;

    public MovieViewHolder(View itemView, Activity activity) {
        super(itemView);
        this.activity = activity;
//        posterIV = (ImageView) activity.findViewById(R.id.movieImage);
        ButterKnife.bind(this, itemView);
    }

    public void setMovieView(Movie movieView){
        if (posterIV != null){
            Glide.with(activity)
                    .load(movieView.getPosterUrl())
                    .asBitmap()
                    .into(posterIV);
        }
        else {
            Log.e("image", i++ +"null");
        }
    }
}
