package com.example.gab.movieorganizer;



import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Anna on 19/03/2015.
 */
public class SimplePagerFragment extends Fragment implements View.OnClickListener{
    DBHelper dbh = new DBHelper(MainActivity.myContext);
    GridView gridViewUpcomingMovies;
    ListView lv2, lv3, lv4;
    TextView textSeen;
    Cursor c;
    ListViewAdapter listViewAdapter, lva;
    ImageButton dice_iButton;
    RadioGroup rg2,rg3,rg4;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Bundle args = getArguments();

        switch(args.getInt("id")+1){//ne pas commencer par zero (id +1)
            case 1:
                final View rootView1 = inflater.inflate(R.layout.home_layout, container, false);
                TaskParamsRottenTomatoesApi paramsDownload = new TaskParamsRottenTomatoesApi("Accueil", null);
                new DownloadFromApi(rootView1).execute(paramsDownload);
                gridViewUpcomingMovies = (GridView)rootView1.findViewById(R.id.gridView);
                gridViewUpcomingMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {

                        Movie m = (Movie) gridViewUpcomingMovies.getItemAtPosition(position);
                        Intent intent = new Intent(rootView1.getContext(), MovieInformationActivity.class);
                        intent.putExtra("movie", m);
                        startActivity(intent);
                    }
                });
                return rootView1;
            case 2:
                final View rootView2 = inflater.inflate(R.layout.seen_layout, container, false);
                lv2= (ListView)rootView2.findViewById(R.id.listViewSeen);
                lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {

                        Movie m =(Movie)lv2.getItemAtPosition(position);
                        Intent intent = new Intent(rootView2.getContext(), MovieInformationActivity.class);
                        intent.putExtra("movie", m);
                        startActivity(intent);

                    }
                });

                rg2 = (RadioGroup)rootView2.findViewById(R.id.radioGroupSeen);
                rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
                {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        // checkedId is the RadioButton selected
                        switch(checkedId) {
                            case R.id.yearRBSeen:
                                sorting(R.id.yearRBSeen);
                                   break;
                            case R.id.ratingRBSeen:
                                sorting(R.id.ratingRBSeen);
                                   break;
                            case R.id.alphabeticRBSeen:
                                sorting(R.id.alphabeticRBSeen);
                                    break;
                        }
                    }
                });

                return rootView2;
            case 3:

                final View rootView3 = inflater.inflate(R.layout.wishlist_layout, container, false);

                lv3 = (ListView)rootView3.findViewById(R.id.listViewWish);
                lv3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {

                        Movie m =(Movie)lv3.getItemAtPosition(position);
                        Intent intent = new Intent(rootView3.getContext(), MovieInformationActivity.class);
                        intent.putExtra("movie", m);
                        startActivity(intent);
                    }
                });
                rg3 = (RadioGroup)rootView3.findViewById(R.id.radioGroupWish);

                rg3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
                {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        // checkedId is the RadioButton selected
                        switch(checkedId) {
                            case R.id.yearRBWish:
                                sorting(R.id.yearRBWish);
                                break;
                            case R.id.ratingRBWish:
                                sorting(R.id.ratingRBWish);
                                break;
                            case R.id.alphabeticRBWish:
                                sorting(R.id.alphabeticRBWish);
                                break;
                        }
                    }
                });

                return rootView3;
            case 4:

                final View rootView4 = inflater.inflate(R.layout.research_layout, container, false);
                lv4 = (ListView)rootView4.findViewById(R.id.listViewSearch);
                rg4 = (RadioGroup)rootView4.findViewById(R.id.radioGroupSearch);
                Button searchButton = (Button) rootView4.findViewById(R.id.searchButton);
                searchButton.setOnClickListener(this);

                lv4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {

                        Movie m =(Movie)lv4.getItemAtPosition(position);
                        Intent intent = new Intent(rootView4.getContext(), MovieInformationActivity.class);
                        intent.putExtra("movie", m);
                        startActivity(intent);
                    }
                });
                rg4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
                {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        // checkedId is the RadioButton selected
                        SearchListViewAdapter sViewAdapter= (SearchListViewAdapter)lv4.getAdapter();
                        SearchListViewAdapter sva;
                        switch(checkedId) {
                            case R.id.yearRBSearch:
                                if(sViewAdapter != null) {
                                    Collections.sort(sViewAdapter.getMovieList(), new CustomYearComparator());
                                    sva = new SearchListViewAdapter(MainActivity.myContext, sViewAdapter.getMovieList());
                                    lv4.setAdapter(sva);
                                }
                                break;
                            case R.id.ratingRBSearch:
                                if(sViewAdapter != null) {
                                    Collections.sort(sViewAdapter.getMovieList(), new CustomRateComparator());
                                    sva = new SearchListViewAdapter(MainActivity.myContext, sViewAdapter.getMovieList());
                                    lv4.setAdapter(sva);
                                }
                                break;
                            case R.id.alphabeticRBSearch:
                                if(sViewAdapter != null) {
                                    Collections.sort(sViewAdapter.getMovieList(), new CustomAlphaComparator());
                                    sva = new SearchListViewAdapter(MainActivity.myContext, sViewAdapter.getMovieList());
                                    lv4.setAdapter(sva);
                                }
                                break;
                        }
                    }
                });

                //aller chercher le cursor contenant les films de la recherche
                //si vide laisse blank?
                dice_iButton = (ImageButton)rootView4.findViewById(R.id.randomButton);
                dice_iButton.setOnClickListener(this);

                return rootView4;
            default: return null;
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.searchButton){
            rg4 = (RadioGroup)getView().findViewById(R.id.radioGroupSearch);
            rg4.clearCheck();;
            rg4.check(R.id.alphabeticRBSearch);
            View rootView = (View) v.getRootView();
            TextView QueryTextView = (TextView)rootView.findViewById(R.id.requete);
            String queryStr = QueryTextView.getText().toString();
            TaskParamsRottenTomatoesApi downloadParams = new TaskParamsRottenTomatoesApi("Recherche", queryStr);
            new DownloadFromApi(rootView).execute(downloadParams);
        } else if(v.getId() == R.id.randomButton){
            View rootView = (View) v.getRootView();
            TextView QueryTextView = (TextView)rootView.findViewById(R.id.requete);
            String queryStr = QueryTextView.getText().toString();
            TaskParamsRottenTomatoesApi downloadParams;
            if(!queryStr.isEmpty()){
                downloadParams  = new TaskParamsRottenTomatoesApi("Recherche", queryStr);
            } else{
                downloadParams = new TaskParamsRottenTomatoesApi("RechercheRandom", null);
            }
            new DownloadFromApi(rootView).execute(downloadParams);
        }
    }
    @Override
     public void onResume() {

        super.onResume();

        refreshList();

    }


    public void refreshList() {
        lv2 = (ListView) getView().findViewById(R.id.listViewSeen);
        lv3 = (ListView) getView().findViewById(R.id.listViewWish);
        rg2 = (RadioGroup)getView().findViewById(R.id.radioGroupSeen);
        rg3 = (RadioGroup)getView().findViewById(R.id.radioGroupWish);

        if(lv2 != null && (lv2.getVisibility() == View.VISIBLE)) {
            textSeen = (TextView)getView().findViewById(R.id.textSeen);
            textSeen.setText("You can add movies to your list by checking the 'Seen' box on the movie description page.");

            int id =rg2.getCheckedRadioButtonId();
            if(id == R.id.alphabeticRBSeen)
                c= dbh.listeSeen(DBHelper.COL_TITRE);
            else if(id == R.id.yearRBSeen)
                c= dbh.listeSeen(DBHelper.COL_ANNEE);
            else
                c= dbh.listeSeen(DBHelper.COL_RATING);

            if(c.getCount() != 0) {
                textSeen.setVisibility(View.GONE);
            }
            else {
                textSeen.setVisibility(View.VISIBLE);
            }
            listViewAdapter = new ListViewAdapter(MainActivity.myContext, c);
            lv2.setAdapter(listViewAdapter);

        }


        if(lv3 != null && (lv3.getVisibility() == View.VISIBLE)){
            TextView textWish= (TextView)getView().findViewById(R.id.textWish);
            textWish.setText("You can add movies to your list by checking the 'Wishlist' box on the movie description page.");

            int id =rg3.getCheckedRadioButtonId();
            if(id == R.id.alphabeticRBWish)
                c= dbh.listeWishlist(DBHelper.COL_TITRE);
            else if(id == R.id.yearRBWish)
                c= dbh.listeWishlist(DBHelper.COL_ANNEE);
            else
                c= dbh.listeWishlist(DBHelper.COL_RATING);

            if(c.getCount() != 0) {
                textWish.setVisibility(View.GONE);
            }
            else{
                textWish.setVisibility(View.VISIBLE);
            }
            lva = new ListViewAdapter(MainActivity.myContext, c);
            lv3.setAdapter(lva);

        }
    }

    public void sorting(int id){
        switch(id){
            case R.id.alphabeticRBWish:
                Cursor cAW = dbh.listeWishlist(DBHelper.COL_TITRE);
                if(cAW.getCount() != 0) {
                    lva.changeCursor(cAW);
                    lv3.setAdapter(lva);
                }
                break;
            case R.id.ratingRBWish:
                Cursor cRW = dbh.listeWishlist(DBHelper.COL_RATING);
                if(cRW.getCount() != 0) {
                    lva.changeCursor(cRW);
                    lv3.setAdapter(lva);
                }
                break;
            case R.id.yearRBWish:
                Cursor cYW = dbh.listeWishlist(DBHelper.COL_ANNEE);
                if(cYW.getCount() != 0){
                    lva.changeCursor(cYW);
                    lv3.setAdapter(lva);
                }
                break;
            case R.id.yearRBSeen:
                Cursor cYS = dbh.listeSeen(DBHelper.COL_ANNEE);
                if(cYS.getCount() != 0) {
                    listViewAdapter.changeCursor(cYS);
                    lv2.setAdapter(listViewAdapter);
                }
                break;
            case R.id.ratingRBSeen:
                //query sorting par rating
                Cursor cRS = dbh.listeSeen(DBHelper.COL_RATING);
                if(cRS.getCount() != 0) {
                    listViewAdapter.changeCursor(cRS);
                    lv2.setAdapter(listViewAdapter);
                }
                break;
            case R.id.alphabeticRBSeen:
                //query sorting par title
                Cursor cAS = dbh.listeSeen(DBHelper.COL_TITRE);
                if(cAS.getCount() != 0) {
                    listViewAdapter.changeCursor(cAS);
                    lv2.setAdapter(listViewAdapter);
                }
                break;
        }

    }
    public class CustomYearComparator implements Comparator<Movie> {
        @Override
        public int compare(Movie o1, Movie o2) {
            return o2.getAnnee().compareTo(o1.getAnnee());
        }
    }

    public class CustomRateComparator implements Comparator<Movie> {
        @Override
        public int compare(Movie o1, Movie o2) {
            Integer r1=o1.getRating();
            Integer r2 =o2.getRating();
            return r2.compareTo(r1);
        }
    }
    public class CustomAlphaComparator implements Comparator<Movie> {
        @Override
        public int compare(Movie o1, Movie o2) {
            return o1.getTitre().compareTo(o2.getTitre());
        }
    }
}
