package com.example.gab.movieorganizer;

import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SlidingDrawer;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


public class MovieInformationActivity extends ActionBarActivity implements SlidingDrawer.OnDrawerCloseListener, SlidingDrawer.OnDrawerOpenListener {
    Movie currentMovie;
    RatingBar rating;
    TextView movieTitleTextView;
    TextView movieSynopsisTextView;
    TextView movieCastTextView;
    ImageView movieImageView;
    DBHelper dbh;
    CheckBox seen;
    CheckBox wish;
    SlidingDrawer slidingDrawer;
    Button reviewsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_information);
        final Movie currentMovie =(Movie) getIntent().getParcelableExtra("movie");
        SlidingDrawer slidingDrawer = (SlidingDrawer) findViewById(R.id.slidingDrawer);
        slidingDrawer.setOnDrawerCloseListener(this);
        slidingDrawer.setOnDrawerOpenListener(this);
        reviewsButton = (Button) findViewById(R.id.reviewsButton);
        movieTitleTextView = (TextView) findViewById(R.id.movieTitleTextView);
        movieImageView = (ImageView) findViewById(R.id.movieImageView);
        movieImageView.setScaleType(ImageView.ScaleType.FIT_START);
        movieSynopsisTextView = (TextView) findViewById(R.id.movieSynopsisTextView);
        movieCastTextView = (TextView) findViewById(R.id.castTextView);
        Picasso.with(this).load(currentMovie.getImgUrl()).into(movieImageView);
        //movieImageView.setScaleType(ImageView.ScaleType.FIT_START);

        rating = (RatingBar)findViewById(R.id.ratingBar_movie_info);
        rating.setRating(currentMovie.getMyRating());
        rating.setVisibility(View.INVISIBLE);
        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                dbh.updateMovie(DBHelper.TABLE_SEEN,currentMovie,rating);
            }
        });

        movieTitleTextView.setText(currentMovie.getTitre() + " (" + currentMovie.getAnnee() + ")");
        movieSynopsisTextView.setText(currentMovie.getSynopsis());
        movieSynopsisTextView.setMovementMethod(new ScrollingMovementMethod());
        if(currentMovie.getCast() != null){
            movieCastTextView.setText("Cast: " + currentMovie.getCast());}
        dbh= new DBHelper(MainActivity.myContext);
        seen = (CheckBox) findViewById(R.id.seen_movie_info);

        //check si deja dans BD si oui doit le mettre CHECKED
        boolean existS = dbh.isMovieExist(DBHelper.TABLE_SEEN,currentMovie);
        if(existS){
            rating.setVisibility(View.VISIBLE);
            seen.setChecked(true);
        }
        seen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (isChecked){
                    rating.setVisibility(View.VISIBLE);
                    dbh.insertMovie(currentMovie, DBHelper.TABLE_SEEN);
                }
                else{
                    rating.setVisibility(View.INVISIBLE);
                    rating.setRating(0.0f);
                    dbh.deleteMovie(currentMovie, DBHelper.TABLE_SEEN);
                }
            }
        });
        wish = (CheckBox)  findViewById(R.id.wishlist_movie_info);
        //check si deja dans BD si oui doit le mettre CHECKED
        boolean existW = dbh.isMovieExist(DBHelper.TABLE_WISH,currentMovie);
        if(existW)
            wish.setChecked(true);
        wish.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (isChecked){
                    dbh.insertMovie(currentMovie, DBHelper.TABLE_WISH);

                }
                else{
                     dbh.deleteMovie(currentMovie, DBHelper.TABLE_WISH);
                }
            }
        });

        View currentView = (View)findViewById(android.R.id.content);
        TaskParamsRottenTomatoesApi downloadParams = new TaskParamsRottenTomatoesApi("Reviews", currentMovie.getReviewLink());
        new DownloadFromApi(currentView).execute(downloadParams);
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

    @Override
    public void onDrawerClosed() {
        Drawable img = getBaseContext().getResources().getDrawable(android.R.drawable.arrow_up_float);
        reviewsButton.setCompoundDrawablesWithIntrinsicBounds(null,null,img,null);
    }

    @Override
    public void onDrawerOpened() {
        Drawable img = getBaseContext().getResources().getDrawable(android.R.drawable.arrow_down_float);
        reviewsButton.setCompoundDrawablesWithIntrinsicBounds(null,null,img,null);
    }
}
