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

    private final String API_KEY = "c9d5b152a06f9f0ebceb82fbace5c84c";
    private final String MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie/";

    MovieShowFragmet movieShowFragmet;

    public ArrayList<Movie> moviesAL =  new ArrayList<>();

    public static ArrayList<Movie> getMovie(String movielistJOStr) throws JSONException {
        ArrayList<Movie> movies = new ArrayList<>();

        final String RESULT = "results";
        final String POSTER_PATH = "poster_path";

        JSONObject movielistJO = new JSONObject(movielistJOStr);
        JSONArray movieJsonArray = movielistJO.getJSONArray(RESULT);

        for (int i = 0; i < movieJsonArray.length(); i++){
            Movie movie = new Movie();
            JSONObject movieJson = movieJsonArray.getJSONObject(i);
            String moviePoster = movieJson.getString(POSTER_PATH);
            movie.setPosterUrl(moviePoster);
            Log.d("Meeeee"+ i + " >>>>", moviePoster);
            movies.add(movie);
        }

        return movies;
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
        } finally {
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
        if (movies != null) {
            for (int i = 0;i <movies.size();i++) {
                movieShowFragmet.movies.add(movies.get(i));
                movieShowFragmet.movieAdapter.notifyItemChanged(i);
                Log.e("m>>>>>>",movieShowFragmet.movieAdapter.getItemCount()+"");
            }

//            MovieShowFragmet movieShowFragmet = new MovieShowFragmet();
//            movieShowFragmet.notifiyAdapt(movies);
        }
    }



}
