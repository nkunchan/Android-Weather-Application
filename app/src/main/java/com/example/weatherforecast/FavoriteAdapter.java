package com.example.weatherforecast;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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

import java.util.ArrayList;

/**
 * Created by Nikita on 10/20/2016.
 */
public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    ArrayList<Favorites> forecastList;
    Context context;
    OnItemClickListenerLong listener;
    static String temp = "0";
    static  IFlag flag;

    public FavoriteAdapter(Context context, ArrayList<Favorites> forecastList, OnItemClickListenerLong listener,IFlag flag) {
        this.context = context;
        this.forecastList = forecastList;
        this.listener = listener;
        this.flag=flag;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View fView = inflater.inflate(R.layout.item_mainrv_row, parent, false);
        ViewHolder holder = new ViewHolder(fView);
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        temp = sharedPrefs.getString("temp", "");
        Log.d("temp", temp);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Favorites fav = forecastList.get(position);

        holder.bind(fav, listener);
    }

    @Override
    public int getItemCount() {

        return forecastList.size();
    }

    //
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView location, date, temperature;
        ImageView favStar;

        public ViewHolder(View itemView) {
            super(itemView);

            date = (TextView) itemView.findViewById(R.id.txtdate);
            location = (TextView) itemView.findViewById(R.id.txtloc);
            temperature = (TextView) itemView.findViewById(R.id.txttemp);
            favStar = (ImageView) itemView.findViewById(R.id.img);

            itemView.setBackgroundColor(Color.parseColor("#D3D3D3"));


        }
        public void bind(final Favorites fObject, final OnItemClickListenerLong listener) {
            location.setText(fObject.city + ", " + fObject.country);
            if(fObject.getFavorite().equals("true"))
            favStar.setImageResource(R.drawable.star_gold);
            else
                favStar.setImageResource(R.drawable.star_gray);
            favStar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String favFlag=fObject.getFavorite();
                    Object tag=favStar.getTag();
                    int imgset=R.drawable.star_gray;
                    if(tag!=null && ((Integer)tag).intValue() == imgset ) {
                        imgset=R.drawable.star_gold;
                        favFlag="true";
                    }
                    else{
                        favFlag="false";
                    }
                    favStar.setTag(imgset);
                    favStar.setImageResource(imgset);
                    fObject.setFavorite(favFlag);
                    flag.imgClick(fObject);
                }
            });
            date.setText("Updated On"+ fObject.getUpdatedDate());
            if (temp.compareTo("0") == 0) {
                temperature.setText(fObject.temp + "°C");
                Log.d("temp in degrees", "abc");
            } else {
                temperature.setText((String.valueOf(Math.round(Double.parseDouble(fObject.temp) * 1.8) + 32)) + "°F");
                Log.d("temp in fh", "abc");
            }

            itemView.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    DBManager dm = new DBManager(itemView.getContext());
                    dm.delete(fObject);
                    listener.onLongItemClick();
                    return true;
                }


            });

        }
    }

    //
    public interface OnItemClickListenerLong {
        void onLongItemClick();

    }
    public interface IFlag{
        void imgClick(Favorites f);
    }
}