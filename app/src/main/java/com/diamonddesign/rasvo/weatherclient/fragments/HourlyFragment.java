package com.diamonddesign.rasvo.weatherclient.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.diamonddesign.rasvo.weatherclient.HourlyForecastDetailActivity;
import com.diamonddesign.rasvo.weatherclient.R;
import com.diamonddesign.rasvo.weatherclient.api.ForecastApi;
import com.diamonddesign.rasvo.weatherclient.api.async.HourlyForecastTask;
import com.diamonddesign.rasvo.weatherclient.enums.TemperatureUnits;
import com.diamonddesign.rasvo.weatherclient.enums.Units;
import com.diamonddesign.rasvo.weatherclient.fragments.adapters.HourlyFragmentAdapter;
import com.diamonddesign.rasvo.weatherclient.fragments.adapters.IRecyclerViewRowClicked;
import com.diamonddesign.rasvo.weatherclient.orm.HourlyCondition;
import com.diamonddesign.rasvo.weatherclient.orm.Location;
import com.diamonddesign.rasvo.weatherclient.strategy.UnitContext;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;


public class HourlyFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, IRecyclerViewRowClicked {
    private Location currentLocation;
    private UnitContext unitContext = new UnitContext(TemperatureUnits.CELSIUS, Units.METRIC);
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private ArrayList<HourlyCondition> conditions = new ArrayList<>();
    private HourlyFragmentAdapter adapter;

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
        View view = inflater.inflate(R.layout.fragment_hourly, container, false);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.hourlySwipeRefresh);
        recyclerView = (RecyclerView) view.findViewById(R.id.hourlyRecyclerView);
        adapter = new HourlyFragmentAdapter(conditions, getContext(), unitContext, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        refreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorPrimary), ContextCompat.getColor(getContext(), R.color.colorAccent),ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
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

    public void loadData(Location location) {
        this.currentLocation = location;
        adapter.setUnitContext(unitContext);
        resetData();

        List<HourlyCondition> list = HourlyCondition.find(HourlyCondition.class, "key = ?", currentLocation.getKey());
        if (list.size() > 0 ) {
            conditions.addAll(list);
        }

        adapter.notifyDataSetChanged();
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

    @Override
    public void onRefresh() {
        if (currentLocation == null) {
            refreshLayout.setRefreshing(false);
            Toast.makeText(getContext(), getString(R.string.error_occured), Toast.LENGTH_SHORT).show();
            return;
        }

        ForecastApi api = new ForecastApi();
        HourlyForecastTask task = new HourlyForecastTask() {
            @Override
            protected void onPostExecute(ArrayList<HourlyCondition> hourlyConditions) {
                super.onPostExecute(hourlyConditions);
                if (hourlyConditions != null) {
                    SugarRecord.deleteAll(HourlyCondition.class, "key = ?", currentLocation.getKey());
                    for (HourlyCondition condition : hourlyConditions) {
                        condition.setKey(currentLocation.getKey());
                        condition.save();
                    }
                    resetData();
                    conditions.addAll(hourlyConditions);
                    adapter.notifyDataSetChanged();
                    refreshLayout.setRefreshing(false);
                    return;
                }

                Toast.makeText(getContext(), getString(R.string.error_occured), Toast.LENGTH_SHORT).show();
                refreshLayout.setRefreshing(false);
            }
        };
        task.execute(api.buildHourlyForecastRequst(currentLocation.getKey()));
    }

    public void resetData() {
        conditions.clear();
    }

    @Override
    public void onRowClick(int position) {
        Intent detail = new Intent(getActivity(), HourlyForecastDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong("ID", conditions.get(position).getId());
        detail.putExtras(bundle);
        startActivity(detail);
    }
}
