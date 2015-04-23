package com.example.gab.movieorganizer;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Anna on 30/03/2015.
 */
public class GridViewAdapter  extends BaseAdapter {

    private Context mContext;
    private ArrayList<Movie> movies;

    public GridViewAdapter(Context c){
        this.mContext=c;
    }

    public GridViewAdapter(Context c, ArrayList<Movie> pMovies){
        this.movies = pMovies;
        this.mContext=c;
    }
    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(250,280));
            imageView.setPadding(0, 8, 0, 8);
        } else {
            imageView = (ImageView) convertView;
        }
        Movie currentMovie = movies.get(position);

        Picasso.with(imageView.getContext()).load(currentMovie.getImgUrl()).into(imageView);
        //new ImageLoadTask(currentMovie.getImgUrl(),imageView).execute();
        //chercher les images de l'API
        //imageView.setImageResource();
        return imageView;
    }
}
