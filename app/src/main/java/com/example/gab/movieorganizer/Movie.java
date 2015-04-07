package com.example.gab.movieorganizer;

/**
 * Created by Émile on 2015-03-24.
 */
public class Movie {
    int id, annee;
    double rating;
    String titre, synopsis, imgUrl;

    public Movie(int id, String titre,int annee, String synopsis, double rating, String imgUrl) {
        this.id = id;
        this.titre = titre;
        this.annee = annee;
        this.synopsis = synopsis;
        this.imgUrl = imgUrl;
        this.rating = rating;

    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", annee=" + annee +
                ", titre='" + titre + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", rating='" + rating + '\'' +
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
