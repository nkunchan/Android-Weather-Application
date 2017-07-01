package com.example.weatherforecast;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by Nikita on 10/19/2016.
 */
public class DBManager {
    private SQLiteDatabase db;
    private Context context;
    private DBOpenHelper dbOpenHelper;
    private FavDAO favDAO;


    public DBManager(Context context) {
        this.context = context;
        dbOpenHelper = new DBOpenHelper(this.context);
        db=dbOpenHelper.getWritableDatabase();
        favDAO=new FavDAO(db);
    }
    public void close()
    {
        if(db!=null)
        {
            db.close();
        }

    }
    public FavDAO getFavDAODAO()
    {
        return favDAO;
    }
    public long save(Favorites fav)
    {
        return favDAO.save(fav);
    }
    public boolean update(Favorites note) {
        return  favDAO.update(note);
    }
    public boolean delete(Favorites note)
    {
        return favDAO.delete(note);
    }
    public List<Favorites> getAll() {
        return favDAO.getAll();
    }
    public Favorites get(String city,String country) {
        return favDAO.get(city,country);
    }
}
