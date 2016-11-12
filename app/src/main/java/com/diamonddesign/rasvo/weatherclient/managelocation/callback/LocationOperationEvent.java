package com.diamonddesign.rasvo.weatherclient.managelocation.callback;

import com.diamonddesign.rasvo.weatherclient.enums.EntryOperation;
import com.diamonddesign.rasvo.weatherclient.orm.Location;

/**
 * Created by rasvo on 11.11.2016.
 */

public class LocationOperationEvent {
    private Location location;
    private int position;
    private EntryOperation operation;

    public LocationOperationEvent() {
    }

    public LocationOperationEvent(Location location, int position, EntryOperation operation) {
        this.location = location;
        this.position = position;
        this.operation = operation;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public EntryOperation getOperation() {
        return operation;
    }

    public void setOperation(EntryOperation operation) {
        this.operation = operation;
    }
}
