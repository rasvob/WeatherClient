package com.diamonddesign.rasvo.weatherclient.strategy;

import com.diamonddesign.rasvo.weatherclient.orm.CurrentConditions;
import com.diamonddesign.rasvo.weatherclient.orm.DailyConditions;

/**
 * Created by rasvo on 14.11.2016.
 */

public interface ITemperatureStrategy {
    double getTemperature(CurrentConditions conditions);
    double getRealFeelTemperature(CurrentConditions conditions);
    double getTemperatureMin(DailyConditions conditions);
    double getTemperatureMax(DailyConditions conditions);
    double getRealFeelTemperatureMin(DailyConditions conditions);
    double getRealFeelTemperatureMax(DailyConditions conditions);
    String getUnit();
}
