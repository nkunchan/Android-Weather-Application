package com.example.weatherforecast;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FavoriteAdapter.OnItemClickListenerLong,FavoriteAdapter.IFlag {
    EditText city;
    EditText country;
    Button search;
    RecyclerView rmain;
    ArrayList<Favorites> flist;
    String temp="0";
    TextView nocitylabel;
    FavoriteAdapter fadap;

DBManager dm;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        city= (EditText) findViewById(R.id.cityedit);
        country= (EditText) findViewById(R.id.countryedit);
        search= (Button) findViewById(R.id.searchbtn);
        nocitylabel= (TextView) findViewById(R.id.noCitieslbl);
        dm=new DBManager(this);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(city.length()==0)
                {
                    city.setError("Please enter correct city name");
                }
                else if(country.length()==0) {
                    country.setError("Please enter correct country name");
                }
                else {
                    Intent i = new Intent(MainActivity.this, CityWeather.class);
                    i.putExtra("city", city.getText().toString());
                    i.putExtra("country", country.getText().toString());
                    startActivity(i);
                }

            }
        });
        flist= (ArrayList<Favorites>) dm.getAll();
        Log.d("flist",flist.toString());
        TextView savedcities= (TextView) findViewById(R.id.SavedCitieslbl);
        if(flist!=null && !flist.isEmpty()) {
            savedcities.setText("Saved Cities");
            nocitylabel.setVisibility(View.GONE);
            //LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            rmain = (RecyclerView) findViewById(R.id.rvMain);
            ArrayList<Favorites> mainfav=new ArrayList<>();
            ArrayList<Favorites> mainfav2=new ArrayList<>();
            for(int k=0;k<flist.size();k++)
            {
                if(flist.get(k).getFavorite().toString().compareTo("true")==0)
                {
                    mainfav.add(flist.get(k));
                }
            }
            for(int k=0;k<flist.size();k++)
            {
                if(flist.get(k).getFavorite().toString().compareTo("false")==0)
                {
                    mainfav.add(flist.get(k));
                }
            }
            Log.d("mainfav",mainfav.toString());








            FavoriteAdapter fadap = new FavoriteAdapter(this, mainfav, this, this);
            rmain.setLayoutManager(layoutManager);
            rmain.setAdapter(fadap);

            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
            temp = sharedPrefs.getString("temp", "");
            Log.d("temp", temp);
        }
        else{


            nocitylabel.setText("There are no cities to display. Search the city from the search box and save.");
            nocitylabel.setVisibility(View.VISIBLE);
        }
    }


//    @Override
//    protected void onResume() {
//        super.onResume();
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        RecyclerView rmain = (RecyclerView) findViewById(R.id.rvMain);
//        FavoriteAdapter fadap = new FavoriteAdapter(this, flist, this,this);
//        //fadap.notifyDataSetChanged();
//        rmain.setLayoutManager(layoutManager);
//        rmain.setAdapter(fadap);
//    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        ArrayList<Favorites> fav= (ArrayList<Favorites>) dm.getAll();
        if(fav.isEmpty())
        {
            nocitylabel.setText("There are no cities to display. Search the city from the search box and save.");
            nocitylabel.setVisibility(View.VISIBLE);

        }

        else {
            TextView savedcities= (TextView) findViewById(R.id.SavedCitieslbl);
            savedcities.setText("Saved Cities");
            nocitylabel.setVisibility(View.GONE);
        }
        RecyclerView rmain1=(RecyclerView) findViewById(R.id.rvMain);
        flist=fav;
        ArrayList<Favorites> mainfav=new ArrayList<>();
        ArrayList<Favorites> mainfav2=new ArrayList<>();
        for(int k=0;k<flist.size();k++)
        {
            if(flist.get(k).getFavorite().toString().compareTo("true")==0)
            {
                mainfav.add(flist.get(k));
            }
        }
        for(int k=0;k<flist.size();k++)
        {
            if(flist.get(k).getFavorite().toString().compareTo("false")==0)
            {
                mainfav.add(flist.get(k));
            }
        }
        Log.d("main fav in restart",mainfav.toString());
        FavoriteAdapter fadap2 = new FavoriteAdapter(this,mainfav,this,this);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rmain1.setLayoutManager(layoutManager1);
        rmain1.setAdapter(fadap2);


    }

    @Override
    public void onLongItemClick() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView rmain = (RecyclerView) findViewById(R.id.rvMain);
        FavoriteAdapter fadap = new FavoriteAdapter(this, (ArrayList<Favorites>) dm.getAll(), this,this);
        //fadap.notifyDataSetChanged();
        rmain.setLayoutManager(layoutManager);
        rmain.setAdapter(fadap);
        Log.d("seefavlistondelete",dm.getAll().toString());
        Toast.makeText(this,"City Deleted",Toast.LENGTH_LONG).show();
        ArrayList<Favorites>f=(ArrayList<Favorites>) dm.getAll();
        if(f.isEmpty())
        {
            TextView savedcities= (TextView) findViewById(R.id.SavedCitieslbl);
            savedcities.setVisibility(View.GONE);

            nocitylabel.setText("There are no cities to display. Search the city from the search box and save.");
            nocitylabel.setVisibility(View.VISIBLE);
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menumain, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
                startActivity(new Intent(this, TempPref.class));

                return true;

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void imgClick(Favorites fav) {
        dm.update(fav);
        Log.d("fav string", fav.toString());
        ArrayList<Favorites> newlist = (ArrayList<Favorites>) dm.getAll();
        ArrayList<Favorites> newlist3=new ArrayList<>();

        if (fav.getFavorite().equals("true")) {

            ArrayList<Favorites> newlist2 = new ArrayList<>();
            for (int i = 0; i < newlist.size(); i++) {
                if (newlist.get(i).toString().compareTo(fav.toString()) == 0) {

                } else {
                   // newlist2.add(newlist.get(i));
                    newlist3.add(newlist.get(i));

                }
            }
            newlist2.add(0, fav);
            for(int k=0;k<newlist3.size();k++)
            {
                if(newlist3.get(k).getFavorite().compareTo("true")==0)
                {
                    newlist2.add(newlist3.get(k));
                }
            }
            for(int k=0;k<newlist3.size();k++)
            {
                if(newlist3.get(k).getFavorite().compareTo("false")==0)
                {
                    newlist2.add(newlist3.get(k));
                }
            }
            FavoriteAdapter fadap3 = new FavoriteAdapter(this, newlist2, this, this);
            rmain.setAdapter(fadap3);


        }

        else{






        }
    }
}
