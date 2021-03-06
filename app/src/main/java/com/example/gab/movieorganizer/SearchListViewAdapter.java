package com.example.gab.movieorganizer;

/**
 * Created by Gab on 4/6/2015.
 */

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Comparator;

public class SearchListViewAdapter  extends BaseAdapter {

    private Context mContext;
    private ArrayList<Movie> movies;
    private LayoutInflater inflater;

    public SearchListViewAdapter(Context c, ArrayList<Movie> pMovies){
        this.movies = pMovies;
        this.mContext=c;
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Movie getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
           v = inflater.inflate(R.layout.search_list_item, parent, false);
        }
        ImageView imageViewMovie = (ImageView) v.findViewById(R.id.imageViewMovie);
        TextView textViewMovieTitle = (TextView) v.findViewById(R.id.textViewMovieTitle);
        TextView textViewRating = (TextView) v.findViewById(R.id.textRating);
        TextView textViewYear = (TextView) v.findViewById(R.id.textYear);
        Movie currentMovie = movies.get(position);
        Picasso.with(v.getContext()).load(currentMovie.getImgUrl()).into(imageViewMovie);
        textViewMovieTitle.setText(currentMovie.getTitre().toString());
        textViewMovieTitle.setTextColor(Color.BLACK);
        textViewRating.setText(currentMovie.getRating() + "%");
        textViewRating.setTextColor(Color.BLACK);
        textViewYear.setText("(" + currentMovie.getAnnee() + ")");
        textViewYear.setTextColor(Color.BLACK);
        //chercher les images de l'API
        //imageView.setImageResource();
        return v;
    }

    public ArrayList<Movie> getMovieList(){return this.movies;}
}


