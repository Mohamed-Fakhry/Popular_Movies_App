package com.example.eastsound.popularmoviesapp.database;


import android.net.Uri;
import android.provider.BaseColumns;

public class MovieContract {

    static final String AUTHORITY = "com.example.eastsound.popularmoviesapp";
    public static final Uri CONTENT_URI =
            Uri.parse("content://" + AUTHORITY + "/" + MovieDBHelper.DB_NAME);

    static final int ALL_MOVIES = 1;
    static final int SINGLE_MOVIE = 2;

    public static final class MovieEntry implements BaseColumns {
        static final String TABLE_NAME = "movies";

        static final String KEY_TABLE = "_id";
        static final String COLUMN_POSTER_PATH = "poster_path";
        static final String COLUMN_TITLE = "title";
        static final String COLUMN_VOTE = "vote";
        static final String COLUMN_RELEASE_DATE = "release_date";
        static final String COLUMN_OVERVIEW = "overview";
        static final String[] columns = new String[]
                {KEY_TABLE, COLUMN_POSTER_PATH, COLUMN_TITLE,
                        COLUMN_VOTE, COLUMN_RELEASE_DATE, COLUMN_OVERVIEW};

    }

}
