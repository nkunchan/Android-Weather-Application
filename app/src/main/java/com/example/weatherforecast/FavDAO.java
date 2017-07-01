package com.example.weatherforecast;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Nikita on 10/19/2016.
 */
public class FavDAO {
    SQLiteDatabase db;

    public FavDAO(SQLiteDatabase db) {

        this.db=db;
    }
    public long save(Favorites fav)
    {
        ContentValues cv=new ContentValues();
        cv.put(FavTable.COLUMN_CITY,fav.getCity());
        cv.put(FavTable.COLUMN_COUNTRY,fav.getCountry());
        cv.put(FavTable.COLUMN_FAVORITE,fav.getFavorite());
        cv.put(FavTable.COLUMN_TEMP,fav.getTemp());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
        Date date = new Date();
        Log.d("date",(dateFormat.format(date)));
        cv.put(FavTable.COLUMN_UDATE, dateFormat.format(date));
        return db.insert(FavTable.TABLENAME,null,cv);

    }
    public boolean update(Favorites fav)
    {
        ContentValues cv=new ContentValues();
        cv.put(FavTable.COLUMN_FAVORITE,fav.getFavorite());
        cv.put(FavTable.COLUMN_TEMP,fav.getTemp());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
        Date date = new Date();
        Log.d("date",(dateFormat.format(date)));
        cv.put(FavTable.COLUMN_UDATE, dateFormat.format(date));

        return db.update(FavTable.TABLENAME,cv,FavTable.COLUMN_CITY +"=? and "+ FavTable.COLUMN_COUNTRY +"=?",new String[]{fav.getCity()+"",fav.getCountry()+""})>0;
    }
    public boolean delete(Favorites fav)
    {
        return db.delete(FavTable.TABLENAME,FavTable.COLUMN_CITY +"=? and "+FavTable.COLUMN_COUNTRY +"=?",new String[]{fav.getCity(),fav.getCountry()+""})>0;
    }
    public Favorites get(String city,String country)
    {
        Favorites fav=null;
        Cursor c=db.query(true,FavTable.TABLENAME,new String[] {FavTable.COLUMN_CITY,FavTable.COLUMN_COUNTRY,FavTable.COLUMN_TEMP,FavTable.COLUMN_FAVORITE},
                FavTable.COLUMN_CITY +","+FavTable.COLUMN_COUNTRY +"=?,?",new String[]{city,country+""},null,null,null,null);

        if(c!=null && c.moveToFirst())
            fav=buildfavFromCursor(c);

        if(!c.isClosed())
        {
            c.close();
        }

        return null;
    }
    public List<Favorites> getAll()
    {
        List<Favorites> Favorites=new ArrayList<Favorites>();
        Cursor c=db.query(FavTable.TABLENAME,new String[] {FavTable.COLUMN_CITY,FavTable.COLUMN_COUNTRY,FavTable.COLUMN_TEMP,
                FavTable.COLUMN_FAVORITE,FavTable.COLUMN_UDATE},null,null,null,null,null);

        if(c!=null && c.moveToFirst())
            do{
                Favorites fav=buildfavFromCursor(c);
                Favorites.add(fav);
            }
            while(c.moveToNext());

        if(!c.isClosed())
        {
            c.close();
        }



        return Favorites;
    }

    public Favorites buildfavFromCursor(Cursor c)
    {
        Favorites fav=null;
        if(c!=null)
        {
            fav=new Favorites();
            fav.setCity(c.getString(0));
            fav.setCountry(c.getString(1));
            fav.setTemp(c.getString(2));
            fav.setFavorite(c.getString(3));
            fav.setUpdatedDate(c.getString(4));

        }
        return fav;

    }

}
