package com.diamonddesign.rasvo.weatherclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.diamonddesign.rasvo.weatherclient.enums.TemperatureUnits;
import com.diamonddesign.rasvo.weatherclient.enums.Units;
import com.diamonddesign.rasvo.weatherclient.fragments.DetailGridFragment;
import com.diamonddesign.rasvo.weatherclient.models.NowGridItem;
import com.diamonddesign.rasvo.weatherclient.orm.HourlyCondition;
import com.diamonddesign.rasvo.weatherclient.orm.Location;
import com.diamonddesign.rasvo.weatherclient.sharedprefs.SharedPrefsWrapper;
import com.diamonddesign.rasvo.weatherclient.strategy.UnitContext;

import java.util.ArrayList;
import java.util.List;

public class HourlyForecastDetailActivity extends AppCompatActivity {
    private ArrayList<NowGridItem> data = new ArrayList<>();
    private Location currentLocation;
    private UnitContext unitContext = new UnitContext(TemperatureUnits.CELSIUS, Units.METRIC);
    private ImageView headerIcon;
    private TextView headerText;
    private TextView headerDate;
    private DetailGridFragment detailGridFragment = new DetailGridFragment();
    private FrameLayout gridFrame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly_forecast_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarHourlyDetail);
        setSupportActionBar(toolbar);

        headerIcon = (ImageView) findViewById(R.id.hourly_detail_header_icon);
        headerText = (TextView) findViewById(R.id.hourly_detail_header_text);
        headerDate = (TextView) findViewById(R.id.hourly_detail_header_date);
        gridFrame = (FrameLayout) findViewById(R.id.hourly_detail_grid_fragment);

        SharedPrefsWrapper shWrapper = new SharedPrefsWrapper(this);
        unitContext.setUnitStrategy(shWrapper.getOtherUnits());
        unitContext.setTemperatureStrategy(shWrapper.getTemperatureUnits());

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            long id = extras.getLong("ID");
            HourlyCondition condition = HourlyCondition.findById(HourlyCondition.class, id);
            if (condition != null) {
                List<Location> locations = Location.find(Location.class, "key = ?", condition.getKey());
                if (locations.size() > 0 ){
                    currentLocation = locations.get(0);
                    getSupportActionBar().setTitle(currentLocation.getLocationName());
                }

                headerIcon.setImageDrawable(condition.getIconDrawable(this));
                headerText.setText(condition.getPhrase());
                headerDate.setText(condition.getFormattedDate(this));

                ArrayList<NowGridItem> nowGridItems = condition.mapToGridItems(this, unitContext.getTemperatureStrategy(), unitContext.getUnitStrategy());
                detailGridFragment.setData(nowGridItems);

                getSupportFragmentManager().beginTransaction().add(R.id.hourly_detail_grid_fragment, detailGridFragment, "DETAIL_HOURLY_FRAGMENT").commit();
            }
        }

    }

}
