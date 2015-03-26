package com.example.gab.movieorganizer;

/**
 * Created by Émile on 2015-03-24.
 */
public class Movie {
    int id, annee;
    String titre, synopsis;

    public Movie(int id, String titre,int annee, String synopsis) {
        this.id = id;
        this.titre = titre;
        this.annee = annee;
        this.synopsis = synopsis;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", annee=" + annee +
                ", titre='" + titre + '\'' +
                ", synopsis='" + synopsis + '\'' +
                '}';
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

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }
}
