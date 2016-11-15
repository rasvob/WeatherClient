package com.diamonddesign.rasvo.weatherclient.strategy;

import com.diamonddesign.rasvo.weatherclient.orm.CurrentConditions;

/**
 * Created by rasvo on 14.11.2016.
 */

public interface IUnitStrategy {
    double getWindSpeed(CurrentConditions conditions);
    double getVisibility(CurrentConditions conditions);
    double getPressure(CurrentConditions conditions);

    String getWindSpeedUnit();
    String getVisibilityUnit();
    String getPressureUnit();
}
