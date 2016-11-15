package com.diamonddesign.rasvo.weatherclient.strategy;

import com.diamonddesign.rasvo.weatherclient.orm.CurrentConditions;
import com.diamonddesign.rasvo.weatherclient.orm.DailyConditions;

/**
 * Created by rasvo on 14.11.2016.
 */

public interface IUnitStrategy {
    double getWindSpeed(CurrentConditions conditions);
    double getVisibility(CurrentConditions conditions);
    double getPressure(CurrentConditions conditions);

    double getWindSpeed(DailyConditions conditions, boolean isDay);

    String getWindSpeedUnit();
    String getVisibilityUnit();
    String getPressureUnit();
}
