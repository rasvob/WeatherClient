package com.diamonddesign.rasvo.weatherclient.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.diamonddesign.rasvo.weatherclient.orm.DailyConditions;
import com.diamonddesign.rasvo.weatherclient.orm.Location;
import com.diamonddesign.rasvo.weatherclient.strategy.UnitContext;

import java.util.ArrayList;


public class DailyFragment extends Fragment {
    private Location currentLocation;
    private UnitContext unitContext;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private DailyFragmentAdapter adapter;

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

        return view;
    }

}
