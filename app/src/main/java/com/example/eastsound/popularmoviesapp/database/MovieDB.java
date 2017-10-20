package com.example.eastsound.popularmoviesapp.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.eastsound.popularmoviesapp.model.Movie;

import java.util.ArrayList;


public class MovieDB extends ContentProvider {

    private MovieDBHelper movieDBHelper;
    private SQLiteDatabase sqLiteDatabase;

    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(MovieContract.AUTHORITY, MovieDBHelper.DB_NAME, MovieContract.ALL_MOVIES);
        uriMatcher.addURI(MovieContract.AUTHORITY, MovieDBHelper.DB_NAME + "/#", MovieContract.SINGLE_MOVIE);
    }

    public MovieDB() {
    }

    @Override
    public boolean onCreate() {
        boolean ret = true;
        movieDBHelper = new MovieDBHelper(getContext());
        sqLiteDatabase = movieDBHelper.getWritableDatabase();

        if (sqLiteDatabase == null) {
            ret = false;
        }

        if (sqLiteDatabase.isReadOnly()) {
            sqLiteDatabase.close();
            sqLiteDatabase = null;
            ret = false;
        }

        return ret;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        switch (uriMatcher.match(uri)) {
            case MovieContract.ALL_MOVIES:
                return sqLiteDatabase.query(MovieContract.MovieEntry.TABLE_NAME, MovieContract.MovieEntry.columns
                        , null, null, null, null, null);

            case MovieContract.SINGLE_MOVIE:
                return sqLiteDatabase.query(MovieContract.MovieEntry.TABLE_NAME, MovieContract.MovieEntry.columns
                        , MovieContract.MovieEntry.KEY_TABLE + "=" + uri.getLastPathSegment()
                        , null, null, null, null);

            default:
                throw new IllegalArgumentException("Invalid URI: " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        if (uriMatcher.match(uri) != MovieContract.ALL_MOVIES)
            throw new IllegalArgumentException("Invalid URI: " + uri);

        long id = sqLiteDatabase.insert(MovieContract.MovieEntry.TABLE_NAME, null, contentValues);

        if (id > 0) return ContentUris.withAppendedId(uri, id);

        throw new SQLException("Error inserting into table: " + MovieContract.MovieEntry.TABLE_NAME);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        int deleted = 0;

        switch (uriMatcher.match(uri)) {
            case MovieContract.ALL_MOVIES:
                break;

            case MovieContract.SINGLE_MOVIE:
                deleted = sqLiteDatabase.delete(MovieContract.MovieEntry.TABLE_NAME,
                        MovieContract.MovieEntry.KEY_TABLE + "=" + uri.getLastPathSegment(), null);
                break;

            default:
                throw new IllegalArgumentException("Invalid URI: " + uri);
        }

        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {

        int updated = 0;

        switch (uriMatcher.match(uri)) {
            case MovieContract.ALL_MOVIES:
                break;

            case MovieContract.SINGLE_MOVIE:
                String where = MovieContract.MovieEntry.KEY_TABLE + " = " + uri.getLastPathSegment();

                if (!s.isEmpty()) {
                    where += " AND " + s;
                }

                updated = sqLiteDatabase.update(MovieContract.MovieEntry.TABLE_NAME, contentValues, where, strings);
                break;

            default:
                throw new IllegalArgumentException("Invalid URI: " + uri);
        }

        return updated;
    }

    public static ContentValues getContentValuesMovie(Movie movie) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MovieContract.MovieEntry.KEY_TABLE, movie.getId());
        contentValues.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, movie.getPosterUrl());
        contentValues.put(MovieContract.MovieEntry.COLUMN_TITLE, movie.getTitle());
        contentValues.put(MovieContract.MovieEntry.COLUMN_VOTE, movie.getVote());
        contentValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
        contentValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, movie.getOverview());

        return contentValues;
    }

    public static ArrayList<Movie> getMovies(Cursor cursor) {
        ArrayList<Movie> movies = new ArrayList<>();

        Movie movie;

        while (cursor.moveToNext()) {
            movie = setMovie(cursor);
            movies.add(movie);
        }

        return movies;
    }

    @NonNull
    public static Movie setMovie(Cursor cursor) {

        int idIndex = cursor.getColumnIndex(MovieContract.MovieEntry.KEY_TABLE);
        int posterIndex = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER_PATH);
        int titleIndex = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE);
        int voteIndex = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_VOTE);
        int releaseDateIndex = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RELEASE_DATE);
        int overviewIndex = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW);

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

    public static Movie getMovie(Cursor cursor) {

        Movie movie = null;

        if (cursor != null && cursor.moveToFirst()) {
            movie = setMovie(cursor);
        }

        return movie;
    }
}
