package com.diamonddesign.rasvo.weatherclient.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diamonddesign.rasvo.weatherclient.R;


public class HourlyFragment extends Fragment {
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
        return inflater.inflate(R.layout.fragment_hourly, container, false);
    }

}
