package com.example.eastsound.popularmoviesapp;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.detailContainer) != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detailContainer, new DetailFragment()).commit();
        }
    }
}
