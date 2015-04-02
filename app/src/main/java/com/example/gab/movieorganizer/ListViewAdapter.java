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
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if(v==null){
            v = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        }
        Cursor c = getCursor();
        c.moveToPosition(position);
        TextView titre = (TextView)v.findViewById(android.R.id.text1);
        titre.setText(c.getString(c.getColumnIndex(DBHelper.COL_TITRE))+" "+c.getString(c.getColumnIndex(DBHelper.COL_SYNOPSIS)) );
        titre.setTextColor(Color.BLACK);
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
