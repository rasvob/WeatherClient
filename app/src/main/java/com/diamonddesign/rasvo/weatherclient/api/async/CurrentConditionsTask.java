package com.diamonddesign.rasvo.weatherclient.api.async;

import android.os.AsyncTask;

import com.diamonddesign.rasvo.weatherclient.orm.CurrentConditions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;

import javax.xml.datatype.DatatypeConstants;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by rasvo on 14.11.2016.
 */

public class CurrentConditionsTask extends AsyncTask<Request, Void, CurrentConditions> {

    @Override
    protected CurrentConditions doInBackground(Request... params) {
        Request param = params[0];

        OkHttpClient client = new OkHttpClient();
        try {
            Response response = client.newCall(param).execute();
            JSONArray arr = new JSONArray(response.body().string());
            JSONObject body = arr.getJSONObject(0);

            CurrentConditions conditions = new CurrentConditions();
            conditions.setDate(body.getString("LocalObservationDateTime"));
            conditions.setWeatherText(body.getString("WeatherText"));
            conditions.setEpochTime(body.getString("EpochTime"));
            conditions.setWeatherIcon(body.getInt("WeatherIcon"));
            conditions.setDayTime(body.getBoolean("IsDayTime"));

            JSONObject tempObj = body.getJSONObject("Temperature");
            JSONObject tempRFObj = body.getJSONObject("RealFeelTemperature");

            conditions.setTemperatureMetric(tempObj.getJSONObject("Metric").getDouble("Value"));
            conditions.setTemperatureImperial(tempObj.getJSONObject("Imperial").getDouble("Value"));
            conditions.setTemperatureRealFeelMetric(tempRFObj.getJSONObject("Metric").getDouble("Value"));
            conditions.setTemperatureRealFeelImperial(tempRFObj.getJSONObject("Imperial").getDouble("Value"));

            conditions.setRelativeHumidity(body.getDouble("RelativeHumidity"));

            JSONObject wind = body.getJSONObject("Wind");
            conditions.setWindDirection(wind.getJSONObject("Direction").getString("English"));
            conditions.setWindSpeedMetric(wind.getJSONObject("Speed").getJSONObject("Metric").getDouble("Value"));
            conditions.setWindSpeedImperial(wind.getJSONObject("Speed").getJSONObject("Imperial").getDouble("Value"));

            conditions.setUvIndex(body.getDouble("UVIndex"));

            JSONObject visibility = body.getJSONObject("Visibility");
            conditions.setVisibilityMetric(visibility.getJSONObject("Metric").getDouble("Value"));
            conditions.setVisibilityImperial(visibility.getJSONObject("Imperial").getDouble("Value"));

            conditions.setCloudCover(body.getDouble("CloudCover"));

            JSONObject pressure = body.getJSONObject("Pressure");
            conditions.setPressureMetric(pressure.getJSONObject("Metric").getDouble("Value"));
            conditions.setPressureImperial(pressure.getJSONObject("Imperial").getDouble("Value"));

            return conditions;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
