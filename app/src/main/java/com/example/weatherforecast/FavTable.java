package com.example.weatherforecast;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Nikita on 10/19/2016.
 */
public class FavTable {
    static final String TABLENAME="cities";
    //static final String COLUMN_ID="id";
    static final String COLUMN_CITY="city";
    static final String COLUMN_COUNTRY="country";
    static final String COLUMN_TEMP="temp";
    static final String COLUMN_FAVORITE="favorite";
    static final String COLUMN_UDATE="updateddate";
    static String create_query="CREATE TABLE Cities " +
            "(city text not null, country text not null," +
            "temp text not null,favorite text not null, updateddate text not null, PRIMARY KEY(city,country));";

    static public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(create_query);
    }
    static public void onUpgrade(SQLiteDatabase db, int oldVersion,int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS "+TABLENAME);
        FavTable.onCreate(db);
    }

}
