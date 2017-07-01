package com.example.weatherforecast;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Nikita on 10/19/2016.
 */
public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {
    ArrayList<Forecast> forecastList;
    Context context;
    OnItemClickListener listener;
    static String temp = "0";

    public ForecastAdapter(Context context, ArrayList<Forecast> forecastList, OnItemClickListener listener) {
        this.context = context;
        this.forecastList = forecastList;
        this.listener = listener;
    }


    @Override
    public ForecastAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View fView = inflater.inflate(R.layout.item_row_display, parent, false);
        ViewHolder holder = new ViewHolder(fView);
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        temp = sharedPrefs.getString("temp", "");
        Log.d("temp", temp);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Forecast fObject = forecastList.get(position);

        holder.bind(fObject, listener);

    }

    @Override
    public int getItemCount() {
        return forecastList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView date, temperature;
        ImageView iconMedian;

        public ViewHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.time);
            temperature = (TextView) itemView.findViewById(R.id.temp);
            iconMedian = (ImageView) itemView.findViewById(R.id.imageView);
            itemView.setBackgroundColor(Color.parseColor("#D3D3D3"));
        }

        public void bind(final Forecast fObject, final OnItemClickListener listener) {

            date.setText(fObject.date);
            if (temp.compareTo("0") == 0) {
                temperature.setText(fObject.temp + "°C");
                Log.d("temp in degrees", "abc");
            } else {
                temperature.setText((String.valueOf(Math.round(Double.parseDouble(fObject.temp) * 1.8) + 32)) + "°F");
                Log.d("temp in fh", "abc");
            }
            String img = "http://openweathermap.org/img/w/" + fObject.icon + ".png";
            Picasso.with(itemView.getContext()).load(img).into(iconMedian);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    listener.onItemClick(fObject, getAdapterPosition());
                }

            });

        }
    }

    public interface OnItemClickListener {
        void onItemClick(Forecast item, int position);

    }


}
