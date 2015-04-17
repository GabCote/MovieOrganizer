package com.example.gab.movieorganizer;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class MovieInformationActivity extends ActionBarActivity {
    Movie currentMovie;
    TextView movieTitleTextView;
    TextView movieSynopsisTextView;
    ImageView movieImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_information);
        Movie currentMovie =(Movie) getIntent().getParcelableExtra("movie");
        movieTitleTextView = (TextView) findViewById(R.id.movieTitleTextView);
        movieImageView = (ImageView) findViewById(R.id.movieImageView);
        movieSynopsisTextView = (TextView) findViewById(R.id.movieSynopsisTextView);
        Picasso.with(this).load(currentMovie.getImgUrl()).into(movieImageView);

        movieTitleTextView.setText(currentMovie.getTitre() + "(" + currentMovie.getAnnee() + ")");
        movieSynopsisTextView.setText(currentMovie.getSynopsis());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
