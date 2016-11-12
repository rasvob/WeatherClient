package com.diamonddesign.rasvo.weatherclient;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.diamonddesign.rasvo.weatherclient.api.LocationApi;
import com.diamonddesign.rasvo.weatherclient.managelocation.AutoCompleteAdapter;
import com.diamonddesign.rasvo.weatherclient.managelocation.LocationAdapter;
import com.diamonddesign.rasvo.weatherclient.managelocation.callback.LocationOperationEvent;
import com.diamonddesign.rasvo.weatherclient.orm.Location;
import com.google.common.collect.Lists;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class ManageLocationsActivity extends AppCompatActivity {
    private ArrayList<Location> locations;
    private AutoCompleteTextView autoCompleteTextView;
    boolean isAutoCompleteVisible = false;
    private FloatingActionButton fab;
    private AutoCompleteAdapter adapter;
    private RecyclerView locationRecyclerView;
    private LocationAdapter locationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_locations);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Manage locations");

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

        locations = new ArrayList<>(Location.listAll(Location.class));
        locationRecyclerView = (RecyclerView) findViewById(R.id.manage_location_recycler);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        locationRecyclerView.setLayoutManager(layoutManager);
        locationRecyclerView.setItemAnimator(new DefaultItemAnimator());
        locationRecyclerView.setHasFixedSize(true);
        locationAdapter = new LocationAdapter(locations);
        locationRecyclerView.setAdapter(locationAdapter);
        locationAdapter.notifyDataSetChanged();

        autoCompleteTextView = (AutoCompleteTextView)findViewById(R.id.autocompleteViewLocation);
        adapter = new AutoCompleteAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Location location = adapter.getItem(position);
                if (location != null) {
                    location.save();
                }
                closeAutoComplete();
            }
        });

        autoCompleteTextView.setAdapter(adapter);
        fab = (FloatingActionButton) findViewById(R.id.fabManageLocations);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autoCompleteTextView.setVisibility(View.VISIBLE);
                autoCompleteTextView.clearListSelection();
                autoCompleteTextView.setText("");
                isAutoCompleteVisible = true;
                autoCompleteTextView.requestFocus();
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.showSoftInput(autoCompleteTextView, InputMethodManager.SHOW_IMPLICIT);
                fab.hide();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLocationEvent(LocationOperationEvent event) {
        Toast.makeText(this, event.getLocation().getLocationName() , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if (isAutoCompleteVisible) {
            closeAutoComplete();
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void closeAutoComplete() {
        autoCompleteTextView.setVisibility(View.GONE);
        isAutoCompleteVisible = false;
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(autoCompleteTextView.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
        fab.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.manage_locations, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_locations_gps:
                //TODO: Add GPS
                Toast.makeText(getApplicationContext(), "GPS clicked", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
