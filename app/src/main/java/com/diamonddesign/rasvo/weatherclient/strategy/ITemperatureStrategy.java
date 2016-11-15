package com.diamonddesign.rasvo.weatherclient.strategy;

import com.diamonddesign.rasvo.weatherclient.orm.CurrentConditions;

/**
 * Created by rasvo on 14.11.2016.
 */

public interface ITemperatureStrategy {
    double getTemperature(CurrentConditions conditions);
    double getRealFeelTemperature(CurrentConditions conditions);
    String getUnit();
}
