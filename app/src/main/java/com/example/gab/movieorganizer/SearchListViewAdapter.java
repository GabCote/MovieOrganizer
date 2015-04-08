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

import java.util.ArrayList;

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
    public Object getItem(int position) {
        return null;
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
        Movie currentMovie = movies.get(position);
        if (imageViewMovie.getDrawable() == null) {
            new ImageLoadTask(currentMovie.getImgUrl(), imageViewMovie).execute();
        }
        textViewMovieTitle.setText(currentMovie.getTitre().toString());
        //chercher les images de l'API
        //imageView.setImageResource();
        return v;
    }
}

   /* @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }
        Movie currentMovie = movies.get(position);

        new ImageLoadTask(currentMovie.getImgUrl(),imageView).execute();
        //chercher les images de l'API
        //imageView.setImageResource();
        return imageView;
    }*/


