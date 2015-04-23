package com.example.gab.movieorganizer;

/**
 * Created by Gab on 4/6/2015.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ReviewsListViewAdapter  extends BaseAdapter {

    private Context mContext;
    private ArrayList<CriticReview> criticReviews;
    private LayoutInflater inflater;

    public ReviewsListViewAdapter(Context c, ArrayList<CriticReview> criticReviews){
        this.criticReviews = criticReviews;
        this.mContext=c;
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return criticReviews.size();
    }

    @Override
    public CriticReview getItem(int position) {
        return criticReviews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = inflater.inflate(R.layout.reviews_list_item, parent, false);
        }
        CriticReview currentCritic = criticReviews.get(position);
        TextView criticTextView = (TextView) v.findViewById(R.id.criticTextView);
        criticTextView.setText(currentCritic.getCritic());
        TextView quoteTextView = (TextView) v.findViewById(R.id.quoteTextView);
        quoteTextView.setText(currentCritic.getQuote());

        return v;
    }
}


