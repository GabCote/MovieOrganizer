package com.example.gab.movieorganizer;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    ViewPager pager;
    SimplePagerAdapter adapter;
    DBHelper dbh;
    static Context myContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        myContext = getApplicationContext();
        //Instantiate the helper
        dbh = new DBHelper(myContext);
        //Test d'insert
        Movie m = new Movie(0,"Harry Potter",1996,"Un magicien va à Poudlard, lécole des sorciers",93,"");
        dbh.insertMovie(m,DBHelper.TABLE_SEEN);
        m = new Movie(0,"Frozen",2014,"Histoire damour entre deux soeurs",98,"");
        dbh.insertMovie(m,DBHelper.TABLE_SEEN);
        m = new Movie(0,"James Bond",2015,"Daniel Craig is hot",83,"");
        dbh.insertMovie(m,DBHelper.TABLE_WISH);
        Log.d("insert","all added");

        dbh.closeDB();

        //View pager
        pager=(ViewPager)findViewById(R.id.pager);
        adapter= new SimplePagerAdapter(getSupportFragmentManager()); //support provient du import android.support sinon aucun support
        pager.setAdapter(adapter);
        //POUR LE ACTION BAR
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            //événements sur les tabs
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                pager.setCurrentItem(tab.getPosition());    //prend la page de mon tab
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {}

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {}
        };

        //creer les tables
        //getcount retoune le nb de pages et donc creer le nombre de tabs avec for
        for(int i=0; i< adapter.getCount();i++){
            //actionBar.addTab(actionBar.newTab().setText(adapter.getPageTitle(i)).setTabListener(tabListener));
            ActionBar.Tab tab = actionBar.newTab();
            tab.setText(adapter.getPageTitle(i));
            tab.setTabListener(tabListener);
            actionBar.addTab(tab);
        }

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {}

            @Override
            public void onPageSelected(int i) {
                //mettre a jour le tab selectionne (highlighter)
                getSupportActionBar().setSelectedNavigationItem(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) { }
        });




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will automatically handle clicks on the Home/Up button,
        // so long as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            new DownloadFromApi().execute();
            Toast.makeText(getApplicationContext(), "On a clique sur les settings!!", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class SimplePagerAdapter extends FragmentPagerAdapter{
        //pour aller chercher la page voulue

        public SimplePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            //creer un fragment
            SimplePagerFragment f = new SimplePagerFragment();
            Bundle args = new Bundle();
            args.putInt("id", i);
            f.setArguments(args); //mis des arguments
            return f;
        }

        @Override
        public int getCount() { //retourne le nb de pages
            return 4;
        }

        public CharSequence getPageTitle(int i){
            switch (i){
                case 0: return getString(R.string.home);
                case 1: return getString(R.string.seen);
                case 2: return getString(R.string.wishList);
                case 3: return getString(R.string.search);
                default: return "Error";
            }
        }
    };



}
