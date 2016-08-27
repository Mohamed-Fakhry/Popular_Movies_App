package com.example.eastsound.popularmoviesapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.eastsound.popularmoviesapp.model.Movie;

import butterknife.Bind;
import butterknife.ButterKnife;


public class DetailFragment extends Fragment {

    Movie movie;

    @Bind(R.id.movieTitle)
        TextView movieTitleTV;
    @Bind(R.id.movieImage)
        ImageView movieImage;
    @Bind(R.id.rateMovie)
        TextView rateMovie;
    @Bind(R.id.movieDate)
        TextView movieDate;
    @Bind(R.id.overviewMovie)
        TextView oveView;

    public DetailFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        ButterKnife.bind(this, view);

        movie = (Movie) getActivity().getIntent().getSerializableExtra("movie");

        setLayoutForMD();

        return view;
    }

    public void setLayoutForMD() {
        movieTitleTV.setText(movie.getTitle());

        Glide.with(getActivity())
                .load(movie.getPosterUrl())
                .asBitmap()
                .centerCrop()
                .into(movieImage);

        rateMovie.setText("Rating : " + movie.getVote());
        movieDate.setText(movie.getReleaseDate());
        oveView.setText(movie.getOverview());
    }
}
