package com.example.eastsound.popularmoviesapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.eastsound.popularmoviesapp.R;
import com.example.eastsound.popularmoviesapp.model.Review;

import java.util.ArrayList;

/**
 * Created by EAST SOUND on 28/08/2016.
 */
public class ReviewArrayAdapter extends ArrayAdapter<Review> {
    ArrayList<Review> reviews;
    static int resource = R.layout.row_review;
    Activity activity;

    public ReviewArrayAdapter(Activity activity, ArrayList<Review> reviews) {
        super(activity, resource, reviews);
        this.reviews = reviews;
        this.activity = activity;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(resource, parent, false);
        TextView authorTV = (TextView) rowView.findViewById(R.id.author);
        TextView contentTV = (TextView) rowView.findViewById(R.id.content);
        TextView readMoreTV = (TextView) rowView.findViewById(R.id.readMore);
        authorTV.setText(reviews.get(position).getAuthor());
        contentTV.setText(reviews.get(position).getContent());
        readMoreTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(reviews.get(position).getUrl()));
                activity.startActivity(browserIntent);
            }
        });

        return rowView;
    }

    @Override
    public int getCount() {
        if(reviews != null){
            return reviews.size() < 3 ? reviews.size(): 3;
        }
        return 0;
    }
}
