package com.diamonddesign.rasvo.weatherclient.strategy;

import com.diamonddesign.rasvo.weatherclient.orm.CurrentConditions;
import com.diamonddesign.rasvo.weatherclient.orm.DailyConditions;

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
    public double getTemperatureMin(DailyConditions conditions) {
        return conditions.getTemperatureMinImperial();
    }

    @Override
    public double getTemperatureMax(DailyConditions conditions) {
        return conditions.getTemperatureMaxImperial();
    }

    @Override
    public double getRealFeelTemperatureMin(DailyConditions conditions) {
        return conditions.getTemperatureRealFeelMinImperial();
    }

    @Override
    public double getRealFeelTemperatureMax(DailyConditions conditions) {
        return conditions.getTemperaturRealFeeleMaxImperial();
    }

    @Override
    public String getUnit() {
        return "°F";
    }
}
