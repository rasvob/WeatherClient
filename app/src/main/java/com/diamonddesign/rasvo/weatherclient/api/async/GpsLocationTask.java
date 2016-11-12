package com.diamonddesign.rasvo.weatherclient.api.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.diamonddesign.rasvo.weatherclient.orm.Location;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by rasvo on 12.11.2016.
 */

public class GpsLocationTask extends AsyncTask<Request, Void, Location> {
    private Context context;
    private ProgressDialog dialog;

    public GpsLocationTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(context);
        dialog.isIndeterminate();
        dialog.setCancelable(false);
        dialog.setTitle("Please wait");
        dialog.setMessage("Getting current location info...");
        dialog.show();
    }

    @Override
    protected Location doInBackground(Request... params) {
        OkHttpClient client = new OkHttpClient();

        try {
            Response response = client.newCall(params[0]).execute();
            JSONObject jLoc = new JSONObject(response.body().string());

            Location location = new Location();

            location.setKey(jLoc.getString("Key"));
            location.setLocalizedName(jLoc.getString("LocalizedName"));
            JSONObject country = jLoc.getJSONObject("Country");
            location.setCountryID(country.getString("ID"));
            location.setCountryName(country.getString("LocalizedName"));

            return location;

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Toast.makeText(context, "Network error, please try again", Toast.LENGTH_SHORT).show();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Location location) {
        super.onPostExecute(location);

        if (dialog != null) {
            dialog.dismiss();
        }
    }
}
