package com.diamonddesign.rasvo.weatherclient.strategy;

import com.diamonddesign.rasvo.weatherclient.orm.CurrentConditions;
import com.diamonddesign.rasvo.weatherclient.orm.DailyConditions;

/**
 * Created by rasvo on 14.11.2016.
 */

public class MetricStrategy implements IUnitStrategy {
    @Override
    public double getWindSpeed(CurrentConditions conditions) {
        return conditions.getWindSpeedMetric();
    }

    @Override
    public double getVisibility(CurrentConditions conditions) {
        return conditions.getVisibilityMetric();
    }

    @Override
    public double getPressure(CurrentConditions conditions) {
        return conditions.getPressureMetric();
    }

    @Override
    public double getWindSpeed(DailyConditions conditions, boolean isDay) {
        return isDay ? conditions.getDayWindSpeedMetric() : conditions.getNightWindSpeedMetric();
    }

    @Override
    public String getWindSpeedUnit() {
        return "km/h";
    }

    @Override
    public String getVisibilityUnit() {
        return "km";
    }

    @Override
    public String getPressureUnit() {
        return "mb";
    }
}
