package com.example.gab.movieorganizer;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Émile on 2015-03-24.
 */
public class RottenTomatoesWebApi {

    // Sera null s'il n'y a pas d'erreur
    String erreur = null;

    int total=12345;
    int page_limit = 16;
    String apiKey = "dmazmwz6h6hymzv5sbyws8cw";
    String country = "ca";
    String url;

    RottenTomatoesWebApi(String param) {
        switch (param){
            case "Accueil": url="http://api.rottentomatoes.com/api/public/v1.0/lists/movies/upcoming.json?apikey="+apiKey+"&page_limit="+Integer.toString(page_limit)+"&country="+country;
                 //ce sera ça: http://api.rottentomatoes.com/api/public/v1.0/lists/movies/upcoming.json?apikey=dmazmwz6h6hymzv5sbyws8cw&page_limit=16&country=ca
                 //c'est l'url pour prendre les Upcoming Movies

                //http://developer.rottentomatoes.com/docs/read/json/v10/Upcoming_Movies
                //pour en savoir plus sur les Upcoming Movies avec l'API
                break;
            case "Recherche":url=null;
                break;
            case "Autre chose blablabla": url="http........";
                break;

        }

        Log.d("URL", "Affichage de l'url:"+url);
        try {
            // Charge le fichier JSON à l'URL donné depuis le web
            HttpEntity page = getHttp(url);
            // Interprète la page retournée comme un fichier JSON encodé en UTF-8
            JSONObject js = new JSONObject(EntityUtils.toString(page, HTTP.UTF_8));

            switch(param){
                case "Accueil":

                    // Le format de ce JSON stocke les informations actuelles dans un sous-objet "movies"
                    JSONArray moviesjson = js.getJSONArray("movies");
                    total = moviesjson.length();
                    Movie tableauFilm[] = new Movie[total];

                    for(int i=0; i<total; i++){
                        JSONObject movie = moviesjson.getJSONObject(i);
                        int id = movie.getInt("id");
                        String titre = movie.getString("title");
                        int annee = movie.getInt("year");
                        String synopsis = movie.getString("synopsis");
                        Movie movie1 = new Movie(id,titre,annee, synopsis);
                        tableauFilm[i] = movie1;

                        Log.d("FILMS1","Affichage des films :"+movie1.toString());
                    }
                    break;
                case "Recherche":
                    break;


            }


            Log.d("TOTAL1","Affichage du total1:"+Integer.toString(total));

            //continuer a prendre l'information du fichier jason
            //entre autre, mettre tous les films dans un tableau de film

        } catch (ClientProtocolException e) {
            erreur = "Erreur HTTP (protocole) :"+e.getMessage();
        } catch (IOException e) {
            erreur = "Erreur HTTP (IO) :"+e.getMessage();
        } catch (ParseException e) {
            erreur = "Erreur JSON (parse) :"+e.getMessage();
        } catch (JSONException e) {
            erreur = "Erreur JSON :"+e.getMessage();
        }
        Log.d("TOTAL2","Affichage du total2:"+Integer.toString(total));
        Log.d("Err", "Affichage de l'erreur:"+erreur);
    }



    /*
	 * Méthode utilitaire qui permet de rapidement
	 * charger et obtenir une page web depuis
	 * l'internet.
	 *
	 */
    private HttpEntity getHttp(String url) throws ClientProtocolException, IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet http = new HttpGet(url);
        HttpResponse response = httpClient.execute(http);
        return response.getEntity();
    }

    /*
     * Méthode utilitaire qui permet
     * d'obtenir une image depuis une URL.
     *

    private Drawable loadHttpImage(String url) throws ClientProtocolException, IOException {
        InputStream is = getHttp(url).getContent();
        Drawable d = Drawable.createFromStream(is, "src");
        return d;
    }*/

}
