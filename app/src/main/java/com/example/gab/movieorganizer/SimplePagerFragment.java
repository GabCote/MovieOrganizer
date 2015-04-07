package com.example.gab.movieorganizer;



import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import junit.framework.Assert;

import java.util.ArrayList;

/**
 * Created by Anna on 19/03/2015.
 */
public class SimplePagerFragment extends Fragment implements View.OnClickListener{
    DBHelper dbh = new DBHelper(MainActivity.myContext);
    View rootView2;
    ListView lv2, lv3;
    TextView textSeen;
    Cursor c;
    ListViewAdapter listViewAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Bundle args = getArguments();
        switch(args.getInt("id")+1){//ne pas commencer par zero (id +1)
            case 1:
                View rootView1 = inflater.inflate(R.layout.home_layout, container, false);
                TaskParamsRottenTomatoesApi paramsDownload = new TaskParamsRottenTomatoesApi("Accueil", null);
                new DownloadFromApi(rootView1).execute(paramsDownload);
                //TextView textViewUpcoming = (TextView) rootView1.findViewById(R.id.textViewUpcoming);
               // textViewUpcoming.setText("whats upp");
                //GRIDVIEW
                //GridView gridview = (GridView)rootView1.findViewById(R.id.gridView);
               // gridview.setAdapter(new GridViewAdapter(getActivity(), ));
                return rootView1;
            case 2:
                rootView2 = inflater.inflate(R.layout.seen_layout, container, false);
                lv2= (ListView)rootView2.findViewById(R.id.listViewSeen);

                textSeen = (TextView)rootView2.findViewById(R.id.textSeen);
                textSeen.setText("You can add movies to your list by checking the 'Seen' box on the movie description page.");

                c= dbh.listeSeen(DBHelper.COL_TITRE);
                if(c.getCount() != 0) {
                    textSeen.setVisibility(View.GONE);

                    listViewAdapter = new ListViewAdapter(MainActivity.myContext, c);
                    lv2.setAdapter(listViewAdapter);
                }
                else {
                    textSeen.setVisibility(View.VISIBLE);
                }

                RadioGroup rg = (RadioGroup)rootView2.findViewById(R.id.radioGroupSeen);
                rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
                {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        // checkedId is the RadioButton selected
                        switch(checkedId) {
                            case R.id.yearRBSeen:
                                    Cursor cYS = dbh.listeSeen(DBHelper.COL_ANNEE);
                                    listViewAdapter.changeCursor(cYS);
                                    lv2.setAdapter(listViewAdapter);
                                    break;
                            case R.id.ratingRBSeen:
                                   //query sorting par rating
                                    Cursor cRS = dbh.listeSeen(DBHelper.COL_RATING);
                                    listViewAdapter.changeCursor(cRS);
                                    lv2.setAdapter(listViewAdapter);
                                    break;
                            case R.id.alphabeticRBSeen:
                                    //query sorting par title
                                    Cursor cAS = dbh.listeSeen(DBHelper.COL_TITRE);
                                    listViewAdapter.changeCursor(cAS);
                                    lv2.setAdapter(listViewAdapter);
                                    break;
                        }
                    }
                });

                return rootView2;
            case 3:

                View rootView3 = inflater.inflate(R.layout.wishlist_layout, container, false);
                lv3 = (ListView)rootView3.findViewById(R.id.listViewWish);

                TextView textWish= (TextView)rootView3.findViewById(R.id.textWish);
                textWish.setText("You can add movies to your list by checking the 'Wishlist' box on the movie description page.");
                RadioGroup rg2 = (RadioGroup)rootView3.findViewById(R.id.radioGroupWish);

                Cursor cursor = dbh.listeWishlist(DBHelper.COL_TITRE);
                if(cursor.getCount() != 0) {
                    textWish.setVisibility(View.GONE);

                    ListViewAdapter lva = new ListViewAdapter(MainActivity.myContext, cursor);
                    lv3.setAdapter(lva);
                }
                else{
                    textWish.setVisibility(View.VISIBLE);
                }
                rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
                {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        // checkedId is the RadioButton selected
                        switch(checkedId) {
                            case R.id.yearRBSeen:
                                Cursor cYS = dbh.listeSeen(DBHelper.COL_ANNEE);
                                listViewAdapter.changeCursor(cYS);
                                lv3.setAdapter(listViewAdapter);
                                break;
                            case R.id.ratingRBSeen:
                                //query sorting par rating
                                Cursor cRS = dbh.listeSeen(DBHelper.COL_RATING);
                                listViewAdapter.changeCursor(cRS);
                                lv3.setAdapter(listViewAdapter);
                                break;
                            case R.id.alphabeticRBSeen:
                                //query sorting par title
                                Cursor cAS = dbh.listeSeen(DBHelper.COL_TITRE);
                                listViewAdapter.changeCursor(cAS);
                                lv3.setAdapter(listViewAdapter);
                                break;
                        }
                    }
                });

                return rootView3;
            case 4:

                View rootView4 = inflater.inflate(R.layout.research_layout, container, false);
                ListView lv4 = (ListView)rootView4.findViewById(R.id.listViewSearch);
                Button searchButton = (Button) rootView4.findViewById(R.id.searchButton);
                searchButton.setOnClickListener(this);
                //aller chercher le cursor contenant les films de la recherche
                //si vide laisse blank?
                return rootView4;
            default: return null;
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.searchButton){

            View rootView = (View) v.getRootView();
            TextView QueryTextView = (TextView)rootView.findViewById(R.id.requete);
            String queryStr = QueryTextView.getText().toString();
            TaskParamsRottenTomatoesApi downloadParams = new TaskParamsRottenTomatoesApi("Recherche", queryStr);
            new DownloadFromApi(rootView).execute(downloadParams);
        }
    }
}
