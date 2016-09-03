package com.example.eastsound.popularmoviesapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.example.eastsound.popularmoviesapp.model.Movie;

import java.util.ArrayList;

/**
 * Created by EAST SOUND on 01/09/2016.
 */
public class MovieDB {

    private static final String DB_NAME = "MOVIEDB";
    private static int versionDB = 1;

    private MovieDBHelper movieDBHelper;
    private final Context context;
    private SQLiteDatabase sqLiteDatabase;

    public MovieDB(Context context) {
        this.context = context;
    }

    private static class MovieDBHelper extends SQLiteOpenHelper{

        public MovieDBHelper(Context context) {
            super(context, DB_NAME, null, versionDB);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String sql = getSql();
            db.execSQL(sql);
        }

        @NonNull
        private String getSql() {
            return "CREATE TABLE " + MovieEntry.TABLE_NAME + " ( " +
                    MovieEntry.KEY_TABLE + " INTEGER PRIMARY KEY, " +
                    MovieEntry.COLUMN_POSTER_PATH + " TEXT UNIQUE NOT NULL, " +
                    MovieEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                    MovieEntry.COLUMN_VOTE + " DOUBLE NOT NULL, " +
                    MovieEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
                    MovieEntry.COLUMN_OVERVIEW + " TEXT NOT NULL );";
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    public MovieDB open() {
        movieDBHelper = new MovieDBHelper(context);
        sqLiteDatabase =  movieDBHelper.getWritableDatabase();
        return this;
    }

    public boolean saveMovie(Movie movie) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MovieEntry.KEY_TABLE, movie.getId());
        contentValues.put(MovieEntry.COLUMN_POSTER_PATH, movie.getPosterUrl());
        contentValues.put(MovieEntry.COLUMN_TITLE, movie.getTitle());
        contentValues.put(MovieEntry.COLUMN_VOTE, movie.getVote());
        contentValues.put(MovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
        contentValues.put(MovieEntry.COLUMN_OVERVIEW, movie.getOverview());

        if(sqLiteDatabase.insert(MovieEntry.TABLE_NAME, null, contentValues) != -1) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Movie> getMovies() {
        ArrayList<Movie> movies = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.query(MovieEntry.TABLE_NAME, MovieEntry.columns
                , null, null, null, null, null);

        Movie movie;

        int idIndex = cursor.getColumnIndex(MovieEntry.KEY_TABLE);
        int posterIndex = cursor.getColumnIndex(MovieEntry.COLUMN_POSTER_PATH);
        int titleIndex = cursor.getColumnIndex(MovieEntry.COLUMN_TITLE);
        int voteIndex = cursor.getColumnIndex(MovieEntry.COLUMN_VOTE);
        int releaseDateIndex = cursor.getColumnIndex(MovieEntry.COLUMN_RELEASE_DATE);
        int overviewIndex = cursor.getColumnIndex(MovieEntry.COLUMN_OVERVIEW);

        while (cursor.moveToNext()) {
            movie = setMovie(cursor, idIndex, posterIndex, titleIndex
                    , voteIndex, releaseDateIndex, overviewIndex);
            movies.add(movie);
        }

        return movies;
    }

    @NonNull
    public Movie setMovie(Cursor cursor, int idIndex, int posterIndex, int titleIndex
            , int voteIndex, int releaseDateIndex, int overviewIndex) {
        Movie movie;
        int id = cursor.getInt(idIndex);
        String poster = cursor.getString(posterIndex);
        String title = cursor.getString(titleIndex);
        double vote = cursor.getDouble(voteIndex);
        String date = cursor.getString(releaseDateIndex);
        String overview = cursor.getString(overviewIndex);

        movie = new Movie(id, poster, title, vote, date, overview, true);

        return movie;
    }

    public Movie getMovie(int id) {
        Cursor cursor = sqLiteDatabase.query(MovieEntry.TABLE_NAME, MovieEntry.columns
                , MovieEntry.KEY_TABLE + "=" + id
                , null, null, null, null);
        Movie movie = null;

        int idIndex = cursor.getColumnIndex(MovieEntry.KEY_TABLE);
        int posterIndex = cursor.getColumnIndex(MovieEntry.COLUMN_POSTER_PATH);
        int titleIndex = cursor.getColumnIndex(MovieEntry.COLUMN_TITLE);
        int voteIndex = cursor.getColumnIndex(MovieEntry.COLUMN_VOTE);
        int releaseDateIndex = cursor.getColumnIndex(MovieEntry.COLUMN_RELEASE_DATE);
        int overviewIndex = cursor.getColumnIndex(MovieEntry.COLUMN_OVERVIEW);

        if(cursor != null && cursor.moveToFirst()) {
            movie = setMovie(cursor, idIndex, posterIndex, titleIndex
                    , voteIndex, releaseDateIndex, overviewIndex);
        }

        return movie;
    }

    public boolean deleteMovie(int id) {
        if(sqLiteDatabase.delete(MovieEntry.TABLE_NAME, MovieEntry.KEY_TABLE + "=" + id, null) != 0)
            return true;
        else
            return false;
    }

    public void close() {
        movieDBHelper.close();
    }
}
