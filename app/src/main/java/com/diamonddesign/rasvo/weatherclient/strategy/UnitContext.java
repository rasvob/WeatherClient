package com.diamonddesign.rasvo.weatherclient.strategy;

import com.diamonddesign.rasvo.weatherclient.enums.TemperatureUnits;
import com.diamonddesign.rasvo.weatherclient.enums.Units;

/**
 * Created by rasvo on 14.11.2016.
 */

public class UnitContext {
    private ITemperatureStrategy temperatureStrategy;
    private IUnitStrategy unitStrategy;

    public UnitContext(TemperatureUnits temperatureUnits, Units units) {
        switch (temperatureUnits) {
            case CELSIUS:
                temperatureStrategy = new CelsiusStrategy();
                break;
            case FAHRENHEIT:
                temperatureStrategy = new FahrenheitStrategy();
                break;
        }

        switch (units) {
            case METRIC:
                unitStrategy = new MetricStrategy();
                break;
            case IMPERIAL:
                unitStrategy = new ImperialStrategy();
                break;
        }
    }

    public ITemperatureStrategy getTemperatureStrategy() {
        return temperatureStrategy;
    }

    public void setTemperatureStrategy(TemperatureUnits temperatureUnits) {
        switch (temperatureUnits) {
            case CELSIUS:
                temperatureStrategy = new CelsiusStrategy();
                break;
            case FAHRENHEIT:
                temperatureStrategy = new FahrenheitStrategy();
                break;
        }
    }

    public IUnitStrategy getUnitStrategy() {
        return unitStrategy;
    }

    public void setUnitStrategy(Units units) {
        switch (units) {
            case METRIC:
                unitStrategy = new MetricStrategy();
                break;
            case IMPERIAL:
                unitStrategy = new ImperialStrategy();
                break;
        }
    }
}
