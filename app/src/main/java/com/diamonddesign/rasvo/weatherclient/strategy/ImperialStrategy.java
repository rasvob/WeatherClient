package com.diamonddesign.rasvo.weatherclient.strategy;

import com.diamonddesign.rasvo.weatherclient.orm.CurrentConditions;
import com.diamonddesign.rasvo.weatherclient.orm.DailyConditions;
import com.diamonddesign.rasvo.weatherclient.orm.HourlyCondition;

/**
 * Created by rasvo on 14.11.2016.
 */

public class ImperialStrategy implements IUnitStrategy {
    @Override
    public double getWindSpeed(CurrentConditions conditions) {
        return conditions.getWindSpeedImperial();
    }

    @Override
    public double getWindSpeed(HourlyCondition conditions) {
        return conditions.getWindSpeedImperial();
    }

    @Override
    public double getVisibility(CurrentConditions conditions) {
        return conditions.getVisibilityImperial();
    }

    @Override
    public double getPressure(CurrentConditions conditions) {
        return conditions.getPressureImperial();
    }

    @Override
    public double getWindSpeed(DailyConditions conditions, boolean isDay) {
        return isDay ? conditions.getDayWindSpeedImperial() : conditions.getNightWindSpeedImperial();
    }

    @Override
    public String getWindSpeedUnit() {
        return "mi/h";
    }

    @Override
    public String getVisibilityUnit() {
        return "mi";
    }

    @Override
    public String getPressureUnit() {
        return "inHg";
    }
}
