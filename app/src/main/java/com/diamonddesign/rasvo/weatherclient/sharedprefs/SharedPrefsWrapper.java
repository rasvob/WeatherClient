package com.diamonddesign.rasvo.weatherclient.sharedprefs;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by rasvo on 11.11.2016.
 */

public class SharedPrefsWrapper {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context context;

    private final String PREFS_NAME = "weather_client_prefs";

    public SharedPrefsWrapper(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

}
