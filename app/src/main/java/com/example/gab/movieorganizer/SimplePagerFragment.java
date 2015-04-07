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
import android.widget.TextView;
import android.widget.Toast;

import junit.framework.Assert;

import java.util.ArrayList;

/**
 * Created by Anna on 19/03/2015.
 */
public class SimplePagerFragment extends Fragment implements View.OnClickListener{
    DBHelper dbh = new DBHelper(MainActivity.myContext);

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
                View rootView2 = inflater.inflate(R.layout.seen_layout, container, false);
                ListView lv2= (ListView)rootView2.findViewById(R.id.listViewSeen);

                Cursor c = dbh.listeSeen();
                if(c != null) Log.d("seen", "cursor is not null");
                ListViewAdapter listViewAdapter = new ListViewAdapter(MainActivity.myContext,c);
                lv2.setAdapter(listViewAdapter);
                //si vide mettre le texte d'explication
                return rootView2;
            case 3:
                View rootView3 = inflater.inflate(R.layout.wishlist_layout, container, false);
                ListView lv3 = (ListView)rootView3.findViewById(R.id.listViewWish);

                Cursor cursor = dbh.listeWishlist();
                if(cursor != null) Log.d("wish", "cursor is not null");
                ListViewAdapter lva = new ListViewAdapter(MainActivity.myContext,cursor);
                lv3.setAdapter(lva);

                //si vide mettre le texte d'explication
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
