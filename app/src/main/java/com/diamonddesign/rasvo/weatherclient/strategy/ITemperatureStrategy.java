package com.diamonddesign.rasvo.weatherclient.strategy;

import com.diamonddesign.rasvo.weatherclient.orm.CurrentConditions;
import com.diamonddesign.rasvo.weatherclient.orm.DailyConditions;
import com.diamonddesign.rasvo.weatherclient.orm.HourlyCondition;

/**
 * Created by rasvo on 14.11.2016.
 */

public interface ITemperatureStrategy {
    double getTemperature(CurrentConditions conditions);
    double getTemperature(HourlyCondition conditions);
    double getRealFeelTemperature(CurrentConditions conditions);
    double getRealFeelTemperature(HourlyCondition conditions);
    double getTemperatureMin(DailyConditions conditions);
    double getTemperatureMax(DailyConditions conditions);
    double getRealFeelTemperatureMin(DailyConditions conditions);
    double getRealFeelTemperatureMax(DailyConditions conditions);
    String getUnit();
}
