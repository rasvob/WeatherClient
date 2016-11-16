package com.diamonddesign.rasvo.weatherclient.sharedprefs;

import com.diamonddesign.rasvo.weatherclient.enums.TemperatureUnits;
import com.diamonddesign.rasvo.weatherclient.enums.Units;

/**
 * Created by rasvo on 16.11.2016.
 */

public class SharedPrefsState {
    private static SharedPrefsState instance = null;

    private TemperatureUnits temperatureUnits;
    private Units units;

    private SharedPrefsState() {

    }

    public static SharedPrefsState getInstance() {
        if(instance == null) {
            instance = new SharedPrefsState();
        }
        return instance;
    }

    public Units getUnits() {
        return units;
    }

    public void setUnits(Units units) {
        this.units = units;
    }

    public TemperatureUnits getTemperatureUnits() {
        return temperatureUnits;
    }

    public void setTemperatureUnits(TemperatureUnits temperatureUnits) {
        this.temperatureUnits = temperatureUnits;
    }
}
