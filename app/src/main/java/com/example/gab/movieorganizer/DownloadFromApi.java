package com.example.gab.movieorganizer;

import android.os.AsyncTask;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Gab on 4/2/2015.
 */
public class DownloadFromApi extends AsyncTask<String, String, RottenTomatoesWebApi> {

    View currentView;
    public DownloadFromApi(){
        super();
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

    // Cette méthode est exécutée dans son propre thread. C'est là où le travail le plus lourd se passe.
    // On pourra appeler publishProgress durant l'exécution de cette méthode pour mettre à jour le thread d'interface.
    @Override
    protected RottenTomatoesWebApi doInBackground(String... params) {
        String currentPageName = params[0];
        RottenTomatoesWebApi web = new RottenTomatoesWebApi(currentPageName);
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
