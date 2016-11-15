package com.diamonddesign.rasvo.weatherclient.strategy;

import com.diamonddesign.rasvo.weatherclient.orm.CurrentConditions;
import com.diamonddesign.rasvo.weatherclient.orm.DailyConditions;

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
    public double getTemperatureMin(DailyConditions conditions) {
        return conditions.getTemperatureMinMetric();
    }

    @Override
    public double getTemperatureMax(DailyConditions conditions) {
        return conditions.getTemperatureMaxMetric();
    }

    @Override
    public double getRealFeelTemperatureMin(DailyConditions conditions) {
        return conditions.getTemperatureRealFeelMinMetric();
    }

    @Override
    public double getRealFeelTemperatureMax(DailyConditions conditions) {
        return conditions.getTemperaturRealFeeleMaxMetric();
    }

    @Override
    public String getUnit() {
        return "°C";
    }
}
