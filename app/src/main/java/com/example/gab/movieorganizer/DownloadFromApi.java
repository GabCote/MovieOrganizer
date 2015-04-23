package com.example.gab.movieorganizer;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SlidingDrawer;
import android.widget.TextView;

/**
 * Created by Gab on 4/2/2015.
 */
public class DownloadFromApi extends AsyncTask<TaskParamsRottenTomatoesApi, String, RottenTomatoesWebApi> {

    View currentView;
    public DownloadFromApi(){
        super();
    }

    @Override
    protected RottenTomatoesWebApi doInBackground(TaskParamsRottenTomatoesApi... params) {
        String currentPageName = params[0].pageName;
        String queryStr = params[0].queryStr;
        RottenTomatoesWebApi web = new RottenTomatoesWebApi(currentPageName, queryStr);
        return web;
    }

    public DownloadFromApi(View pCurrentView){
        super();
        this.currentView = pCurrentView;
    }
    // Cette méthode s'exécute dans le thread de l'interface. C'est le bon endroit pour notifier l'usager
    // qu'une tâche plus longue commence (par exemple, afficher une barre de progression).
    @Override
    protected void onPreExecute() {
        // Affiche la barre de progression
        //pb.setVisibility(View.VISIBLE);
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
        if (web == null) {
           // Toast.makeText(MainActivity.this, getText(R.string.fatal_error), Toast.LENGTH_SHORT).show();
            return;
        }
        if (web.erreur != null) {
           // Toast.makeText(MainActivity.this, web.erreur, Toast.LENGTH_SHORT).show();
            return;
        }
        switch(web.getCurrentPage()) {
            case "Accueil":
               GridView gridview = (GridView)currentView.findViewById(R.id.gridView);
               GridViewAdapter gridViewAdapter = new GridViewAdapter(currentView.getContext(), web.getMovies());
               gridview.setAdapter(gridViewAdapter);
               // Toast.makeText(MainActivity.this, "On reussi a obtenir un webapi non null et sans erreur", Toast.LENGTH_SHORT).show();
                break;
            case "Recherche":
                TextView nombre_res = (TextView)currentView.findViewById(R.id.nbr_res);
                if(web.getMovies().isEmpty()){
                    nombre_res.setText("No result");
                } else if(web.getMovies().size()==1){
                    nombre_res.setText("1 result");
                } else{
                    nombre_res.setText(String.valueOf(web.getMovies().size())+" results");
                }
                ListView listViewSearch = (ListView)currentView.findViewById(R.id.listViewSearch);
                SearchListViewAdapter searchListViewAdapter = new SearchListViewAdapter(currentView.getContext(), web.getMovies());
                listViewSearch.setAdapter(searchListViewAdapter);
                break;
            case "Reviews":
                ListView listViewReviews = (ListView)currentView.findViewById(R.id.listViewReviews);
                ReviewsListViewAdapter reviewsListViewAdapter = new ReviewsListViewAdapter(currentView.getContext(), web.getCriticReviews());
                listViewReviews.setAdapter(reviewsListViewAdapter);
                break;
            default:
                break;
        }
        // Si tout est OK, on met l'interface à jour
        //temperature.setText(web.temperature);
        //condition.setText(web.conditions);
        //city.setText(web.ville);
        //time.setText(web.depuis);
        //icone.setImageDrawable(web.icone);
    }

}
