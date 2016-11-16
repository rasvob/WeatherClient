package com.diamonddesign.rasvo.weatherclient.sharedprefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.diamonddesign.rasvo.weatherclient.enums.TemperatureUnits;
import com.diamonddesign.rasvo.weatherclient.enums.Units;

/**
 * Created by rasvo on 11.11.2016.
 */

public class SharedPrefsWrapper {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context context;

    private final String PREFS_NAME = "weather_client_prefs";
    private final String PREFS_TEMPERATURE = "temperature_unit";
    private final String PREFS_UNITS = "other_units";


    public SharedPrefsWrapper(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public TemperatureUnits getTemperatureUnits() {
        int unit = preferences.getInt(PREFS_TEMPERATURE, 0);
        return unit == 0 ? TemperatureUnits.CELSIUS : TemperatureUnits.FAHRENHEIT;
    }

    public void setTemperatureUnits(TemperatureUnits unit) {
        editor = preferences.edit();
        editor.putInt(PREFS_TEMPERATURE, unit == TemperatureUnits.CELSIUS ? 0 : 1);
        editor.commit();
    }

    public void setOtherUnits(Units units) {
        editor = preferences.edit();
        editor.putInt(PREFS_UNITS, units == Units.METRIC ? 0 : 1);
        editor.commit();
    }

    public Units getOtherUnits() {
        int units = preferences.getInt(PREFS_UNITS, 0);
        return units == 0 ? Units.METRIC : Units.IMPERIAL;
    }
}
