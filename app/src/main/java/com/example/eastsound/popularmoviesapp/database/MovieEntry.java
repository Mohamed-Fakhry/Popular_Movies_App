package com.example.eastsound.popularmoviesapp.database;

/**
 * Created by EAST SOUND on 01/09/2016.
 */
public class MovieEntry {


    static final String TABLE_NAME = "MOVIETABLE";

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
