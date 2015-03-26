package com.example.gab.movieorganizer;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Anna on 19/03/2015.
 */
public class SimplePagerFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Bundle args = getArguments();

        switch(args.getInt("id")+1){//ne pas commencer par zero (id +1)
            case 1: //View rootView1 = inflater.inflate(R.layout.simple_fragment, container, false);
                //((TextView)rootView1.findViewById(R.id.textView2)).setText("HOME");
                View rootView1 = inflater.inflate(R.layout.home_layout, container, false);
                return rootView1;
            case 2: //View rootView2 = inflater.inflate(R.layout.simple_fragment, container, false);
               // ((TextView)rootView2.findViewById(R.id.textView2)).setText("SEEN");
                View rootView2 = inflater.inflate(R.layout.seen_layout, container, false);
                return rootView2;
            case 3://View rootView3 = inflater.inflate(R.layout.simple_fragment, container, false);
                //((TextView)rootView3.findViewById(R.id.textView2)).setText("WISHLIST");
                View rootView3 = inflater.inflate(R.layout.wishlist_layout, container, false);
                return rootView3;
            case 4://View rootView4 = inflater.inflate(R.layout.simple_fragment, container, false);
                //((TextView)rootView4.findViewById(R.id.textView2)).setText("SEARCH");
                View rootView4 = inflater.inflate(R.layout.research_layout, container, false);
                return rootView4;
            default: return null;
        }

    }
}
