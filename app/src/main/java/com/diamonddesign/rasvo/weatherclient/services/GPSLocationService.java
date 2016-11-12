package com.diamonddesign.rasvo.weatherclient.services;

import android.Manifest;
import android.app.Service;
import android.app.usage.UsageEvents;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.diamonddesign.rasvo.weatherclient.managelocation.callback.GpsEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by rasvo on 12.11.2016.
 */

public class GPSLocationService extends Service implements LocationListener {
    private Context context;
    private LocationManager locationManager;
    private Location location;
    private double latitude;
    private double longtitude;
    private boolean canGetLocation = false;

    private final int INTERVAL = 3000;
    private final int DISTANCE = 0;

    public GPSLocationService(Context context) {
        this.context = context;
    }

    @SuppressWarnings("MissingPermission")
    public Location getLocation() {
        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        canGetLocation = isGpsEnabled || isNetworkEnabled;

        if (isGpsEnabled) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, INTERVAL, DISTANCE, this);
            if (locationManager != null) {
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    latitude = location.getLatitude();
                    longtitude = location.getLongitude();
                }
            }
        } else if (isNetworkEnabled) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, INTERVAL, DISTANCE, this);
            if (locationManager != null) {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location != null) {
                    latitude = location.getLatitude();
                    longtitude = location.getLongitude();
                }
            }
        }

        return location;
    }

    @SuppressWarnings("MissingPermission")
    public void stopService() {
        if (locationManager != null) {
            locationManager.removeUpdates(GPSLocationService.this);
            canGetLocation = false;
            location = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        //EventBus.getDefault().post(new GpsEvent(location));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public double getLatitude() {
        if (locationManager != null) {
            latitude = location.getLatitude();
        }
        return latitude;
    }

    public double getLongtitude() {
        if (locationManager != null) {
            longtitude = location.getLongitude();
        }
        return longtitude;
    }

    public boolean canGetCurrentLocation() {
        return canGetLocation;
    }
}
