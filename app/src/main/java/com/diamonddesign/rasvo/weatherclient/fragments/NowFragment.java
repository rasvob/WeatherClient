package com.diamonddesign.rasvo.weatherclient.fragments;


import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.diamonddesign.rasvo.weatherclient.MainActivity;
import com.diamonddesign.rasvo.weatherclient.R;
import com.diamonddesign.rasvo.weatherclient.api.CurrentConditionsApi;
import com.diamonddesign.rasvo.weatherclient.api.async.CurrentConditionsTask;
import com.diamonddesign.rasvo.weatherclient.enums.TemperatureUnits;
import com.diamonddesign.rasvo.weatherclient.enums.Units;
import com.diamonddesign.rasvo.weatherclient.fragments.adapters.NowFragmentAdapter;
import com.diamonddesign.rasvo.weatherclient.models.NowGridItem;
import com.diamonddesign.rasvo.weatherclient.orm.CurrentConditions;
import com.diamonddesign.rasvo.weatherclient.orm.Location;
import com.diamonddesign.rasvo.weatherclient.strategy.UnitContext;
import com.orm.SugarRecord;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class NowFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private NowFragmentAdapter adapter;
    private ArrayList<NowGridItem> data = new ArrayList<>();
    private Location currentLocation;
    private UnitContext unitContext = new UnitContext(TemperatureUnits.CELSIUS, Units.METRIC);
    private ImageView headerIcon;
    private TextView headerText;
    private TextView headerDate;
    private ICurrentConditionRefreshCallback currentConditionRefreshCallback;

    public NowFragment() {

    }

    public static NowFragment newInstance() {
        return new NowFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_now, container, false);

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.nowSwipeRefresh);
        recyclerView = (RecyclerView) view.findViewById(R.id.nowRecyclerView);

        headerIcon = (ImageView) view.findViewById(R.id.now_header_icon);
        headerText = (TextView) view.findViewById(R.id.now_header_text);
        headerDate = (TextView) view.findViewById(R.id.now_header_date);

        adapter = new NowFragmentAdapter(data);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        resetData();

        if (currentLocation != null) {
            loadData(currentLocation);
        }

        refreshLayout.setOnRefreshListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void loadData(final Location location) {
        currentLocation = location;
        resetData();

        List<CurrentConditions> currentConditions = CurrentConditions.find(CurrentConditions.class, "key = ?", currentLocation.getKey());
        if (currentConditions.size() > 0) {
            CurrentConditions conditions = currentConditions.get(0);
            ArrayList<NowGridItem> nowGridItems = conditions.mapToGridItems(getContext(), unitContext.getTemperatureStrategy(), unitContext.getUnitStrategy());

            headerText.setText(conditions.getWeatherText());
            headerDate.setText(conditions.getFormattedDate(getContext()));
            setHeaderIcon(conditions.getWeatherIcon());

            data.addAll(nowGridItems);
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        if (currentLocation == null) {
            refreshLayout.setRefreshing(false);
            Toast.makeText(getContext(), getString(R.string.error_occured), Toast.LENGTH_SHORT).show();
            return;
        }

        CurrentConditionsApi api = new CurrentConditionsApi();
        CurrentConditionsTask task = new CurrentConditionsTask() {
            @Override
            protected void onPostExecute(CurrentConditions conditions) {
                super.onPostExecute(conditions);
                if (conditions != null) {
                    conditions.setKey(currentLocation.getKey());
                    SugarRecord.deleteAll(CurrentConditions.class, "key = ?", currentLocation.getKey());
                    conditions.save();

                    resetData();
                    ArrayList<NowGridItem> nowGridItems = conditions.mapToGridItems(getContext(), unitContext.getTemperatureStrategy(), unitContext.getUnitStrategy());
                    data.addAll(nowGridItems);

                    headerText.setText(conditions.getWeatherText());
                    headerDate.setText(conditions.getFormattedDate(getContext()));
                    setHeaderIcon(conditions.getWeatherIcon());

                    adapter.notifyDataSetChanged();

                    if (currentConditionRefreshCallback != null) {
                        currentConditionRefreshCallback.refreshCurrentHeader(currentLocation);
                    }

                    refreshLayout.setRefreshing(false);
                    return;
                }
                Toast.makeText(getContext(), getString(R.string.error_occured), Toast.LENGTH_SHORT).show();
                refreshLayout.setRefreshing(false);
            }
        };
        task.execute(api.buildCurrentConditionsRequst(currentLocation.getKey()));
    }

    public UnitContext getUnitContext() {
        return unitContext;
    }

    public void setUnitContext(UnitContext unitContext) {
        this.unitContext = unitContext;
    }

    public ICurrentConditionRefreshCallback getCurrentConditionRefreshCallback() {
        return currentConditionRefreshCallback;
    }

    public void setCurrentConditionRefreshCallback(ICurrentConditionRefreshCallback currentConditionRefreshCallback) {
        this.currentConditionRefreshCallback = currentConditionRefreshCallback;
    }

    public void resetData() {
        data.clear();
        headerText.setText(getString(R.string.no_info));
        headerDate.setText("(" + getString(R.string.no_info) + ")");
        Resources resources = getContext().getResources();
        int id = resources.getIdentifier("ic_" + 1, "drawable", getContext().getPackageName());
        headerIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), id));
    }

    public void setHeaderIcon(int iconCode) {
        Resources resources = getContext().getResources();
        int id = resources.getIdentifier("ic_" + iconCode, "drawable", getContext().getPackageName());
        headerIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), id));
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public interface ICurrentConditionRefreshCallback {
        void refreshCurrentHeader(Location location);
    }
}
