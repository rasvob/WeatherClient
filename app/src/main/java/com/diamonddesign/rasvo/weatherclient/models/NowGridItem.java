package com.diamonddesign.rasvo.weatherclient.models;

/**
 * Created by rasvo on 15.11.2016.
 */

public class NowGridItem {
    private String header;
    private String value;
    private String unit;

    public NowGridItem() {

    }

    public NowGridItem(String header, String value, String unit) {
        this.header = header;
        this.value = value;
        this.unit = unit;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
