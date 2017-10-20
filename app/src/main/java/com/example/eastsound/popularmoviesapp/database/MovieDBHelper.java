package com.example.eastsound.popularmoviesapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.example.eastsound.popularmoviesapp.database.MovieContract.MovieEntry;

public class MovieDBHelper  extends SQLiteOpenHelper {

    static final String DB_NAME = "movies";
    private static int versionDB = 1;

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
