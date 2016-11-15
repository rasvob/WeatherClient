package com.diamonddesign.rasvo.weatherclient.services;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;

/**
 * Created by rasvo on 12.11.2016.
 */

public class Helpers {
    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            Runtime runtime = Runtime.getRuntime();
            try {
                Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
                int exitValue = ipProcess.waitFor();
                if (exitValue == 0) {
                    return true;
                }
            }
            catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static double toFahrenheit(double celsius) {
        return celsius * (9.0/5.0) + 32;
    }

    public static double toMilePerHour(double kmh) {
        return kmh / 1.609344;
    }
}
