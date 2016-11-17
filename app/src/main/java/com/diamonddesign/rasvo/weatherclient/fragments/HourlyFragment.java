package com.diamonddesign.rasvo.weatherclient.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diamonddesign.rasvo.weatherclient.R;
import com.diamonddesign.rasvo.weatherclient.api.ForecastApi;
import com.diamonddesign.rasvo.weatherclient.api.async.HourlyForecastTask;
import com.diamonddesign.rasvo.weatherclient.enums.TemperatureUnits;
import com.diamonddesign.rasvo.weatherclient.enums.Units;
import com.diamonddesign.rasvo.weatherclient.orm.DailyConditions;
import com.diamonddesign.rasvo.weatherclient.orm.HourlyCondition;
import com.diamonddesign.rasvo.weatherclient.orm.Location;
import com.diamonddesign.rasvo.weatherclient.strategy.UnitContext;

import java.util.ArrayList;


public class HourlyFragment extends Fragment {
    private Location currentLocation;
    private UnitContext unitContext = new UnitContext(TemperatureUnits.CELSIUS, Units.METRIC);
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private ArrayList<HourlyCondition> conditions = new ArrayList<>();

    public HourlyFragment() {

    }

    public static HourlyFragment newInstance() {
        HourlyFragment fragment = new HourlyFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ForecastApi api = new ForecastApi();

        HourlyForecastTask task = new HourlyForecastTask() {
            @Override
            protected void onPostExecute(ArrayList<HourlyCondition> hourlyConditions) {
                super.onPostExecute(hourlyConditions);
            }
        };
        task.execute(api.buildHourlyForecastRequst("256499"));

        View view = inflater.inflate(R.layout.fragment_hourly, container, false);

        return view;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public UnitContext getUnitContext() {
        return unitContext;
    }

    public void setUnitContext(UnitContext unitContext) {
        this.unitContext = unitContext;
    }
}
