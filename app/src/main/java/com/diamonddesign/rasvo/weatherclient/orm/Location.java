package com.diamonddesign.rasvo.weatherclient.orm;

import com.google.common.base.Strings;
import com.orm.SugarRecord;

/**
 * Created by rasvo on 11.11.2016.
 */

public class Location extends SugarRecord {
    String key;
    String localizedName;
    String countryName;
    String countryId;
    boolean isFavourite = false;

    public Location() {

    }

    public Location(String key, String localizedName, String countryName, String countryID) {
        this.key = key;
        this.localizedName = localizedName;
        this.countryName = countryName;
        this.countryId = countryID;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLocalizedName() {
        return localizedName;
    }

    public void setLocalizedName(String localizedName) {
        this.localizedName = localizedName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryID() {
        return countryId;
    }

    public void setCountryID(String countryID) {
        this.countryId = countryID;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public String getLocationName() {
        return localizedName + ", " + countryId;
    }
}
