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

    // table Films vus
    static final String TABLE_SEEN = "seen";
    static final String S_ID = "_id";
    static final String S_TITRE = "titre_film";

    // table Films a voir
    static final String TABLE_WISHLIST = "wishlist";
    static final String W_ID = "_id";
    static final String W_TITRE = "titre_film";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table "+TABLE_SEEN +" ("+S_ID+" primary key,"+S_TITRE+")";
        db.execSQL(sql);
        Log.d("DB", "database created");


        ContentValues values = new ContentValues();
        values.put(S_TITRE, "Hello World");

        db = this.getWritableDatabase();

        db.insert(TABLE_SEEN, null, values);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+TABLE_SEEN);
        onCreate(db);
    }

    public Cursor listeSeen(SQLiteDatabase db){
        //comme une ArrayListe de type Database
        String sql = "select * from "+ TABLE_SEEN
                +" order by "+ S_TITRE+" asc";
        Cursor c=db.rawQuery(sql,null);

        return c;
    }

    public Cursor listeWishlist(SQLiteDatabase db){
        //comme une ArrayListe de type Database
        String sql = "select * from "+ TABLE_WISHLIST
                +" order by "+ W_TITRE+" asc";
        Cursor c=db.rawQuery(sql,null);

        return c;
    }
}
