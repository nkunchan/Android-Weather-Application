package com.example.weatherforecast;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Nikita on 10/19/2016.
 */
public class ForecastUtil {

    public static ArrayList<Forecast> GetJson(String sb) {
        ArrayList<Forecast> list;
        list = new ArrayList<>();
        try {

            JSONObject root = new JSONObject(sb);
            JSONObject job = root.getJSONObject("city");
            JSONArray jarray = root.getJSONArray("list");
            for (int i = 0; i < jarray.length(); i++) {
                Forecast forecast = new Forecast();
                forecast.setCityname(job.getString("name"));
                forecast.setCountry(job.getString("country"));
                JSONObject jlistobj = jarray.getJSONObject(i);
                JSONObject jlisttobj1 = jlistobj.getJSONObject("main");
                double cel =(Double.parseDouble(jlisttobj1.getString("temp"))-32)/1.8;
                forecast.setTemp(String.valueOf( Math.round(cel)));
                forecast.setHumidity(jlisttobj1.getString("humidity"));
                forecast.setPressure(jlisttobj1.getString("pressure"));
                JSONArray jweather = jlistobj.getJSONArray("weather");
                for (int j = 0; j < jweather.length(); j++) {
                    JSONObject jwobj = jweather.getJSONObject(j);
                    forecast.setCondition(jwobj.getString("description"));
                    forecast.setIcon(jwobj.getString("icon"));
                }
                JSONObject jwindobj = jlistobj.getJSONObject("wind");
                forecast.setWind(jwindobj.getString("speed") + ", " + jwindobj.getString("deg"));
                String parsedDate = jlistobj.getString("dt_txt");
                Log.d("parsed",parsedDate);
                String s= "2016-10-20 00:00:00";
                SimpleDateFormat date1 = new SimpleDateFormat("yyyy-M-dd HH:mm:ss");

                Date d = date1.parse(parsedDate);
                SimpleDateFormat objDate = new SimpleDateFormat("MMM dd, yyyy");
                forecast.setDate(objDate.format(d));
                Log.d("date d",d.toString());
                SimpleDateFormat date2 = new SimpleDateFormat("");

                SimpleDateFormat objTime = new SimpleDateFormat("h:mm a");
                forecast.setTime(objTime.format(d));
                list.add(forecast);

            }
            Log.d("getparsed", list.toString());
            return list;

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return list;
    }
}
