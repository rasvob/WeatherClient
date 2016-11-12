package com.diamonddesign.rasvo.weatherclient.managelocation.callback;

import com.diamonddesign.rasvo.weatherclient.enums.EntryOperation;
import com.diamonddesign.rasvo.weatherclient.orm.Location;

/**
 * Created by rasvo on 11.11.2016.
 */

public interface OnOperationPerformed {
    void onOperation(Location location, EntryOperation operation);
}
