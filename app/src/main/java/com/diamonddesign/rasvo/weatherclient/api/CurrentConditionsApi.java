package com.diamonddesign.rasvo.weatherclient.api;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by rasvo on 14.11.2016.
 */

public class CurrentConditionsApi {
    public Request buildCurrentConditionsRequst(String query) {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host(ApiContants.BASE_URL)
                .addPathSegment("currentconditions")
                .addPathSegment("v1")
                .addPathSegment(query)
                .addQueryParameter("apikey", ApiContants.API_KEY)
                .addQueryParameter("details", "true")
                .build();

        return new Request.Builder().url(url).build();
    }
}
