package com.diamonddesign.rasvo.weatherclient.api.async;

import android.os.AsyncTask;

import com.diamonddesign.rasvo.weatherclient.orm.DailyConditions;
import com.diamonddesign.rasvo.weatherclient.services.Helpers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by rasvo on 15.11.2016.
 */

public class DailyForecastTask extends AsyncTask<Request, Void, ArrayList<DailyConditions>> {
    @Override
    protected ArrayList<DailyConditions> doInBackground(Request... params) {
        Request param = params[0];
        OkHttpClient client = new OkHttpClient();

        try {
            Response response = client.newCall(param).execute();

            JSONObject body = new JSONObject(response.body().string());
            JSONArray arr = body.getJSONArray("DailyForecasts");

            ArrayList<DailyConditions> res = new ArrayList<>();

            for (int i = 0; i < arr.length(); i++) {
                JSONObject forecast = arr.getJSONObject(i);
                JSONObject day = forecast.getJSONObject("Day");
                JSONObject night = forecast.getJSONObject("Night");
                JSONObject sun = forecast.getJSONObject("Sun");
                JSONObject temperature = forecast.getJSONObject("Temperature");
                JSONObject realFeelTemperature = forecast.getJSONObject("RealFeelTemperature");

                DailyConditions condition = new DailyConditions();
                condition.setEpochTime(forecast.getString("EpochDate"));
                condition.setDate(forecast.getString("Date"));
                condition.setSunSet(sun.getString("Set"));
                condition.setSunRise(sun.getString("Rise"));

                condition.setTemperatureMaxMetric(temperature.getJSONObject("Maximum").getDouble("Value"));
                condition.setTemperatureMinMetric(temperature.getJSONObject("Minimum").getDouble("Value"));
                condition.setTemperaturRealFeeleMaxMetric(realFeelTemperature.getJSONObject("Maximum").getDouble("Value"));
                condition.setTemperatureRealFeelMinMetric(realFeelTemperature.getJSONObject("Minimum").getDouble("Value"));

                condition.setTemperatureMinImperial(Helpers.toFahrenheit(condition.getTemperatureMinMetric()));
                condition.setTemperatureMaxImperial(Helpers.toFahrenheit(condition.getTemperatureMaxImperial()));
                condition.setTemperatureRealFeelMinImperial(Helpers.toFahrenheit(condition.getTemperatureRealFeelMinImperial()));
                condition.setTemperaturRealFeeleMaxImperial(Helpers.toFahrenheit(condition.getTemperaturRealFeeleMaxImperial()));

                condition.setDayIcon(day.getInt("Icon"));
                condition.setDayPhrase(day.getString("ShortPhrase"));
                condition.setDayPrecipitationProbability(day.getDouble("PrecipitationProbability"));
                condition.setDayRainProbability(day.getDouble("RainProbability"));
                condition.setDaySnowProbability(day.getDouble("SnowProbability"));
                condition.setDayIceProbability(day.getDouble("IceProbability"));
                condition.setDayWindDirection(day.getJSONObject("Wind").getJSONObject("Direction").getString("English"));
                condition.setDayWindSpeedMetric(day.getJSONObject("Wind").getJSONObject("Speed").getDouble("Value"));
                condition.setDayWindSpeedImperial(Helpers.toMilePerHour(condition.getDayWindSpeedMetric()));
                condition.setDayCloudCover(day.getDouble("CloudCover"));

                condition.setNightIcon(night.getInt("Icon"));
                condition.setNightPhrase(night.getString("ShortPhrase"));
                condition.setNightPrecipitationProbability(night.getDouble("PrecipitationProbability"));
                condition.setNightRainProbability(night.getDouble("RainProbability"));
                condition.setNightSnowProbability(night.getDouble("SnowProbability"));
                condition.setNightIceProbability(night.getDouble("IceProbability"));
                condition.setNightWindDirection(night.getJSONObject("Wind").getJSONObject("Direction").getString("English"));
                condition.setNightWindSpeedMetric(night.getJSONObject("Wind").getJSONObject("Speed").getDouble("Value"));
                condition.setNightWindSpeedImperial(Helpers.toMilePerHour(condition.getNightWindSpeedMetric()));
                condition.setNightCloudCover(night.getDouble("CloudCover"));

                res.add(condition);
            }

            return res;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
