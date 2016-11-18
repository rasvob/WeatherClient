package com.diamonddesign.rasvo.weatherclient.fragments;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.diamonddesign.rasvo.weatherclient.R;
import com.diamonddesign.rasvo.weatherclient.models.NowGridItem;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DailyDetailFragment extends Fragment {
    private ImageView headerIcon;
    private TextView headerText;
    private TextView headerDate;
    private String[] headerData;
    private Drawable icon;
    private Drawable headerBackground;
    private ArrayList<NowGridItem> data;
    private DetailGridFragment detailGridFragment = new DetailGridFragment();
    private LinearLayout headerWrapper;
    private boolean isDay = true;

    int dayFrame = R.id.day_daily_fragment_detail_grid_fragment;
    int nightFrame = R.id.night_daily_fragment_detail_grid_fragment;
    int dayLayout = R.layout.fragment_daily_detail;
    int nightLayout = R.layout.fragment_daily_night_detail;

    public DailyDetailFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(isDay ? dayLayout : nightLayout, container, false);
        headerIcon = (ImageView) view.findViewById(R.id.daily_fragment_detail_header_icon);
        headerText = (TextView) view.findViewById(R.id.daily_fragment_detail_header_text);
        headerDate = (TextView) view.findViewById(R.id.daily_fragment_detail_header_date);
        headerWrapper = (LinearLayout) view.findViewById(R.id.daily_fragment_detail_linear_header);

        headerIcon.setImageDrawable(icon);
        headerText.setText(headerData[0]);
        headerDate.setText(headerData[1]);
        headerWrapper.setBackground(headerBackground);

        detailGridFragment.setData(data);

        getActivity().getSupportFragmentManager().beginTransaction().add(isDay ? dayFrame : nightFrame, detailGridFragment).commit();
        return view;
    }

    public void setHeaderData(String[] headerData, Drawable icon) {
        this.headerData = headerData;
        this.icon = icon;
    }

    public void setData(ArrayList<NowGridItem> data) {
        this.data = data;
    }

    public void setHeaderBackground(Drawable headerBackground) {
        this.headerBackground = headerBackground;
    }

    public void setDay(boolean day) {
        isDay = day;
    }
}
