package com.example.gab.movieorganizer;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Anna on 30/03/2015.
 */
public class ListViewAdapter extends CursorAdapter {
    LayoutInflater inflater;

    public ListViewAdapter(Context context, Cursor c) {
        super(context, c, true);
        inflater = LayoutInflater.from(context);

    }


    @Override
    public int getCount() {
        return getCursor().getCount();
    }

    @Override
    public Movie getItem(int position) {
        Movie m = new Movie(getCursor().getInt(getCursor().getColumnIndex(DBHelper.COL_ID)),getCursor().getString(getCursor().getColumnIndex(DBHelper.COL_TITRE)),getCursor().getInt(getCursor().getColumnIndex(DBHelper.COL_ANNEE)),getCursor().getString(getCursor().getColumnIndex(DBHelper.COL_SYNOPSIS)),getCursor().getInt(getCursor().getColumnIndex(DBHelper.COL_RATING)),getCursor().getFloat(getCursor().getColumnIndex(DBHelper.COL_MYRATING)),getCursor().getString(getCursor().getColumnIndex(DBHelper.COL_IMAGE)),getCursor().getString(getCursor().getColumnIndex(DBHelper.COL_CAST)),getCursor().getString(getCursor().getColumnIndex(DBHelper.COL_REVIEW_LINK)));
        return m;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;


        if(v==null){
            v = inflater.inflate(R.layout.view_list_item, parent, false);
        }

        Cursor c = getCursor();
        c.moveToPosition(position);

        TextView titre = (TextView)v.findViewById(R.id.textTitle);
        TextView annee = (TextView)v.findViewById(R.id.textYear);
        TextView rating = (TextView)v.findViewById(R.id.textRating);

        annee.setText("(" + c.getInt(c.getColumnIndex(DBHelper.COL_ANNEE)) + ") ");
        titre.setText(c.getString(c.getColumnIndex(DBHelper.COL_TITRE)));
        rating.setText(c.getInt(c.getColumnIndex(DBHelper.COL_RATING))+"%");
        return v;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return null;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
    }


}
