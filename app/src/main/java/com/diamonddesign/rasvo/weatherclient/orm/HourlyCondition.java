package com.diamonddesign.rasvo.weatherclient.orm;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.diamonddesign.rasvo.weatherclient.R;
import com.diamonddesign.rasvo.weatherclient.models.NowGridItem;
import com.diamonddesign.rasvo.weatherclient.strategy.ITemperatureStrategy;
import com.diamonddesign.rasvo.weatherclient.strategy.IUnitStrategy;
import com.orm.SugarRecord;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by rasvo on 17.11.2016.
 */

public class HourlyCondition extends SugarRecord {
    String key;
    String epochTime;
    String date;
    int weatherIcon;
    String phrase;

    double temperatureMetric;
    double temperatureImperial;
    double temperatureRealFeelMetric;
    double temperatureRealFeelImperial;

    double relativeHumidity;
    String windDirection;
    double windSpeedMetric;
    double windSpeedImperial;
    double uvIndex;

    double precipitationProbability;
    double rainProbability;
    double snowProbability;
    double iceProbability;
    double cloudCover;

    public HourlyCondition() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public double getPrecipitationProbability() {
        return precipitationProbability;
    }

    public void setPrecipitationProbability(double precipitationProbability) {
        this.precipitationProbability = precipitationProbability;
    }

    public double getRainProbability() {
        return rainProbability;
    }

    public void setRainProbability(double rainProbability) {
        this.rainProbability = rainProbability;
    }

    public double getSnowProbability() {
        return snowProbability;
    }

    public void setSnowProbability(double snowProbability) {
        this.snowProbability = snowProbability;
    }

    public double getIceProbability() {
        return iceProbability;
    }

    public void setIceProbability(double iceProbability) {
        this.iceProbability = iceProbability;
    }

    public double getCloudCover() {
        return cloudCover;
    }

    public void setCloudCover(double cloudCover) {
        this.cloudCover = cloudCover;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public Drawable getIconDrawable(Context context) {
        Resources resources = context.getResources();
        int id = resources.getIdentifier("ic_" + this.weatherIcon, "drawable", context.getPackageName());
        return ContextCompat.getDrawable(context, id);
    }

    public String getDayAndMonth(Context context) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.GERMAN);
        try {
            Date parse = format.parse(this.date);
            SimpleDateFormat newFormat = new SimpleDateFormat("dd. MM.", Locale.GERMAN);
            newFormat.setTimeZone(TimeZone.getTimeZone(TimeZone.getDefault().getID()));
            return newFormat.format(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return context.getString(R.string.no_info);
    }

    public String getTime(Context context) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.GERMAN);
        try {
            Date parse = format.parse(this.date);
            SimpleDateFormat newFormat = new SimpleDateFormat("HH:mm", Locale.GERMAN);
            newFormat.setTimeZone(TimeZone.getTimeZone(TimeZone.getDefault().getID()));
            return newFormat.format(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return context.getString(R.string.no_info);
    }

    public String getDayName(Context context) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.GERMAN);
        try {
            Date parse = format.parse(this.date);
            SimpleDateFormat newFormat = new SimpleDateFormat("EEE", Locale.ENGLISH);
            newFormat.setTimeZone(TimeZone.getTimeZone(TimeZone.getDefault().getID()));
            return newFormat.format(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return context.getString(R.string.no_info);
    }

    public String getFormattedDate(Context context) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.GERMAN);
        try {
            Date parse = format.parse(this.date);
            SimpleDateFormat newFormat = new SimpleDateFormat("dd. MM. yyyy HH:mm:ss", Locale.GERMAN);
            newFormat.setTimeZone(TimeZone.getTimeZone(TimeZone.getDefault().getID()));
            return newFormat.format(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return context.getString(R.string.no_info);
    }

    public ArrayList<NowGridItem> mapToGridItems(Context context, ITemperatureStrategy temperatureStrategy, IUnitStrategy unitStrategy) {
        ArrayList<NowGridItem> items = new ArrayList<>();
        NowGridItem temp = new NowGridItem();
        NowGridItem realFee = new NowGridItem();
        NowGridItem cloudCover = new NowGridItem();

        NowGridItem rain = new NowGridItem();
        NowGridItem snow = new NowGridItem();
        NowGridItem ice = new NowGridItem();

        NowGridItem windSpeed = new NowGridItem();
        NowGridItem uvIndex = new NowGridItem();
        NowGridItem humidity = new NowGridItem();

        DecimalFormat format = new DecimalFormat("#.00");

        //Headers
        temp.setHeader(context.getString(R.string.temperature));
        realFee.setHeader(context.getString(R.string.real_feel));
        cloudCover.setHeader(context.getString(R.string.cloud_cover));

        rain.setHeader(context.getString(R.string.rain));
        snow.setHeader(context.getString(R.string.snow));
        ice.setHeader(context.getString(R.string.ice));

        windSpeed.setHeader(context.getString(R.string.wind_speed));
        uvIndex.setHeader(context.getString(R.string.uv_index));
        humidity.setHeader(context.getString(R.string.humidity));

        //Values
        temp.setValue(format.format(temperatureStrategy.getTemperature(this)));
        realFee.setValue(format.format(temperatureStrategy.getRealFeelTemperature(this)));
        cloudCover.setValue(String.valueOf(this.cloudCover));

        rain.setValue(String.valueOf(this.rainProbability));
        snow.setValue(String.valueOf(this.snowProbability));
        ice.setValue(String.valueOf(this.iceProbability));

        windSpeed.setValue(format.format(unitStrategy.getWindSpeed(this)));
        uvIndex.setValue(String.valueOf(this.uvIndex));
        humidity.setValue(String.valueOf(this.relativeHumidity));

        //Units
        temp.setUnit(temperatureStrategy.getUnit());
        realFee.setUnit(temperatureStrategy.getUnit());
        cloudCover.setUnit(context.getString(R.string.percent));

        rain.setUnit(context.getString(R.string.percent));
        snow.setUnit(context.getString(R.string.percent));
        ice.setUnit(context.getString(R.string.percent));

        windSpeed.setUnit(unitStrategy.getWindSpeedUnit());
        uvIndex.setUnit("");
        humidity.setUnit(context.getString(R.string.percent));

        items.add(temp);
        items.add(realFee);
        items.add(cloudCover);

        items.add(rain);
        items.add(snow);
        items.add(ice);

        items.add(windSpeed);
        items.add(uvIndex);
        items.add(humidity);

        return items;
    }
}
