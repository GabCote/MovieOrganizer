package com.example.gab.movieorganizer;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Émile on 2015-03-24.
 */
public class Movie implements Parcelable {
    int id;
    Integer annee;
    int rating;
    String titre;
    String synopsis;
    String imgUrl;
    String cast;
    String reviewLink;


    public Movie(int id, String titre,Integer annee, String synopsis, int rating, String imgUrl, String cast, String reviewLink) {
        this.id = id;
        this.titre = titre;
        this.annee = annee;
        this.synopsis = synopsis;
        this.imgUrl = imgUrl;
        this.rating = rating;
        this.cast = cast;
        this.reviewLink = reviewLink;

    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", annee=" + annee +
                ", rating=" + rating +
                ", titre='" + titre + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", cast=" + cast +
                ", reviewLink=" + reviewLink +
                '}';
    }
    public String getReviewLink() {
        return reviewLink;
    }
    public void setReviewLink(String reviewLink) {
        this.reviewLink = reviewLink;
    }
    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getAnnee() {
        return annee;
    }

    public void setAnnee(Integer annee) {
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.titre);
        dest.writeInt(this.annee);
        dest.writeInt(this.rating);
        dest.writeString(this.synopsis);
        dest.writeString(this.imgUrl);
        dest.writeString(this.cast);
        dest.writeString(this.reviewLink);

    }
    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Movie(Parcel in) {
        int id = in.readInt();
        String titre = in.readString();
        Integer annee = in.readInt();
        int rating = in.readInt();
        String synopsis = in.readString();
        String imgUrl = in.readString();
        String cast = in.readString();
        String reviewLink = in.readString();
        this.id = id;
        this.titre = titre;
        this.annee = annee;
        this.rating = rating;
        this.synopsis = synopsis;
        this.imgUrl = imgUrl;
        this.cast = cast;
        this.reviewLink = reviewLink;
    }
}
