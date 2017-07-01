package com.example.weatherforecast;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Nikita on 10/20/2016.
 */
public class DisplayHourlyAdapter extends RecyclerView.Adapter<DisplayHourlyAdapter.ViewHolder> {
    ArrayList<Forecast> forecastList;
    Context context;
    static String temp="0";
    public  DisplayHourlyAdapter(Context context, ArrayList<Forecast> forecastList)
    {
        this.context=context;
        this.forecastList=forecastList;

    }
    @Override
    public DisplayHourlyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        View fView= inflater.inflate(R.layout.item_row_lyt_forecast,parent,false);
        ViewHolder holder=new ViewHolder(fView);
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        temp=sharedPrefs.getString("temp","");
        Log.d("temp",temp);
        return  holder;


    }

    @Override
    public void onBindViewHolder(DisplayHourlyAdapter.ViewHolder holder, int position) {
        TextView ttime= holder.time;
        ttime.setText(forecastList.get(position).getTime());
        TextView tpressure=holder.pressure;
        tpressure.setText(forecastList.get(position).getPressure()+" hPa");
        TextView thum=holder.humidity;
        thum.setText(forecastList.get(position).getHumidity()+"%");
        TextView tcon=holder.cond;
        tcon.setText(forecastList.get(position).getCondition());
        TextView ttemp=holder.temp;
        if(temp.compareTo("0")==0) {
            ttemp.setText(forecastList.get(position).getTemp()+"°C");
            Log.d("temp in degrees","abc");
        }
        else{

            ttemp.setText((String.valueOf(Math.round(Double.parseDouble(forecastList.get(position).getTemp())*1.8)+32))+"°F");
            Log.d("temp in fh","abc");
        }

        TextView twind=holder.wind;
        twind.setText(forecastList.get(position).getWind());
        ImageView icon =holder.icon;
        String img="http://openweathermap.org/img/w/"+forecastList.get(position).icon+".png";
        Picasso.with(context).load(img).into(icon);
            }

    @Override
    public int getItemCount() {
        return forecastList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView time, pressure, temp, wind, humidity, cond;
        ImageView icon;

        public ViewHolder(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.txttime);
            pressure = (TextView) itemView.findViewById(R.id.presvalue);
            temp = (TextView) itemView.findViewById(R.id.tmpvalue);
            wind = (TextView) itemView.findViewById(R.id.windvalue);
            humidity = (TextView) itemView.findViewById(R.id.humvalue);
            cond = (TextView) itemView.findViewById(R.id.condvalue);
            icon = (ImageView) itemView.findViewById(R.id.imgIcon);
            itemView.setBackgroundColor(Color.parseColor("#D3D3D3"));

        }

    }
}
