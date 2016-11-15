package com.diamonddesign.rasvo.weatherclient.strategy;

import com.diamonddesign.rasvo.weatherclient.orm.CurrentConditions;

/**
 * Created by rasvo on 14.11.2016.
 */

public class FahrenheitStrategy implements ITemperatureStrategy {
    @Override
    public double getTemperature(CurrentConditions conditions) {
        return conditions.getTemperatureImperial();
    }

    @Override
    public double getRealFeelTemperature(CurrentConditions conditions) {
        return conditions.getTemperatureRealFeelImperial();
    }

    @Override
    public String getUnit() {
        return "°F";
    }
}
