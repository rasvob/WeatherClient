package com.diamonddesign.rasvo.weatherclient.orm;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.diamonddesign.rasvo.weatherclient.R;
import com.diamonddesign.rasvo.weatherclient.models.NowGridItem;
import com.diamonddesign.rasvo.weatherclient.strategy.ITemperatureStrategy;
import com.diamonddesign.rasvo.weatherclient.strategy.IUnitStrategy;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by rasvo on 13.11.2016.
 */

public class CurrentConditions extends SugarRecord {
    String key;
    String weatherText;
    String epochTime;
    String date;
    int weatherIcon;
    boolean isDayTime;
    double temperatureMetric;
    double temperatureImperial;
    double temperatureRealFeelMetric;
    double temperatureRealFeelImperial;
    double relativeHumidity;
    String windDirection;
    double windSpeedMetric;
    double windSpeedImperial;
    double uvIndex;
    double visibilityMetric;
    double visibilityImperial;
    double cloudCover;
    double pressureMetric;
    double pressureImperial;

    public CurrentConditions() {

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getWeatherText() {
        return weatherText;
    }

    public void setWeatherText(String weatherText) {
        this.weatherText = weatherText;
    }

    public String getEpochTime() {
        return epochTime;
    }

    public void setEpochTime(String epochTime) {
        this.epochTime = epochTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(int weatherIcon) {
        this.weatherIcon = weatherIcon;
    }

    public double getTemperatureMetric() {
        return temperatureMetric;
    }

    public void setTemperatureMetric(double temperatureMetric) {
        this.temperatureMetric = temperatureMetric;
    }

    public double getTemperatureImperial() {
        return temperatureImperial;
    }

    public void setTemperatureImperial(double temperatureImperial) {
        this.temperatureImperial = temperatureImperial;
    }

    public double getTemperatureRealFeelMetric() {
        return temperatureRealFeelMetric;
    }

    public void setTemperatureRealFeelMetric(double temperatureRealFeelMetric) {
        this.temperatureRealFeelMetric = temperatureRealFeelMetric;
    }

    public double getTemperatureRealFeelImperial() {
        return temperatureRealFeelImperial;
    }

    public void setTemperatureRealFeelImperial(double temperatureRealFeelImperial) {
        this.temperatureRealFeelImperial = temperatureRealFeelImperial;
    }

    public double getRelativeHumidity() {
        return relativeHumidity;
    }

    public void setRelativeHumidity(double relativeHumidity) {
        this.relativeHumidity = relativeHumidity;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public double getWindSpeedMetric() {
        return windSpeedMetric;
    }

    public void setWindSpeedMetric(double windSpeedMetric) {
        this.windSpeedMetric = windSpeedMetric;
    }

    public double getWindSpeedImperial() {
        return windSpeedImperial;
    }

    public void setWindSpeedImperial(double windSpeedImperial) {
        this.windSpeedImperial = windSpeedImperial;
    }

    public double getUvIndex() {
        return uvIndex;
    }

    public void setUvIndex(double uvIndex) {
        this.uvIndex = uvIndex;
    }

    public double getVisibilityMetric() {
        return visibilityMetric;
    }

    public void setVisibilityMetric(double visibilityMetric) {
        this.visibilityMetric = visibilityMetric;
    }

    public double getVisibilityImperial() {
        return visibilityImperial;
    }

    public void setVisibilityImperial(double visibilityImperial) {
        this.visibilityImperial = visibilityImperial;
    }

    public double getCloudCover() {
        return cloudCover;
    }

    public void setCloudCover(double cloudCover) {
        this.cloudCover = cloudCover;
    }

    public double getPressureMetric() {
        return pressureMetric;
    }

    public void setPressureMetric(double pressureMetric) {
        this.pressureMetric = pressureMetric;
    }

    public double getPressureImperial() {
        return pressureImperial;
    }

    public void setPressureImperial(double pressureImperial) {
        this.pressureImperial = pressureImperial;
    }

    public boolean isDayTime() {
        return isDayTime;
    }

    public void setDayTime(boolean dayTime) {
        isDayTime = dayTime;
    }

    public ArrayList<NowGridItem> mapToGridItems(Context context, ITemperatureStrategy temperatureStrategy, IUnitStrategy unitStrategy) {
        ArrayList<NowGridItem> items = new ArrayList<>();
        NowGridItem temp = new NowGridItem();
        NowGridItem realFeel = new NowGridItem();
        NowGridItem cloudCover = new NowGridItem();

        NowGridItem humidity = new NowGridItem();
        NowGridItem uvIndex = new NowGridItem();
        NowGridItem pressure = new NowGridItem();

        NowGridItem windDirection = new NowGridItem();
        NowGridItem windSpeed = new NowGridItem();
        NowGridItem visibility = new NowGridItem();

        //Headers
        temp.setHeader(context.getString(R.string.temperature));
        realFeel.setHeader(context.getString(R.string.real_feel));
        cloudCover.setHeader(context.getString(R.string.cloud_cover));

        humidity.setHeader(context.getString(R.string.humidity));
        uvIndex.setHeader(context.getString(R.string.uv_index));
        pressure.setHeader(context.getString(R.string.pressure));

        windDirection.setHeader(context.getString(R.string.wind_direction));
        windSpeed.setHeader(context.getString(R.string.wind_speed));
        visibility.setHeader(context.getString(R.string.visibility));

        //Values
        temp.setValue(String.valueOf(temperatureStrategy.getTemperature(this)));
        realFeel.setValue(String.valueOf(temperatureStrategy.getRealFeelTemperature(this)));
        cloudCover.setValue(String.valueOf(this.cloudCover));

        humidity.setValue(String.valueOf(this.relativeHumidity));
        uvIndex.setValue(String.valueOf(this.uvIndex));
        pressure.setValue(String.valueOf(unitStrategy.getPressure(this)));

        windDirection.setValue(String.valueOf(this.windDirection));
        windSpeed.setValue(String.valueOf(unitStrategy.getWindSpeed(this)));
        visibility.setValue(String.valueOf(unitStrategy.getVisibility(this)));

        //Units
        temp.setUnit(temperatureStrategy.getUnit());
        realFeel.setUnit(temperatureStrategy.getUnit());
        cloudCover.setUnit(context.getString(R.string.percent));

        humidity.setUnit(context.getString(R.string.percent));
        uvIndex.setUnit("");
        pressure.setUnit(unitStrategy.getPressureUnit());

        windDirection.setUnit("");
        windSpeed.setUnit(unitStrategy.getWindSpeedUnit());
        visibility.setUnit(unitStrategy.getVisibilityUnit());

        items.add(temp);
        items.add(realFeel);
        items.add(cloudCover);

        items.add(humidity);
        items.add(uvIndex);
        items.add(pressure);

        items.add(windDirection);
        items.add(windSpeed);
        items.add(visibility);

        return items;
    }
}
