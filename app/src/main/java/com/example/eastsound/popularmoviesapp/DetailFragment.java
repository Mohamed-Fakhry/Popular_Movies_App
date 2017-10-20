package com.example.eastsound.popularmoviesapp;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.example.eastsound.popularmoviesapp.adapter.ReviewArrayAdapter;
import com.example.eastsound.popularmoviesapp.adapter.TrailerArrayAdapter;
import com.example.eastsound.popularmoviesapp.adapter.viewholder.NonScrollListView;
import com.example.eastsound.popularmoviesapp.database.MovieContract;
import com.example.eastsound.popularmoviesapp.database.MovieDB;
import com.example.eastsound.popularmoviesapp.model.Movie;
import com.example.eastsound.popularmoviesapp.model.Review;
import com.example.eastsound.popularmoviesapp.model.Trailer;
import com.example.eastsound.popularmoviesapp.service.SetupService;
import com.example.eastsound.popularmoviesapp.service.responde.RespondReview;
import com.example.eastsound.popularmoviesapp.service.responde.RespondTrailer;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailFragment extends Fragment {

    Movie movie;
    TrailerArrayAdapter trailerArrayAdapter;
    ReviewArrayAdapter reviewArrayAdapter;

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
    @Bind(R.id.trailerList)
    NonScrollListView trailerLV;
    @Bind(R.id.reviewList)
    NonScrollListView reviewLV;
    @Bind(R.id.favoriteToggleBtn)
    ToggleButton favoriteTBtn;

    public static DetailFragment newInstance(Movie movie) {
        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("movie", movie);
        Log.d("test", movie.toString());
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            movie = getArguments().getParcelable("movie");
        } catch (Exception e) {
            Log.d("test", e.getMessage());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        ButterKnife.bind(this, view);

        if (movie == null) {
            movie = (Movie) getActivity().getIntent().getSerializableExtra("movie");
        }

        displayMovie(movie);

        return view;
    }

    private void displayMovie(Movie movie) {
        if (movie != null) {
            getTrailer(movie);
            getReview(movie);
            setLayoutForMD();
            getActivity().setTitle(movie.getTitle());
        }
    }

    public void setLayoutForMD() {
        movieTitleTV.setText(movie.getTitle());

        Glide.with(getActivity())
                .load("http://image.tmdb.org/t/p/w185/" + movie.getPosterUrl())
                .asBitmap()
                .centerCrop()
                .into(movieImage);

        rateMovie.setText("Rating : " + movie.getVote());

        movieDate.setText(movie.getReleaseDate().split("-")[0]);

        oveView.setText(movie.getOverview());

        setFavoriteTBtn();
    }

    private void setFavoriteTBtn() {

        String stringId = Integer.toString(movie.getId());
        Uri uri = MovieContract.CONTENT_URI;
        uri = uri.buildUpon().appendPath(stringId).build();

        if (MovieDB.getMovie(getActivity().getContentResolver().query(uri, null, null, null, null)) != null)
            movie.setFavorite(true);
        favoriteTBtn.setVisibility(View.VISIBLE);
        favoriteTBtn.setChecked(movie.isFavorite());

        Log.e("Test", movie.isFavorite() + "");

        favoriteTBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movie.setFavorite(!movie.isFavorite());
                if (movie.isFavorite()) {
                    ContentValues movieContentValues = MovieDB.getContentValuesMovie(movie);
                    Uri uri = getActivity().getContentResolver().insert(MovieContract.CONTENT_URI, movieContentValues);
                    if (uri != null)
                        Log.d("Test", "movieSaved");
                } else {
                    String stringId = Integer.toString(movie.getId());
                    Uri uri = MovieContract.CONTENT_URI;
                    uri = uri.buildUpon().appendPath(stringId).build();
                    getActivity().getContentResolver().delete(uri, null, null);
                }
            }
        });
    }

    private void getReview(final Movie movie) {
        SetupService.getServiceMovies.getReviwes(movie.getId()).enqueue(new Callback<RespondReview>() {
            @Override
            public void onResponse(Call<RespondReview> call, Response<RespondReview> response) {
                if (response.body() != null) {
                    ArrayList<Review> reviews = response.body().getReviews();
                    movie.setReviews(reviews);
                    if (reviews != null && getActivity() != null) {
                        reviewArrayAdapter = new ReviewArrayAdapter(getActivity(), reviews);

                        View reviewHeader =
                                getActivity().getLayoutInflater().inflate(R.layout.header, null);

                        TextView trailerTV = (TextView) reviewHeader.findViewById(R.id.head);
                        if (reviews.size() == 0) {
                            trailerTV.setText("No reviews");
                            reviewLV.addHeaderView(reviewHeader);
                        }

                        reviewLV.setAdapter(reviewArrayAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<RespondReview> call, Throwable t) {
            }
        });
    }

    private void getTrailer(final Movie movie) {
        SetupService.getServiceMovies.getTrailer(movie.getId()).enqueue(new Callback<RespondTrailer>() {
            @Override
            public void onResponse(Call<RespondTrailer> call, Response<RespondTrailer> response) {
                if (response.body() != null) {
                    ArrayList<Trailer> trailers = response.body().getTrailers();
                    movie.setTrailers(trailers);
                    if (trailers != null && getActivity() != null)
                        setTrailerRV(trailers);
                }
            }

            private void setTrailerRV(final ArrayList<Trailer> trailers) {
                trailerArrayAdapter = new TrailerArrayAdapter(getActivity(), trailers);

                View trailerHeader =
                        getActivity().getLayoutInflater().inflate(R.layout.header, null);

                TextView trailerTV = (TextView) trailerHeader.findViewById(R.id.head);
                if (trailers.size() == 0) {
                    trailerTV.setText("No trailers");
                } else {
                    trailerTV.setText("Trailers");
                }
                trailerLV.addHeaderView(trailerHeader);
                trailerLV.setAdapter(trailerArrayAdapter);
                trailerLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                            long arg3) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailers.get(arg2 - 1).getKey()));
                        startActivity(browserIntent);
                    }

                });
            }

            @Override
            public void onFailure(Call<RespondTrailer> call, Throwable t) {
            }
        });
    }

}
