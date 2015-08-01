package com.vanbios.helptestapp.utils;


import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

    private SharedPreferences sharedPreferences;
    private final static String APP_PREFERENCES = "HelpTestPref";

    public SharedPref(Context context) {
        this.sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }


    public void setDataPresentStatus(boolean b) {
        sharedPreferences.edit().putBoolean("dataPresentStatus", b).apply();
    }

    public boolean getDataPresentStatus() {
        return sharedPreferences.getBoolean("dataPresentStatus", false);
    }
}
