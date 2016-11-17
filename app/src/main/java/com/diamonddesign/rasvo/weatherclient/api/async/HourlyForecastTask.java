package com.diamonddesign.rasvo.weatherclient.api.async;

import android.os.AsyncTask;

import com.diamonddesign.rasvo.weatherclient.orm.HourlyCondition;
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
 * Created by rasvo on 17.11.2016.
 */

public class HourlyForecastTask extends AsyncTask<Request, Void, ArrayList<HourlyCondition>> {

    @Override
    protected ArrayList<HourlyCondition> doInBackground(Request... params) {
        Request param = params[0];
        OkHttpClient client = new OkHttpClient();

        Response response = null;
        try {
            response = client.newCall(param).execute();
            JSONArray body = new JSONArray(response.body().string());

            ArrayList<HourlyCondition> res = new ArrayList<>();

            for (int i = 0; i < body.length(); i++) {
                HourlyCondition condition = new HourlyCondition();
                JSONObject item = body.getJSONObject(i);
                JSONObject wind = item.getJSONObject("Wind");

                condition.setDate(item.getString("DateTime"));
                condition.setEpochTime((item.getString("EpochDateTime")));
                condition.setWeatherIcon(item.getInt("WeatherIcon"));
                condition.setPhrase(item.getString("IconPhrase"));
                condition.setTemperatureMetric(item.getJSONObject("Temperature").getDouble("Value"));
                condition.setTemperatureImperial(Helpers.toFahrenheit(item.getJSONObject("Temperature").getDouble("Value")));
                condition.setTemperatureRealFeelMetric(item.getJSONObject("RealFeelTemperature").getDouble("Value"));
                condition.setTemperatureRealFeelImperial(Helpers.toFahrenheit(item.getJSONObject("RealFeelTemperature").getDouble("Value")));
                condition.setRelativeHumidity(item.getDouble("RelativeHumidity"));
                condition.setWindDirection(wind.getJSONObject("Direction").getString("English"));
                condition.setWindSpeedMetric(wind.getJSONObject("Speed").getDouble("Value"));
                condition.setWindSpeedImperial(Helpers.toMilePerHour(wind.getJSONObject("Speed").getDouble("Value")));
                condition.setUvIndex(item.getDouble("UVIndex"));
                condition.setPrecipitationProbability(item.getDouble("PrecipitationProbability"));
                condition.setRainProbability(item.getDouble("RainProbability"));
                condition.setSnowProbability(item.getDouble("SnowProbability"));
                condition.setIceProbability(item.getDouble("IceProbability"));
                condition.setCloudCover(item.getDouble("CloudCover"));

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
