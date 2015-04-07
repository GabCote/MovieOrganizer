package com.example.gab.movieorganizer;

/**
 * Created by Gab on 4/6/2015.
 */
public class TaskParamsRottenTomatoesApi {
    String pageName;
    String queryStr;

    TaskParamsRottenTomatoesApi(String pPageName, String pQueryStr) {
        this.pageName = pPageName;
        this.queryStr = pQueryStr;
    }
}