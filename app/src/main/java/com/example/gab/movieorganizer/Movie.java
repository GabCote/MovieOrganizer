package com.example.gab.movieorganizer;

/**
 * Created by Émile on 2015-03-24.
 */
public class Movie {
    int id, annee;
    String title, synopsis;

    public Movie(int id, int annee, String title, String synopsis) {
        this.id = id;
        this.annee = annee;
        this.title = title;
        this.synopsis = synopsis;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }
}
