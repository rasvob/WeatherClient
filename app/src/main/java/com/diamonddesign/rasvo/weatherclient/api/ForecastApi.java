package com.diamonddesign.rasvo.weatherclient.api;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by rasvo on 15.11.2016.
 */

public class ForecastApi {
    public Request buildDailyForecastRequst(String query) {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host(ApiContants.BASE_URL)
                .addPathSegment("forecasts")
                .addPathSegment("v1")
                .addPathSegment("daily")
                .addPathSegment("5day")
                .addPathSegment(query)
                .addQueryParameter("apikey", ApiContants.API_KEY)
                .addQueryParameter("details", "true")
                .addQueryParameter("metric", "true")
                .build();

        return new Request.Builder().url(url).build();
    }

    public Request buildHourlyForecastRequst(String query) {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host(ApiContants.BASE_URL)
                .addPathSegment("forecasts")
                .addPathSegment("v1")
                .addPathSegment("hourly")
                .addPathSegment("12hour")
                .addPathSegment(query)
                .addQueryParameter("apikey", ApiContants.API_KEY)
                .addQueryParameter("details", "true")
                .addQueryParameter("metric", "true")
                .build();

        return new Request.Builder().url(url).build();
    }
}
