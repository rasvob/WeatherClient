package com.diamonddesign.rasvo.weatherclient.api;

import android.util.Log;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import static android.content.ContentValues.TAG;

/**
 * Created by rasvo on 11.11.2016.
 */

public class LocationApi {
    public Request buildAutocompleteRequest(String query) {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host(ApiContants.BASE_URL)
                .addPathSegment("locations")
                .addPathSegment("v1")
                .addPathSegment("cities")
                .addPathSegment("autocomplete")
                .addQueryParameter("apikey", ApiContants.API_KEY)
                .addQueryParameter("q", query)
                .build();

        Log.d(TAG, "buildAutocompleteRequest: " + url);

        return new Request.Builder().url(url).build();
    }
}
