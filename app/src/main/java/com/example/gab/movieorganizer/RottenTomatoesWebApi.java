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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Émile on 2015-03-24.
 */
public class RottenTomatoesWebApi {

    final String  API_KEY = "xbut6w49ap4qtjh22kz2fg7q";
    // Sera null s'il n'y a pas d'erreur
    String erreur = null;

    int total=12345;
    int page_limit = 16;

    String country = "ca";
    String url;
    String currentPage = "";
    String cast;
    ArrayList<Movie> movies;
    ArrayList<CriticReview> criticReviews;
    String URLEncodedQueryStr;

    RottenTomatoesWebApi(String pCurrentPAge, String pQueryStr) {
        currentPage = pCurrentPAge;
        url = getApiUrl(pCurrentPAge, pQueryStr);
        JSONArray moviesjson;

        Log.d("URL", "Affichage de l'url:"+url);
        try {
            // Charge le fichier JSON à l'URL donné depuis le web
            HttpEntity page = getHttp(url);
            // Interprète la page retournée comme un fichier JSON encodé en UTF-8
            String contenu = EntityUtils.toString(page, HTTP.UTF_8);
            JSONObject js = new JSONObject(contenu);

            //pas encore sur si on garde le switch, je pense qu'on va aller chercher la meme info des films
            //peu importe la page, on verra.
            switch(pCurrentPAge){
                case "Accueil":
                case "RechercheRandom":
                    // Le format de ce JSON stocke les informations actuelles dans un sous-objet "movies"
                    moviesjson = js.getJSONArray("movies");

                    total = moviesjson.length();
                    movies = new ArrayList<Movie>();

                    for(int i=0; i<total; i++){
                        Log.d("json", moviesjson.getJSONObject(i)+"");
                        Integer annee;
                        int ratingScore;
                        String rating;
                        JSONObject movie = moviesjson.getJSONObject(i);
                        int id = movie.getInt("id");
                        String titre = movie.getString("title");

                        String anneeStr = movie.getString("year");
                        if (Utilities.isStringInt(anneeStr)){
                            annee = movie.getInt("year");
                        } else{
                            annee = null;
                        }
                        String synopsis = movie.getString("synopsis");

                        JSONObject rate = movie.getJSONObject("ratings");
                        Log.d("rate",rate+"");
                        String ratingStr = rate.getString("audience_score");
                        if (Utilities.isStringInt(ratingStr)){
                            ratingScore = rate.getInt("audience_score");
                        } else{
                            ratingScore = 0;
                        }

                        JSONObject posters = movie.getJSONObject("posters");
                        String thumbnail = posters.getString("thumbnail");
                        JSONArray castArr = movie.getJSONArray("abridged_cast");
                        cast="";
                        for(int j=0; j<castArr.length();j++){
                            JSONObject temp = castArr.getJSONObject(j);
                            cast = cast + ", " + temp.getString("name");
                        }
                        if(!cast.contentEquals("")){
                            cast = cast.substring(1);
                        }
                        JSONObject links = movie.getJSONObject("links");
                        String reviewLink = null;
                        if(!links.getString("reviews").isEmpty())
                            reviewLink = links.getString("reviews") + "?apikey=" + API_KEY;
                        Movie movie1 = new Movie(id,titre,annee, synopsis, ratingScore,0.0f, thumbnail, cast, reviewLink);
                        movies.add(movie1);
                        cast="";

                        Log.d("FILMS2","Affichage des films :"+movie1.toString());
                    }
                    break;
                case "Recherche":
                    // Le format de ce JSON stocke les informations actuelles dans un sous-objet "movies"
                    moviesjson = js.getJSONArray("movies");

                    total = moviesjson.length();
                    movies = new ArrayList<Movie>();

                    for(int i=0; i<total; i++){
                        Log.d("json", moviesjson.getJSONObject(i)+"");
                        Integer annee;
                        int ratingScore;
                        String rating;
                        JSONObject movie = moviesjson.getJSONObject(i);
                        int id = movie.getInt("id");
                        String titre = movie.getString("title");

                        String anneeStr = movie.getString("year");
                        if (Utilities.isStringInt(anneeStr)){
                            annee = movie.getInt("year");
                        } else{
                            annee = null;
                        }
                        String synopsis = movie.getString("synopsis");

                        JSONObject rate = movie.getJSONObject("ratings");
                        Log.d("rate",rate+"");
                        String ratingStr = rate.getString("audience_score");
                        if (Utilities.isStringInt(ratingStr)){
                            ratingScore = rate.getInt("audience_score");
                        } else{
                            ratingScore = 0;
                        }

                        JSONObject posters = movie.getJSONObject("posters");
                        String thumbnail = posters.getString("thumbnail");
                        JSONArray castArr = movie.getJSONArray("abridged_cast");
                        cast="";
                        for(int j=0; j<castArr.length();j++){
                            JSONObject temp = castArr.getJSONObject(j);
                            cast = cast + ", " + temp.getString("name");
                        }
                        if(!cast.contentEquals("")){
                            cast = cast.substring(1);
                        }
                        JSONObject links = movie.getJSONObject("links");
                        String reviewLink = null;
                        if(!links.getString("reviews").isEmpty())
                            reviewLink = links.getString("reviews") + "?apikey=" + API_KEY;
                        Movie movie1 = new Movie(id,titre,annee, synopsis, ratingScore,0.0f, thumbnail, cast, reviewLink);
                        movies.add(movie1);
                        cast="";

                        Log.d("FILMS2","Affichage des films :"+movie1.toString());
                    }
                    break;
                case "Reviews":
                    JSONArray reviewsjson = js.getJSONArray("reviews");
                    criticReviews = new ArrayList<CriticReview>();
                            HttpEntity pageReviews = getHttp(url);
                            for(int i=0; i<reviewsjson.length(); i++){
                                JSONObject review = reviewsjson.getJSONObject(i);
                                String critic = review.getString("critic");
                                Date reviewDate = Calendar.getInstance().getTime();
                                String score = null;
                                try {
                                    score = review.getString("original_score");
                                }catch(JSONException e){
                                    score = null;
                                }
                                String quote = review.getString("quote");
                                JSONObject linksReviews = review.getJSONObject("links");
                                String reviewLink = linksReviews.getString("review");
                                CriticReview criticReview = new CriticReview(critic, reviewDate, score, quote, reviewLink);
                                criticReviews.add(criticReview);
                            }
                    break;
            }

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

    public String getApiUrl(String pCurrentPage, String pQueryStr){
        switch (pCurrentPage){
            case "Accueil":
            case "RechercheRandom": return "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/upcoming.json?apikey="+API_KEY+"&page_limit="+Integer.toString(page_limit)+"&country="+country;
                //ce sera ça: http://api.rottentomatoes.com/api/public/v1.0/lists/movies/upcoming.json?apikey=xbut6w49ap4qtjh22kz2fg7q&page_limit=16&country=ca
                //c'est l'url pour prendre les Upcoming Movies

                //http://developer.rottentomatoes.com/docs/read/json/v10/Upcoming_Movies
                //pour en savoir plus sur les Upcoming Movies avec l'API

            case "Recherche":try {
                URLEncodedQueryStr = URLEncoder.encode(pQueryStr, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
                return "http://api.rottentomatoes.com/api/public/v1.0/movies.json?apikey="+API_KEY+"&q="+ URLEncodedQueryStr + "&page_limit=16";

            case "Reviews": return pQueryStr;

            default: return null;

        }
    }

    public String getCurrentPage(){
        return currentPage;
    }




    public ArrayList<Movie> getMovies(){
        return movies;
    }

    public ArrayList<CriticReview> getCriticReviews() {
        return criticReviews;
    }

}
