package com.diamonddesign.rasvo.weatherclient.strategy;

import com.diamonddesign.rasvo.weatherclient.orm.CurrentConditions;

/**
 * Created by rasvo on 14.11.2016.
 */

public class CelsiusStrategy implements ITemperatureStrategy {

    @Override
    public double getTemperature(CurrentConditions conditions) {
        return conditions.getTemperatureMetric();
    }

    @Override
    public double getRealFeelTemperature(CurrentConditions conditions) {
        return conditions.getTemperatureRealFeelMetric();
    }

    @Override
    public String getUnit() {
        return "°C";
    }
}
