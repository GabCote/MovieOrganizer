package com.example.gab.movieorganizer;



import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Anna on 19/03/2015.
 */
public class SimplePagerFragment extends Fragment {
    SQLiteDatabase db;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Bundle args = getArguments();
        switch(args.getInt("id")+1){//ne pas commencer par zero (id +1)
            case 1:
                View rootView1 = inflater.inflate(R.layout.home_layout, container, false);
                new DownloadFromApi(rootView1).execute("Accueil");
                //TextView textViewUpcoming = (TextView) rootView1.findViewById(R.id.textViewUpcoming);
               // textViewUpcoming.setText("whats upp");
                //GRIDVIEW
                //GridView gridview = (GridView)rootView1.findViewById(R.id.gridView);
               // gridview.setAdapter(new GridViewAdapter(getActivity(), ));
                return rootView1;
            case 2:
                View rootView2 = inflater.inflate(R.layout.seen_layout, container, false);
                ListView lv2= (ListView)rootView2.findViewById(R.id.listViewSeen);
                //aller chercher le cursor contenant les films
                //si vide mettre le texte d'explication
                return rootView2;
            case 3:
                View rootView3 = inflater.inflate(R.layout.wishlist_layout, container, false);
                ListView lv3 = (ListView)rootView3.findViewById(R.id.listViewWish);
                //aller chercher le cursor contenant les films
                //si vide mettre le texte d'explication
                return rootView3;
            case 4:
                View rootView4 = inflater.inflate(R.layout.research_layout, container, false);
                ListView lv4 = (ListView)rootView4.findViewById(R.id.listViewSearch);
                //aller chercher le cursor contenant les films de la recherche
                //si vide laisse blank?
                return rootView4;
            default: return null;
        }

    }
}
