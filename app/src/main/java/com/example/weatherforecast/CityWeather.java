package com.example.weatherforecast;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CityWeather extends AppCompatActivity implements ParseForecastAsync.SetForecast, ForecastAdapter.OnItemClickListener {
    TextView heading;
    ArrayList<Forecast> mainForecastList;
    RecyclerView rView, rView2;
    int clickedPosition = 0;
    String city, country;
    ArrayList<Forecast> medians;
    ArrayList<ArrayList<Forecast>> firstlist;
    TextView dateheader;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);
        dateheader = (TextView) findViewById(R.id.txtDate);
        city = getIntent().getExtras().getString("city");
        country = getIntent().getExtras().getString("country");
        String url = "http://api.openweathermap.org/data/2.5/forecast?q=" + city + "," + country + "&apikey=34acec53ab7213de9860766d3c88211a&mode=json";
        new ParseForecastAsync(this).execute(url);
        heading = (TextView) findViewById(R.id.txtHeading);
        String location = "Daily Forecast for " + city + ", " + country;
        heading.setText(location);
        pd = new ProgressDialog(this);
        pd.setMessage("Loading Data");
        pd.show();
    }

    @Override
    public void SetForecastmethod(ArrayList<Forecast> list) {
        pd.dismiss();
        mainForecastList = list;
        ArrayList<String> dates = new ArrayList<>();
        firstlist = new ArrayList<>();
        Set<String> uniquevalues = new LinkedHashSet<String>();
        ArrayList<String> avgTemp = new ArrayList<>();
        if (mainForecastList != null && !mainForecastList.isEmpty()) {
            for (Forecast record : mainForecastList) {
                uniquevalues.add(record.getDate());// 5 dates
            }
            for (String dt : uniquevalues) {
                double avg = 0;
                ArrayList<Forecast> temp = new ArrayList<>();
                for (int j = 0; j < list.size(); j++) {
                    if (list.get(j).getDate().equals(dt)) {
                        temp.add(list.get(j));
                        avg = (avg + Double.parseDouble(list.get(j).temp));
                    }

                }

                avgTemp.add(String.valueOf(avg / 2));
                Log.d("temp", temp.toString());
                firstlist.add(temp);
            }
            Log.d("firstlist", firstlist.toString());
            medians = new ArrayList<>();
            for (int l = 0; l < firstlist.size(); l++) {

                medians.add(firstlist.get(l).get(firstlist.get(l).size() / 2));
                medians.get(l).setTemp(avgTemp.get(l));


            }
            dateheader.setText("Three Hourly Forecast on " + medians.get(clickedPosition).date);
            Log.d("medians", medians.toString());


            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            rView = (RecyclerView) findViewById(R.id.rvForecast);
            ForecastAdapter fadap = new ForecastAdapter(this, medians, this);
            rView.setLayoutManager(layoutManager);
            rView.setAdapter(fadap);
// for display
            LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            rView2 = (RecyclerView) findViewById(R.id.rvForecastDetails);
            DisplayHourlyAdapter fadap1 = new DisplayHourlyAdapter(this, firstlist.get(clickedPosition));
            rView2.setLayoutManager(layoutManager1);
            rView2.setAdapter(fadap1);
        } else {
            Toast.makeText(CityWeather.this, "No items to match your Query", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                DBManager dm = new DBManager(this);
                ArrayList<Favorites> favlist = (ArrayList<Favorites>) dm.getAll();
                int flag = 0;
                for (int i = 0; i < favlist.size(); i++) {
                    if (city.equals(favlist.get(i).city) && country.equals(favlist.get(i).country)) {
                        flag = 1;
                        dm.update(new Favorites(city, country, medians.get(clickedPosition).temp, "false"));
                        Toast.makeText(this, "City Updated", Toast.LENGTH_LONG).show();
                    }
                }
                if (flag != 1) {
                    dm.save(new Favorites(city, country, medians.get(clickedPosition).temp, "false"));
                    Toast.makeText(this, "City Saved", Toast.LENGTH_LONG).show();
                }
                Log.d("db entries", dm.getAll().toString());
                break;

            case R.id.setting:
                startActivity(new Intent(this, TempPref.class));
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemClick(Forecast item, int position) {
        clickedPosition = position;
        dateheader.setText("Three Hourly Forecast on " + firstlist.get(clickedPosition).get(0).date);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        DisplayHourlyAdapter fadap2 = new DisplayHourlyAdapter(this, firstlist.get(clickedPosition));
        rView2.setLayoutManager(layoutManager2);
        rView2.setAdapter(fadap2);


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("backpressed", "backpressed");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rView = (RecyclerView) findViewById(R.id.rvForecast);
        ForecastAdapter fadap = new ForecastAdapter(this, medians, this);
        rView.setLayoutManager(layoutManager);
        rView.setAdapter(fadap);
        // for display
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rView2 = (RecyclerView) findViewById(R.id.rvForecastDetails);
        DisplayHourlyAdapter fadap1 = new DisplayHourlyAdapter(this, firstlist.get(clickedPosition));
        rView2.setLayoutManager(layoutManager1);
        rView2.setAdapter(fadap1);

    }


}
