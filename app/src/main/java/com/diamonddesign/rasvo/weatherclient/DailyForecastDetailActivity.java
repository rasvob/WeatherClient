package com.diamonddesign.rasvo.weatherclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.diamonddesign.rasvo.weatherclient.fragments.DailyDetailFragment;
import com.diamonddesign.rasvo.weatherclient.fragments.adapters.ViewPagerAdapter;
import com.diamonddesign.rasvo.weatherclient.orm.DailyConditions;
import com.diamonddesign.rasvo.weatherclient.orm.Location;
import com.diamonddesign.rasvo.weatherclient.sharedprefs.SharedPrefsWrapper;
import com.diamonddesign.rasvo.weatherclient.strategy.UnitContext;

import java.util.List;

public class DailyForecastDetailActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private UnitContext unitContext;
    private Location currentLocation;
    private DailyConditions conditions;
    private DailyDetailFragment dayFragment = new DailyDetailFragment();
    private DailyDetailFragment nightFragment = new DailyDetailFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast_detail_actvity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDailyDetail);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout) findViewById(R.id.tabsDailyDetail);
        viewPager = (ViewPager) findViewById(R.id.viewpagerDaily);

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

        SharedPrefsWrapper sharedPrefsWrapper = new SharedPrefsWrapper(this);
        unitContext = new UnitContext(sharedPrefsWrapper.getTemperatureUnits(), sharedPrefsWrapper.getOtherUnits());

        Intent intent = getIntent();

        if (intent != null) {
            Bundle extras = intent.getExtras();
            long id = extras.getLong("ID");
            conditions = DailyConditions.findById(DailyConditions.class, id);

            if (conditions != null) {
                List<Location> locations = Location.find(Location.class, "key = ?", conditions.getKey());
                if (locations.size() > 0) {
                    currentLocation = locations.get(0);
                    getSupportActionBar().setTitle(currentLocation.getLocationName());

                    dayFragment.setData(conditions.mapToGridItemsDay(this, unitContext.getTemperatureStrategy(), unitContext.getUnitStrategy()));
                    dayFragment.setHeaderData(new String[]{ conditions.getDayPhrase(), conditions.getFormattedDate(this)}, conditions.getIconDrawable(this, true));
                    dayFragment.setHeaderBackground(getDrawable(R.mipmap.fw_day));
                    dayFragment.setDay(true);

                    nightFragment.setData(conditions.mapToGridItemsNight(this, unitContext.getTemperatureStrategy(), unitContext.getUnitStrategy()));
                    nightFragment.setHeaderData(new String[]{ conditions.getNightPhrase(), conditions.getFormattedDate(this)}, conditions.getIconDrawable(this, false));
                    nightFragment.setHeaderBackground(getDrawable(R.mipmap.fw_night));
                    nightFragment.setDay(false);

                    setupViewPager();
                    tabLayout.setupWithViewPager(viewPager);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setupViewPager() {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(dayFragment, getString(R.string.day));
        viewPagerAdapter.addFragment(nightFragment, getString(R.string.night));
        viewPager.setAdapter(viewPagerAdapter);
    }

}
