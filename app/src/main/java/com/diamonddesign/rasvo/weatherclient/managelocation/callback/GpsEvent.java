package com.diamonddesign.rasvo.weatherclient.managelocation.callback;

import android.location.Location;

/**
 * Created by rasvo on 12.11.2016.
 */

public class GpsEvent {
    private Location location;

    public GpsEvent() {
    }

    public GpsEvent(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
