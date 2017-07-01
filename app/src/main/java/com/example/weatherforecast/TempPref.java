package com.example.weatherforecast;

import android.content.SharedPreferences;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

public class TempPref extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.temppref);
        ListPreference listPreference = (ListPreference) findPreference("temp");
        listPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Log.d("newv",newValue.toString());
                if(newValue.toString().compareTo("1")==0) {
                    Toast.makeText(TempPref.this, "Temperature Unit has been changed to ºC to ºF", Toast.LENGTH_LONG).show();
                    return true;
                }
                else{
                    Toast.makeText(TempPref.this, "Temperature Unit has been changed to ºF to ºC", Toast.LENGTH_LONG).show();
                    return true;
                }
            }
        });


    }


}
