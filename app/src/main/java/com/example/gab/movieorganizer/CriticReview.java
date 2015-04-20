package com.example.gab.movieorganizer;

import java.util.Date;

/**
 * Created by Gab on 4/17/2015.
 */
public class CriticReview {
    String critic;
    Date reviewDate;
    String score;
    String quote;
    String reviewLink;

    public CriticReview(String critic, Date reviewDate, String score, String quote, String reviewLink) {
        this.critic = critic;
        this.reviewDate = reviewDate;
        this.score = score;
        this.quote = quote;
        this.reviewLink = reviewLink;
    }

    public String getCritic() {
        return critic;
    }

    public void setCritic(String critic) {
        this.critic = critic;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getReviewLink() {
        return reviewLink;
    }

    public void setReviewLink(String reviewLink) {
        this.reviewLink = reviewLink;
    }
}
