package com.diamonddesign.rasvo.weatherclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.diamonddesign.rasvo.weatherclient.enums.TemperatureUnits;
import com.diamonddesign.rasvo.weatherclient.enums.Units;
import com.diamonddesign.rasvo.weatherclient.fragments.DailyFragment;
import com.diamonddesign.rasvo.weatherclient.fragments.HourlyFragment;
import com.diamonddesign.rasvo.weatherclient.fragments.NowFragment;
import com.diamonddesign.rasvo.weatherclient.fragments.adapters.ViewPagerAdapter;
import com.diamonddesign.rasvo.weatherclient.orm.CurrentConditions;
import com.diamonddesign.rasvo.weatherclient.orm.DailyConditions;
import com.diamonddesign.rasvo.weatherclient.orm.Location;
import com.diamonddesign.rasvo.weatherclient.strategy.UnitContext;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private NavigationView navigationView;
    private List<Location> locations;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private UnitContext unitContext;

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

        unitContext = new UnitContext(TemperatureUnits.CELSIUS, Units.METRIC);
    }

    private void setupViewPager() {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(NowFragment.newInstance(), getString(R.string.now));
        viewPagerAdapter.addFragment(HourlyFragment.newInstance(), getString(R.string.hourly));
        viewPagerAdapter.addFragment(DailyFragment.newInstance(), getString(R.string.daily));
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
                return true;
            case R.id.action_refresh:
                return true;
            case R.id.action_current_position:
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
                closeDrawer();
                return true;
            case R.id.nav_manage_location:
                Intent manageLocations = new Intent(getApplicationContext(), ManageLocationsActivity.class);
                startActivity(manageLocations);
                closeDrawer();
                return true;
        }

        final Location location = Location.findById(Location.class, id);
        setupCurrentLocation(location);
        closeDrawer();
        return true;
    }

    private void closeDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    private void setupCurrentLocation(Location location) {
        getSupportActionBar().setTitle(location.getLocalizedName());
        NowFragment nowFragment = (NowFragment) viewPagerAdapter.getItem(0);
        nowFragment.setUnitContext(unitContext);
        nowFragment.loadData(location);
    }
}
