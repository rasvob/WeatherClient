package com.diamonddesign.rasvo.weatherclient.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.diamonddesign.rasvo.weatherclient.R;
import com.diamonddesign.rasvo.weatherclient.api.ForecastApi;
import com.diamonddesign.rasvo.weatherclient.api.async.DailyForecastTask;
import com.diamonddesign.rasvo.weatherclient.enums.TemperatureUnits;
import com.diamonddesign.rasvo.weatherclient.enums.Units;
import com.diamonddesign.rasvo.weatherclient.fragments.adapters.DailyFragmentAdapter;
import com.diamonddesign.rasvo.weatherclient.fragments.adapters.NowFragmentAdapter;
import com.diamonddesign.rasvo.weatherclient.orm.CurrentConditions;
import com.diamonddesign.rasvo.weatherclient.orm.DailyConditions;
import com.diamonddesign.rasvo.weatherclient.orm.Location;
import com.diamonddesign.rasvo.weatherclient.strategy.UnitContext;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;


public class DailyFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private Location currentLocation;
    private UnitContext unitContext = new UnitContext(TemperatureUnits.CELSIUS, Units.METRIC);
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private DailyFragmentAdapter adapter;
    private ArrayList<DailyConditions> conditions = new ArrayList<>();

    public DailyFragment() {

    }

    public static DailyFragment newInstance() {
        DailyFragment fragment = new DailyFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_daily, container, false);

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.dailySwipeRefresh);
        recyclerView = (RecyclerView) view.findViewById(R.id.dailyRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new DailyFragmentAdapter(conditions, getContext(), unitContext);
        recyclerView.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(this);

        if (currentLocation != null) {
            loadData(currentLocation);
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
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

    public void loadData(Location location) {
        currentLocation = location;
        adapter.setUnitContext(unitContext);
        resetData();

        List<DailyConditions> list = DailyConditions.find(DailyConditions.class, "key = ?", currentLocation.getKey());

        if (list.size() > 0) {
            conditions.addAll(list);
        }

        adapter.notifyDataSetChanged();
    }

    public void resetData() {
        conditions.clear();
    }

    @Override
    public void onRefresh() {
        if (currentLocation == null) {
            refreshLayout.setRefreshing(false);
            Toast.makeText(getContext(), getString(R.string.error_occured), Toast.LENGTH_SHORT).show();
            return;
        }

        ForecastApi api = new ForecastApi();
        DailyForecastTask task = new DailyForecastTask() {
            @Override
            protected void onPostExecute(ArrayList<DailyConditions> data) {
                super.onPostExecute(data);
                if (data != null) {
                    SugarRecord.deleteAll(DailyConditions.class, "key = ?", currentLocation.getKey());
                    for (DailyConditions dailyConditions : data) {
                        dailyConditions.setKey(currentLocation.getKey());
                        dailyConditions.save();
                    }

                    resetData();
                    conditions.addAll(data);
                    adapter.notifyDataSetChanged();
                    refreshLayout.setRefreshing(false);
                    return;
                }

                Toast.makeText(getContext(), getString(R.string.error_occured), Toast.LENGTH_SHORT).show();
                refreshLayout.setRefreshing(false);
            }
        };

        task.execute(api.buildDailyForecastRequst(currentLocation.getKey()));
    }
}
