package com.diamonddesign.rasvo.weatherclient.fragments;


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
    private UnitContext unitContext;
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

        adapter = new DailyFragmentAdapter(conditions, getContext(), unitContext);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (currentLocation != null) {
            loadData(currentLocation);
        }
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
        loadData(currentLocation);
    }

    public UnitContext getUnitContext() {
        return unitContext;
    }

    public void setUnitContext(UnitContext unitContext) {
        this.unitContext = unitContext;
        adapter.setUnitContext(unitContext);
    }

    public void loadData(Location location) {
        currentLocation = location;
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
