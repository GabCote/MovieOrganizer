package com.example.gab.movieorganizer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Anna on 27/03/2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    static final String DB_NAME = "MovieOrganizer.db";
    static final int DB_Version =1;

    //table names
    static final String TABLE_SEEN = "seen";
    static final String TABLE_WISH = "wish";

    // table Cols
    static final String COL_ID = "_id";
    static final String COL_TITRE = "titre";
    static final String COL_ANNEE = "annee";
    static final String COL_SYNOPSIS = "synopsis";
    static final String COL_IMAGE = "image";
    static final String COL_RATING = "rating";
    static final String COL_CAST ="cast";

    //Create table
    String CREATE_SEEN = "create table "+TABLE_SEEN
            +" ("+COL_ID+" integer primary key autoincrement, "
            + COL_TITRE+" text, "
            + COL_ANNEE+" integer, "
            + COL_SYNOPSIS+" text,"
            + COL_RATING+" text,"
            + COL_IMAGE+" text,"
            + COL_CAST+" text)";

    String CREATE_WISH = "create table "+TABLE_WISH
            +" ("+COL_ID+" integer primary key autoincrement, "
            + COL_TITRE+" text, "
            + COL_ANNEE+" integer, "
            + COL_SYNOPSIS+" text,"
            + COL_RATING+" double,"
            + COL_IMAGE+" text,"
            + COL_CAST+" text)";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_Version);
        // Android will look for the database defined by DB_NAME
        // And if not found will invoke your onCreate method

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_SEEN);
            db.execSQL(CREATE_WISH);


        }
        catch (Exception e) {
            e.printStackTrace();
        }

            Log.d("DB", "database created");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("drop table if exists " + TABLE_SEEN);
            db.execSQL("drop table if exists " + TABLE_WISH);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
            onCreate(db);

    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }



    /*Methode pour insérer un film dans la liste SEEN ou WISH*/

    public void insertMovie(Movie movie, String table_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        //verifier si le film est deja inserer
        String[] columns ={COL_TITRE,COL_ANNEE,COL_IMAGE};
        Cursor findEntry = db.query(table_name,columns,COL_TITRE+"=? and "+COL_ANNEE+"=? and "+COL_IMAGE+"=?",new String[] { movie.getTitre(),movie.getAnnee()+"",movie.getImgUrl()},null,null,null);
        if(findEntry.getCount() <= 0) {
            String syp =movie.getSynopsis();
            String syp_rep =syp.replace("'","''");

            try {
                db.execSQL("INSERT INTO " +
                        table_name + "(" + COL_TITRE + "," + COL_ANNEE + "," + COL_SYNOPSIS + "," + COL_RATING + "," + COL_IMAGE + ","+COL_CAST+ ")" +
                        " Values ('" + movie.getTitre() + "','" + movie.getAnnee() + "','" + syp_rep + "','" + movie.getRating() + "','" + movie.getImgUrl() + "','"+ movie.getCast()+"');");
                Toast.makeText(MainActivity.myContext, "INSERT done", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {

                e.printStackTrace();
            }
        }
        else
            Toast.makeText(MainActivity.myContext, "DEJA INSERER", Toast.LENGTH_SHORT).show();

    }

    /*Methode pour supprimer un film dans liste SEEN ou WISH*/
    public void deleteMovie(Movie movie,String table_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean exist = isMovieExist(table_name,movie);
        if(exist) {
            db.delete(table_name,COL_TITRE+"=? and "+COL_ANNEE+"=? and "+COL_IMAGE+"=?",new String[] { movie.getTitre(),movie.getAnnee()+"",movie.getImgUrl()});
        }
        else
            Toast.makeText(MainActivity.myContext, "MOVIE existe pas!", Toast.LENGTH_SHORT).show();
     }

    /*Check if movie exist in table*/
    public boolean isMovieExist(String table_name, Movie movie){
        SQLiteDatabase db = this.getWritableDatabase();

        String[] columns ={COL_ID,COL_TITRE,COL_ANNEE,COL_IMAGE};
        Cursor findEntry = db.query(table_name,columns,COL_TITRE+"=? and "+COL_ANNEE+"=? and "+COL_IMAGE+"=?",new String[] { movie.getTitre(),movie.getAnnee()+"",movie.getImgUrl()},null,null,null);
        if(findEntry.getCount() >0)
            return true;
        else
            return false;
    }

    /* --------------- MOVIE SEEN MÉTHODES ---------------------------- */



    /*Methode pour avoir la liste de tous les films SEEN*/
    public Cursor listeSeen(String sort){
        SQLiteDatabase db = this.getReadableDatabase();

        //comme une ArrayListe de type Database
        String sql = "select * from "+ TABLE_SEEN
                +" order by "+ sort+" asc";
        Cursor c=db.rawQuery(sql,null);

        return c;
    }

    /*Methode pour updater un Movie SEEN*/
    public int updateMovieSeen(Movie movie){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_ID, movie.getId());
        values.put(COL_TITRE, movie.getTitre());
        values.put(COL_ANNEE, movie.getAnnee());
        values.put(COL_SYNOPSIS, movie.getSynopsis());
        values.put(COL_RATING, movie.getRating());
        values.put(COL_IMAGE, movie.getImgUrl());
        values.put(COL_CAST, movie.getCast());

        // updating row
        return db.update(TABLE_SEEN, values, COL_ID + " = ?",
                new String[] { String.valueOf(movie.getId()) });
    }



    /* ---------------- MÉTHODE WISHLIST --------------------- */

    public Cursor listeWishlist(String sort){
        SQLiteDatabase db = this.getReadableDatabase();

        //comme une ArrayListe de type Database
        String sql = "select * from "+ TABLE_WISH
                +" order by "+ sort+" asc";
        Cursor c=db.rawQuery(sql,null);

        return c;
    }
}
