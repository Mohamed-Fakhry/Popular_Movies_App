package com.example.eastsound.popularmoviesapp.service;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.example.eastsound.popularmoviesapp.MovieShowFragmet;
import com.example.eastsound.popularmoviesapp.model.Movie;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Mohamed Fakhry on 02/08/2016.
 */
public class MovieDataParser extends AsyncTask<Object, Void, ArrayList<Movie> >{

    private final String LOG_TAG = MovieDataParser.class.getSimpleName();

    private final String API_KEY = "e0e7f86d51fd9f1c11d41bea49e14f25";
    private final String MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie/";

    MovieShowFragmet movieShowFragmet;

    public static ArrayList<Movie> getMovie(String movielistJOStr) throws JSONException {
        ArrayList<Movie> movies = new ArrayList<>();

        final String RESULT = "results";

        JSONObject movielistJO = new JSONObject(movielistJOStr);
        JSONArray movieJsonArray = movielistJO.getJSONArray(RESULT);

        for (int i = 0; i < movieJsonArray.length(); i++){
            Movie movie = new Movie();

            JSONObject movieJson = movieJsonArray.getJSONObject(i);

            getMovieData(movie, movieJson);

            movies.add(movie);
        }

        return movies;
    }

    private static void getMovieData(Movie movie, JSONObject movieJson) throws JSONException {
        final String POSTER_PATH = "poster_path";
        final String ID = "id";
        final String TITLE = "title";
        final String VOTE_AVREAGE = "vote_average";
        final String RELEASE_DATA = "release_date";
        final String OVERVIEW = "overview";

        int movieId = movieJson.getInt(ID);
        movie.setId(movieId);

        String moviePoster = movieJson.getString(POSTER_PATH);
        movie.setPosterUrl(moviePoster);

        String movieTilte = movieJson.getString(TITLE);
        movie.setTitle(movieTilte);

        double movieRate = movieJson.getDouble(VOTE_AVREAGE);
        movie.setVote(movieRate);

        String movieReleaseDate = movieJson.getString(RELEASE_DATA);
        movie.setReleaseDate(movieReleaseDate);

        String overView = movieJson.getString(OVERVIEW);
        movie.setOverview(overView);
    }

    @Override
    protected ArrayList<Movie> doInBackground(Object... params) {
        BufferedReader reader = null;
        String movieJsonStr = null;
        HttpURLConnection urlConnection = null;

        String type = String.valueOf(params[0]);
        movieShowFragmet = (MovieShowFragmet) params[1];

        try {

            final String UrlStr = MOVIE_BASE_URL + type + "?api_key=" + API_KEY;
            URL url = new URL(UrlStr);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line = reader.readLine();
            while(line != null) {
                buffer.append(line + "\n");
                line = reader.readLine();
            }

            if (buffer.length() == 0) {
                return null;
            }

            movieJsonStr = buffer.toString();
            Log.v(LOG_TAG, "doInBackground: "+ movieJsonStr);

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {

            if (urlConnection != null) {
                urlConnection.disconnect();
            }

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        try {
            return getMovie(movieJsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected synchronized void onPostExecute(ArrayList<Movie> movies) {
        movieShowFragmet.notify(movies);
    }

}
