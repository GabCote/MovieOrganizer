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

    //Create table
    String CREATE_SEEN = "create table "+TABLE_SEEN
            +" ("+COL_ID+" integer primary key autoincrement, "
            + COL_TITRE+" text, "
            + COL_ANNEE+" integer, "
            + COL_SYNOPSIS+" text,"
            + COL_RATING+" text,"
            + COL_IMAGE+" text)";

    String CREATE_WISH = "create table "+TABLE_WISH
            +" ("+COL_ID+" integer primary key autoincrement, "
            + COL_TITRE+" text, "
            + COL_ANNEE+" integer, "
            + COL_SYNOPSIS+" text,"
            + COL_RATING+" double,"
            + COL_IMAGE+" text)";

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

        try {
        db.execSQL("INSERT INTO " +
                table_name +"("+COL_TITRE+","+COL_ANNEE+","+COL_SYNOPSIS+","+COL_RATING+","+COL_IMAGE+")"+
                " Values ('"+ movie.getTitre() + "','" +movie.getAnnee() + "','" +movie.getSynopsis()+ "','" +movie.getRating() +"','" + movie.getImgUrl()+ "');");

        }catch (Exception e){

            e.printStackTrace();
        }

    }

    /*Methode pour supprimer un film dans liste SEEN ou WISH*/
    public void deleteMovieSeen(long movieID,String table_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table_name, COL_ID + " = ?",
                new String[] { String.valueOf(movieID) });
    }

    /* --------------- MOVIE SEEN MÉTHODES ---------------------------- */

    /*Methode pour chercher un film dans les SEEN */
    public Movie getMovieSeen(long movieID){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_SEEN + " WHERE "
                + COL_ID + " = " + movieID;

        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null)
            c.moveToFirst();

        Movie m = new Movie(c.getInt(c.getColumnIndex(COL_ID)),c.getString(c.getColumnIndex(COL_TITRE)),c.getInt(c.getColumnIndex(COL_ANNEE)),c.getString(c.getColumnIndex(COL_SYNOPSIS)),c.getInt(c.getColumnIndex(COL_RATING)),c.getString(c.getColumnIndex(COL_IMAGE)));

        return m;
    }

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
