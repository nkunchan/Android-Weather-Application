package com.example.weatherforecast;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Nikita on 10/19/2016.
 */
public class DBOpenHelper extends SQLiteOpenHelper {
    static final String DB_NAME="fav.db";
    static final int DB_VERSION=10;

    public DBOpenHelper(Context context) {
        super(context, DB_NAME, null,DB_VERSION);
        Log.d("check here","in database open helper");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        FavTable.onCreate(db);
        Log.d("check here","in on create");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        FavTable.onUpgrade(db,oldVersion,newVersion);
    }


}
