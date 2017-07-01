package com.example.weatherforecast;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Nikita on 10/19/2016.
 */
public class ParseForecastAsync extends AsyncTask<String,Void,ArrayList<Forecast>> {
    String read;
    SetForecast ref;
     public ParseForecastAsync(SetForecast ref)
     { this.ref=ref;}
    @Override
    protected ArrayList<Forecast> doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.connect();
            BufferedReader buf = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuffer sb = new StringBuffer();
            while ((read = buf.readLine()) != null) {
                sb.append(read);
            }
            return ForecastUtil.GetJson(sb.toString());


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;


    }

    @Override
    protected void onPostExecute(ArrayList<Forecast> forecasts) {
        super.onPostExecute(forecasts);
        ref.SetForecastmethod(forecasts);
    }

    public interface SetForecast
    {
        public void SetForecastmethod(ArrayList<Forecast> list);
    }
}
