package com.diamonddesign.rasvo.weatherclient;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.*;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.diamonddesign.rasvo.weatherclient.enums.TemperatureUnits;
import com.diamonddesign.rasvo.weatherclient.enums.Units;
import com.diamonddesign.rasvo.weatherclient.fragments.DailyFragment;
import com.diamonddesign.rasvo.weatherclient.fragments.HourlyFragment;
import com.diamonddesign.rasvo.weatherclient.fragments.NowFragment;
import com.diamonddesign.rasvo.weatherclient.fragments.adapters.ViewPagerAdapter;
import com.diamonddesign.rasvo.weatherclient.models.NowGridItem;
import com.diamonddesign.rasvo.weatherclient.orm.CurrentConditions;
import com.diamonddesign.rasvo.weatherclient.orm.DailyConditions;
import com.diamonddesign.rasvo.weatherclient.orm.HourlyCondition;
import com.diamonddesign.rasvo.weatherclient.orm.Location;
import com.diamonddesign.rasvo.weatherclient.sharedprefs.SharedPrefsState;
import com.diamonddesign.rasvo.weatherclient.sharedprefs.SharedPrefsWrapper;
import com.diamonddesign.rasvo.weatherclient.strategy.UnitContext;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, NowFragment.ICurrentConditionRefreshCallback {

    private static final String TAG = MainActivity.class.getSimpleName();
    private NavigationView navigationView;
    private List<Location> locations;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private UnitContext unitContext;
    private NowFragment nowFragment = new NowFragment();
    private DailyFragment dailyFragment = new DailyFragment();
    private HourlyFragment hourlyFragment = new HourlyFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout) findViewById(R.id.tabsMain);
        viewPager = (ViewPager) findViewById(R.id.viewpagerMain);

        //Hack ORM
        Location.findById(Location.class, (long)1);
        CurrentConditions.findById(CurrentConditions.class, (long)1);
        DailyConditions.findById(DailyConditions.class, (long)1);
        HourlyCondition.findById(HourlyCondition.class, (long)1);

        setupViewPager();
        tabLayout.setupWithViewPager(viewPager);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        if (drawer != null) {
            drawer.addDrawerListener(toggle);
        }

        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }

        SharedPrefsWrapper wrapper = new SharedPrefsWrapper(this);
        SharedPrefsState.getInstance().setTemperatureUnits(wrapper.getTemperatureUnits());
        SharedPrefsState.getInstance().setUnits(wrapper.getOtherUnits());

        unitContext = new UnitContext(SharedPrefsState.getInstance().getTemperatureUnits(), SharedPrefsState.getInstance().getUnits());

        nowFragment.setUnitContext(unitContext);
        nowFragment.setCurrentConditionRefreshCallback(this);
        //hourly
        dailyFragment.setUnitContext(unitContext);

        List<Location> fav = Location.find(Location.class, "is_favourite = ?", "1");

        if (fav.size() > 0) {
            setupCurrentLocation(fav.get(0), true);
        }
        else {
            Location first = Location.first(Location.class);

            if (first != null) {
                setupCurrentLocation(first, true);
            }
        }
    }

    private void setupViewPager() {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(nowFragment, getString(R.string.now));
        viewPagerAdapter.addFragment(hourlyFragment, getString(R.string.hourly));
        viewPagerAdapter.addFragment(dailyFragment, getString(R.string.daily));
        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            locations = Location.listAll(Location.class);
            Menu navMenu = navigationView.getMenu();
            navMenu.removeGroup(R.id.nav_group_locations);
            for (Location location : locations) {
                MenuItem item = navMenu.add(R.id.nav_group_locations, location.getId().intValue(), 100, location.getLocationName());
                item.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_5));
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }

        SharedPrefsWrapper wrapper = new SharedPrefsWrapper(this);
        SharedPrefsState.getInstance().setTemperatureUnits(wrapper.getTemperatureUnits());
        SharedPrefsState.getInstance().setUnits(wrapper.getOtherUnits());
        unitContext.setTemperatureStrategy(SharedPrefsState.getInstance().getTemperatureUnits());
        unitContext.setUnitStrategy(SharedPrefsState.getInstance().getUnits());
        updateUnitContext();

        int selectedTabPosition = tabLayout.getSelectedTabPosition();

        switch (selectedTabPosition) {
            case 0:
                NowFragment item0 = (NowFragment)viewPagerAdapter.getItem(0);
                Location currentLocation0 = item0.getCurrentLocation();
                try {
                    item0.loadData(currentLocation0);
                    refreshCurrentHeader(currentLocation0);
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
                break;
            case 1:

                break;
            case 2:
                DailyFragment item2 = (DailyFragment)viewPagerAdapter.getItem(2);
                Location currentLocation2 = item2.getCurrentLocation();
                try {
                    item2.loadData(currentLocation2);
                    refreshCurrentHeader(currentLocation2);
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
                break;
        }

        Log.d(TAG, "onResume: ");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                Intent settings = new Intent(this, Settings.class);
                startActivity(settings);
                return true;
        }

        return false;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_settings:
                Intent settings = new Intent(this, Settings.class);
                startActivity(settings);
                closeDrawer();
                return true;
            case R.id.nav_manage_location:
                Intent manageLocations = new Intent(getApplicationContext(), ManageLocationsActivity.class);
                startActivity(manageLocations);
                closeDrawer();
                return true;
        }

        final Location location = Location.findById(Location.class, id);
        setupCurrentLocation(location, false);
        closeDrawer();
        return true;
    }

    private void closeDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    private void setupCurrentLocation(Location location, boolean isInit) {
        getSupportActionBar().setTitle(location.getLocalizedName());
        NowFragment nowFragment = (NowFragment) viewPagerAdapter.getItem(0);
        nowFragment.setCurrentLocation(location);

        DailyFragment dailyFragment = (DailyFragment) viewPagerAdapter.getItem(2);
        dailyFragment.setCurrentLocation(location);

        HourlyFragment hourlyFragment = (HourlyFragment) viewPagerAdapter.getItem(1);

        setDrawerHeaderInfo(location);

        if (isInit) {
            return;
        }

        int selectedTabPosition = tabLayout.getSelectedTabPosition();
        switch (selectedTabPosition) {
            case 0:
                nowFragment.loadData(location);
                break;
            case 1:

                break;
            case 2:
                dailyFragment.loadData(location);
                break;
        }
    }

    private void updateUnitContext() {
        NowFragment nowFragment = (NowFragment) viewPagerAdapter.getItem(0);
        nowFragment.setUnitContext(unitContext);

        DailyFragment dailyFragment = (DailyFragment) viewPagerAdapter.getItem(2);
        dailyFragment.setUnitContext(unitContext);

        HourlyFragment hourlyFragment = (HourlyFragment) viewPagerAdapter.getItem(1);
        hourlyFragment.setUnitContext(unitContext);
    }

    private void setDrawerHeaderInfo(Location location) {
        resetHeaderView();
        List<CurrentConditions> currentConditions = CurrentConditions.find(CurrentConditions.class, "key = ?", location.getKey());
        if (currentConditions.size() > 0) {
            CurrentConditions conditions = currentConditions.get(0);

            View headerView = navigationView.getHeaderView(0);
            TextView loc = (TextView) headerView.findViewById(R.id.textViewHeaderLocation);
            TextView phrase = (TextView) headerView.findViewById(R.id.textViewHeaderInfo);
            TextView temp = (TextView) headerView.findViewById(R.id.textViewHeaderTemp);
            ImageView icon = (ImageView) headerView.findViewById(R.id.imageViewHeaderIcon);

            loc.setText(location.getLocationName());
            phrase.setText(conditions.getWeatherText());
            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            temp.setText(decimalFormat.format(unitContext.getTemperatureStrategy().getTemperature(conditions)) + " " + unitContext.getTemperatureStrategy().getUnit());
            Resources resources = this.getResources();
            int id = resources.getIdentifier("ic_" + conditions.getWeatherIcon(), "drawable", this.getPackageName());
            ContextCompat.getDrawable(getApplicationContext(), id);
            icon.setImageDrawable(getDrawable(id));
        }
    }

    private void resetHeaderView() {
        View headerView = navigationView.getHeaderView(0);
        TextView loc = (TextView) headerView.findViewById(R.id.textViewHeaderLocation);
        TextView phrase = (TextView) headerView.findViewById(R.id.textViewHeaderInfo);
        TextView temp = (TextView) headerView.findViewById(R.id.textViewHeaderTemp);
        ImageView icon = (ImageView) headerView.findViewById(R.id.imageViewHeaderIcon);

        loc.setText(getString(R.string.no_info));
        phrase.setText(getString(R.string.no_info));
        temp.setText(getString(R.string.unknown_value));
        icon.setImageDrawable(getDrawable(R.drawable.ic_1));
    }

    @Override
    public void refreshCurrentHeader(Location location) {
        setDrawerHeaderInfo(location);
    }
}
