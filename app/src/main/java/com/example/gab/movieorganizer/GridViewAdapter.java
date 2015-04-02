package com.example.gab.movieorganizer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
        return null;
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
    }
}
