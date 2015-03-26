package com.example.gab.movieorganizer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    ViewPager pager;
    SimplePagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

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
            new DownloadNouveautes().execute();
            Toast.makeText(getApplicationContext(), "On a clique sur les settings!!", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private class DownloadNouveautes extends AsyncTask<String, String, RottenTomatoesWebApi> {

         // Cette méthode s'exécute dans le thread de l'interface. C'est le bon endroit pour notifier l'usager
         // qu'une tâche plus longue commence (par exemple, afficher une barre de progression).
        @Override
        protected void onPreExecute() {
            // Affiche la barre de progression
            //pb.setVisibility(View.VISIBLE);
        }

         // Cette méthode est exécutée dans son propre thread. C'est là où le travail le plus lourd se passe.
         // On pourra appeler publishProgress durant l'exécution de cette méthode pour mettre à jour le thread d'interface.
        @Override
        protected RottenTomatoesWebApi doInBackground(String... params) {
            RottenTomatoesWebApi web = new RottenTomatoesWebApi("Accueil");
            return web;
        }

         // Cette méthode est appelée dans le thread d'interface lorsque publishProgress est appelée dans doInBackground.
         // Les paramètres sont passés directement de l'une à l'autre.
        @Override
        protected void onProgressUpdate(String... s) {
            // éxécute dans le thread interface, si le thread non-interface
            // appelle publishProgress à l'intérieur de doInBackground
        }

        // Cette méthode s'exécute dans le thread d'interface. C'est l'endroit où on réagit généralement à la complétion du
        // processus en arrière-plan, par exemple en mettant à jour l'interface avec les données obtenues.
        @Override
        protected void onPostExecute(RottenTomatoesWebApi web) {
            // Cache la barre de progression
            //pb.setVisibility(View.INVISIBLE);

            // On s'assure que l'objet de retour existe
            // et qu'il n'ait pas d'erreurs
            if( web == null ) {
                Toast.makeText(MainActivity.this, getText(R.string.fatal_error), Toast.LENGTH_SHORT).show();
                return;
            }

            if( web.erreur != null ) {
                Toast.makeText(MainActivity.this, web.erreur, Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(MainActivity.this, "Meteo a jour a partir de AsyncTask" , Toast.LENGTH_SHORT).show();
            // Si tout est OK, on met l'interface à jour
            //temperature.setText(web.temperature);
            //condition.setText(web.conditions);
            //city.setText(web.ville);
            //time.setText(web.depuis);
            //icone.setImageDrawable(web.icone);
        }

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
