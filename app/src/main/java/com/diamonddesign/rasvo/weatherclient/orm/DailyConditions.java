package com.diamonddesign.rasvo.weatherclient.orm;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.diamonddesign.rasvo.weatherclient.R;
import com.diamonddesign.rasvo.weatherclient.models.NowGridItem;
import com.diamonddesign.rasvo.weatherclient.strategy.ITemperatureStrategy;
import com.diamonddesign.rasvo.weatherclient.strategy.IUnitStrategy;
import com.google.common.base.Strings;
import com.orm.SugarRecord;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by rasvo on 15.11.2016.
 */

public class DailyConditions extends SugarRecord {
    String key;
    String epochTime;
    String date;
    String sunSet;
    String sunRise;

    double temperatureMaxMetric;
    double temperatureMinMetric;
    double temperaturRealFeeleMaxMetric;
    double temperatureRealFeelMinMetric;

    double temperatureMaxImperial;
    double temperatureMinImperial;
    double temperaturRealFeeleMaxImperial;
    double temperatureRealFeelMinImperial;

    int dayIcon;
    String dayPhrase;
    double dayPrecipitationProbability;
    double dayRainProbability;
    double daySnowProbability;
    double dayIceProbability;
    String dayWindDirection;
    double dayWindSpeedMetric;
    double dayWindSpeedImperial;
    double dayCloudCover;

    int nightIcon;
    String nightPhrase;
    double nightPrecipitationProbability;
    double nightRainProbability;
    double nightSnowProbability;
    double nightIceProbability;
    String nightWindDirection;
    double nightWindSpeedMetric;
    double nightWindSpeedImperial;
    double nightCloudCover;

    public DailyConditions() {
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

    public String getSunSet() {
        return sunSet;
    }

    public void setSunSet(String sunSet) {
        this.sunSet = sunSet;
    }

    public String getSunRise() {
        return sunRise;
    }

    public void setSunRise(String sunRise) {
        this.sunRise = sunRise;
    }

    public double getTemperatureMaxMetric() {
        return temperatureMaxMetric;
    }

    public void setTemperatureMaxMetric(double temperatureMaxMetric) {
        this.temperatureMaxMetric = temperatureMaxMetric;
    }

    public double getTemperatureMinMetric() {
        return temperatureMinMetric;
    }

    public void setTemperatureMinMetric(double temperatureMinMetric) {
        this.temperatureMinMetric = temperatureMinMetric;
    }

    public double getTemperaturRealFeeleMaxMetric() {
        return temperaturRealFeeleMaxMetric;
    }

    public void setTemperaturRealFeeleMaxMetric(double temperaturRealFeeleMaxMetric) {
        this.temperaturRealFeeleMaxMetric = temperaturRealFeeleMaxMetric;
    }

    public double getTemperatureRealFeelMinMetric() {
        return temperatureRealFeelMinMetric;
    }

    public void setTemperatureRealFeelMinMetric(double temperatureRealFeelMinMetric) {
        this.temperatureRealFeelMinMetric = temperatureRealFeelMinMetric;
    }

    public double getTemperatureMaxImperial() {
        return temperatureMaxImperial;
    }

    public void setTemperatureMaxImperial(double temperatureMaxImperial) {
        this.temperatureMaxImperial = temperatureMaxImperial;
    }

    public double getTemperatureMinImperial() {
        return temperatureMinImperial;
    }

    public void setTemperatureMinImperial(double temperatureMinImperial) {
        this.temperatureMinImperial = temperatureMinImperial;
    }

    public double getTemperaturRealFeeleMaxImperial() {
        return temperaturRealFeeleMaxImperial;
    }

    public void setTemperaturRealFeeleMaxImperial(double temperaturRealFeeleMaxImperial) {
        this.temperaturRealFeeleMaxImperial = temperaturRealFeeleMaxImperial;
    }

    public double getTemperatureRealFeelMinImperial() {
        return temperatureRealFeelMinImperial;
    }

    public void setTemperatureRealFeelMinImperial(double temperatureRealFeelMinImperial) {
        this.temperatureRealFeelMinImperial = temperatureRealFeelMinImperial;
    }

    public int getDayIcon() {
        return dayIcon;
    }

    public void setDayIcon(int dayIcon) {
        this.dayIcon = dayIcon;
    }

    public String getDayPhrase() {
        return dayPhrase;
    }

    public void setDayPhrase(String dayPhrase) {
        this.dayPhrase = dayPhrase;
    }

    public double getDayPrecipitationProbability() {
        return dayPrecipitationProbability;
    }

    public void setDayPrecipitationProbability(double dayPrecipitationProbability) {
        this.dayPrecipitationProbability = dayPrecipitationProbability;
    }

    public double getDayRainProbability() {
        return dayRainProbability;
    }

    public void setDayRainProbability(double dayRainProbability) {
        this.dayRainProbability = dayRainProbability;
    }

    public double getDaySnowProbability() {
        return daySnowProbability;
    }

    public void setDaySnowProbability(double daySnowProbability) {
        this.daySnowProbability = daySnowProbability;
    }

    public double getDayIceProbability() {
        return dayIceProbability;
    }

    public void setDayIceProbability(double dayIceProbability) {
        this.dayIceProbability = dayIceProbability;
    }

    public String getDayWindDirection() {
        return dayWindDirection;
    }

    public void setDayWindDirection(String dayWindDirection) {
        this.dayWindDirection = dayWindDirection;
    }

    public double getDayWindSpeedMetric() {
        return dayWindSpeedMetric;
    }

    public void setDayWindSpeedMetric(double dayWindSpeedMetric) {
        this.dayWindSpeedMetric = dayWindSpeedMetric;
    }

    public double getDayWindSpeedImperial() {
        return dayWindSpeedImperial;
    }

    public void setDayWindSpeedImperial(double dayWindSpeedImperial) {
        this.dayWindSpeedImperial = dayWindSpeedImperial;
    }

    public double getDayCloudCover() {
        return dayCloudCover;
    }

    public void setDayCloudCover(double dayCloudCover) {
        this.dayCloudCover = dayCloudCover;
    }

    public int getNightIcon() {
        return nightIcon;
    }

    public void setNightIcon(int nightIcon) {
        this.nightIcon = nightIcon;
    }

    public String getNightPhrase() {
        return nightPhrase;
    }

    public void setNightPhrase(String nightPhrase) {
        this.nightPhrase = nightPhrase;
    }

    public double getNightPrecipitationProbability() {
        return nightPrecipitationProbability;
    }

    public void setNightPrecipitationProbability(double nightPrecipitationProbability) {
        this.nightPrecipitationProbability = nightPrecipitationProbability;
    }

    public double getNightRainProbability() {
        return nightRainProbability;
    }

    public void setNightRainProbability(double nightRainProbability) {
        this.nightRainProbability = nightRainProbability;
    }

    public double getNightSnowProbability() {
        return nightSnowProbability;
    }

    public void setNightSnowProbability(double nightSnowProbability) {
        this.nightSnowProbability = nightSnowProbability;
    }

    public double getNightIceProbability() {
        return nightIceProbability;
    }

    public void setNightIceProbability(double nightIceProbability) {
        this.nightIceProbability = nightIceProbability;
    }

    public String getNightWindDirection() {
        return nightWindDirection;
    }

    public void setNightWindDirection(String nightWindDirection) {
        this.nightWindDirection = nightWindDirection;
    }

    public double getNightWindSpeedMetric() {
        return nightWindSpeedMetric;
    }

    public void setNightWindSpeedMetric(double nightWindSpeedMetric) {
        this.nightWindSpeedMetric = nightWindSpeedMetric;
    }

    public double getNightWindSpeedImperial() {
        return nightWindSpeedImperial;
    }

    public void setNightWindSpeedImperial(double nightWindSpeedImperial) {
        this.nightWindSpeedImperial = nightWindSpeedImperial;
    }

    public double getNightCloudCover() {
        return nightCloudCover;
    }

    public void setNightCloudCover(double nightCloudCover) {
        this.nightCloudCover = nightCloudCover;
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

    public ArrayList<NowGridItem> mapToGridItemsDay(Context context, ITemperatureStrategy temperatureStrategy, IUnitStrategy unitStrategy) {
        ArrayList<NowGridItem> items = new ArrayList<>();
        NowGridItem tempMin = new NowGridItem();
        NowGridItem tempMax = new NowGridItem();
        NowGridItem realFeelMin = new NowGridItem();

        NowGridItem realFeelMax = new NowGridItem();
        NowGridItem cloudCover = new NowGridItem();
        NowGridItem windSpeed = new NowGridItem();


        NowGridItem rain = new NowGridItem();
        NowGridItem snow = new NowGridItem();
        NowGridItem ice = new NowGridItem();

        DecimalFormat format = new DecimalFormat("#.00");

        //Headers
        tempMin.setHeader(context.getString(R.string.temp_min));
        tempMax.setHeader(context.getString(R.string.temp_max));
        realFeelMin.setHeader(context.getString(R.string.feel_min));

        realFeelMax.setHeader(context.getString(R.string.feel_max));
        cloudCover.setHeader(context.getString(R.string.cloud_cover));
        windSpeed.setHeader(context.getString(R.string.wind_speed));

        rain.setHeader(context.getString(R.string.rain));
        snow.setHeader(context.getString(R.string.snow));
        ice.setHeader(context.getString(R.string.ice));

        //Values
        tempMin.setValue(format.format(temperatureStrategy.getTemperatureMin(this)));
        tempMax.setValue(format.format(temperatureStrategy.getTemperatureMax(this)));
        realFeelMin.setValue(format.format(temperatureStrategy.getRealFeelTemperatureMin(this)));

        realFeelMax.setValue(format.format(temperatureStrategy.getRealFeelTemperatureMax(this)));
        cloudCover.setValue(String.valueOf(this.dayCloudCover));
        windSpeed.setValue(format.format(unitStrategy.getWindSpeed(this, true)));

        rain.setValue(String.valueOf(this.dayRainProbability));
        snow.setValue(String.valueOf(this.daySnowProbability));
        ice.setValue(String.valueOf(this.dayIceProbability));

        //Units
        tempMin.setUnit(temperatureStrategy.getUnit());
        tempMax.setUnit(temperatureStrategy.getUnit());
        realFeelMin.setUnit(temperatureStrategy.getUnit());

        realFeelMax.setUnit(temperatureStrategy.getUnit());
        cloudCover.setUnit(context.getString(R.string.percent));
        windSpeed.setUnit(unitStrategy.getWindSpeedUnit());

        rain.setUnit(context.getString(R.string.percent));
        snow.setUnit(context.getString(R.string.percent));
        ice.setUnit(context.getString(R.string.percent));

        items.add(tempMin);
        items.add(tempMax);
        items.add(realFeelMin);

        items.add(realFeelMax);
        items.add(cloudCover);
        items.add(windSpeed);

        items.add(rain);
        items.add(snow);
        items.add(ice);

        return items;
    }

    public ArrayList<NowGridItem> mapToGridItemsNight(Context context, ITemperatureStrategy temperatureStrategy, IUnitStrategy unitStrategy) {
        ArrayList<NowGridItem> items = new ArrayList<>();
        NowGridItem tempMin = new NowGridItem();
        NowGridItem tempMax = new NowGridItem();
        NowGridItem realFeelMin = new NowGridItem();

        NowGridItem realFeelMax = new NowGridItem();
        NowGridItem cloudCover = new NowGridItem();
        NowGridItem windSpeed = new NowGridItem();


        NowGridItem rain = new NowGridItem();
        NowGridItem snow = new NowGridItem();
        NowGridItem ice = new NowGridItem();

        DecimalFormat format = new DecimalFormat("#.00");

        //Headers
        tempMin.setHeader(context.getString(R.string.temp_min));
        tempMax.setHeader(context.getString(R.string.temp_max));
        realFeelMin.setHeader(context.getString(R.string.feel_min));

        realFeelMax.setHeader(context.getString(R.string.feel_max));
        cloudCover.setHeader(context.getString(R.string.cloud_cover));
        windSpeed.setHeader(context.getString(R.string.wind_speed));

        rain.setHeader(context.getString(R.string.rain));
        snow.setHeader(context.getString(R.string.snow));
        ice.setHeader(context.getString(R.string.ice));

        //Values
        tempMin.setValue(format.format(temperatureStrategy.getTemperatureMin(this)));
        tempMax.setValue(format.format(temperatureStrategy.getTemperatureMax(this)));
        realFeelMin.setValue(format.format(temperatureStrategy.getRealFeelTemperatureMin(this)));

        realFeelMax.setValue(format.format(temperatureStrategy.getRealFeelTemperatureMax(this)));
        cloudCover.setValue(String.valueOf(this.nightCloudCover));
        windSpeed.setValue(format.format(unitStrategy.getWindSpeed(this, false)));

        rain.setValue(String.valueOf(this.nightRainProbability));
        snow.setValue(String.valueOf(this.nightSnowProbability));
        ice.setValue(String.valueOf(this.nightIceProbability));

        //Units
        tempMin.setUnit(temperatureStrategy.getUnit());
        tempMax.setUnit(temperatureStrategy.getUnit());
        realFeelMin.setUnit(temperatureStrategy.getUnit());

        realFeelMax.setUnit(temperatureStrategy.getUnit());
        cloudCover.setUnit(context.getString(R.string.percent));
        windSpeed.setUnit(unitStrategy.getWindSpeedUnit());

        rain.setUnit(context.getString(R.string.percent));
        snow.setUnit(context.getString(R.string.percent));
        ice.setUnit(context.getString(R.string.percent));

        items.add(tempMin);
        items.add(tempMax);
        items.add(realFeelMin);

        items.add(realFeelMax);
        items.add(cloudCover);
        items.add(windSpeed);

        items.add(rain);
        items.add(snow);
        items.add(ice);

        return items;
    }

    public Drawable getIconDrawable(Context context, boolean isDay) {
        Resources resources = context.getResources();
        int id = resources.getIdentifier("ic_" + (isDay ? this.dayIcon : this.nightIcon), "drawable", context.getPackageName());
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
}
