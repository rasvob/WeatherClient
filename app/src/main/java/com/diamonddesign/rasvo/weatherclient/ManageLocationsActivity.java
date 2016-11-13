package com.diamonddesign.rasvo.weatherclient;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.diamonddesign.rasvo.weatherclient.api.LocationApi;
import com.diamonddesign.rasvo.weatherclient.api.async.GpsLocationTask;
import com.diamonddesign.rasvo.weatherclient.dialogs.DialogBuilders;
import com.diamonddesign.rasvo.weatherclient.managelocation.AutoCompleteAdapter;
import com.diamonddesign.rasvo.weatherclient.managelocation.LocationAdapter;
import com.diamonddesign.rasvo.weatherclient.managelocation.callback.LocationOperationEvent;
import com.diamonddesign.rasvo.weatherclient.orm.Location;
import com.diamonddesign.rasvo.weatherclient.services.Helpers;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import okhttp3.Request;


public class ManageLocationsActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private ArrayList<Location> locations;
    private AutoCompleteTextView autoCompleteTextView;
    boolean isAutoCompleteVisible = false;
    private FloatingActionButton fab;
    private AutoCompleteAdapter adapter;
    private RecyclerView locationRecyclerView;
    private LocationAdapter locationAdapter;
    private CoordinatorLayout coordinatorLayout;
    private android.location.Location currentLocation;
    private GoogleApiClient googleApiClient;


    private final int REQUEST_PERMISSION_FINE_LOCATION = 127;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_locations);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(R.string.manage_locations_toolbar_header);
        }

        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

        try {
            buildApiClient();
        } catch (Exception exc) {
            exc.printStackTrace();
        }

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.manage_location_coordinator);
        locationRecyclerView = (RecyclerView) findViewById(R.id.manage_location_recycler);
        fab = (FloatingActionButton) findViewById(R.id.fabManageLocations);

        locations = new ArrayList<>(Location.listAll(Location.class));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        locationRecyclerView.setLayoutManager(layoutManager);
        locationRecyclerView.setItemAnimator(new DefaultItemAnimator());
        locationRecyclerView.setHasFixedSize(true);
        locationAdapter = new LocationAdapter(locations, this);
        locationRecyclerView.setAdapter(locationAdapter);
        locationAdapter.notifyDataSetChanged();

        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autocompleteViewLocation);
        adapter = new AutoCompleteAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                addNewLocation(position);
                closeAutoComplete();
            }
        });
        autoCompleteTextView.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isInternetAvailable()) {
                    showInternetDialog();
                    return;
                }

                showAutoComplete();
            }
        });
    }

    private void buildApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    private void addNewLocation(int position) {
        Location location = adapter.getItem(position);
        if (location != null) {
            if (Location.find(Location.class, "key = ?", location.getKey()).size() == 0) {
                location.save();
                ManageLocationsActivity.this.locations.add(location);
                locationAdapter.notifyItemInserted(ManageLocationsActivity.this.locations.size() - 1);
            } else {
                Toast.makeText(ManageLocationsActivity.this, R.string.location_exists, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isInternetAvailable() {
        return Helpers.isInternetAvailable(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLocationEvent(LocationOperationEvent event) {
        final Location location = event.getLocation();
        int pos = event.getPosition();

        switch (event.getOperation()) {
            case REMOVE:
                removeLocation(location, pos);
                break;
            case FAVOURITE:
                setLocationFavourite(location, pos);
                break;
        }
    }

    private void removeLocation(final Location location, int pos) {
        locations.remove(pos);
        locationAdapter.notifyItemRemoved(pos);
        locationAdapter.notifyItemRangeChanged(pos, locations.size());
        location.delete();

        Snackbar snackbar = Snackbar.make(coordinatorLayout, location.getLocationName() + getString(R.string.removed), Snackbar.LENGTH_LONG);
        snackbar.setAction("Undo", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locations.add(location);
                locationAdapter.notifyItemInserted(locations.size() - 1);
                location.save();
            }
        });
        snackbar.show();
    }

    private void setLocationFavourite(Location location, int pos) {
        boolean initialState = location.isFavourite();
        location.setFavourite(!initialState);
        location.save();
        locationAdapter.notifyItemChanged(pos);

        for (int i = 0; i < locations.size(); i++) {
            Location loc = locations.get(i);
            if (loc.isFavourite() && loc.getId() != location.getId()) {
                loc.setFavourite(false);
                locationAdapter.notifyItemChanged(i);
                loc.save();
            }
        }
    }


    @Override
    public void onBackPressed() {
        if (isAutoCompleteVisible) {
            closeAutoComplete();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);

        if (googleApiClient != null) {
            googleApiClient.disconnect();
        }
    }

    private void closeAutoComplete() {
        autoCompleteTextView.setVisibility(View.GONE);
        isAutoCompleteVisible = false;
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(autoCompleteTextView.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
        fab.show();
    }

    private void showAutoComplete() {
        autoCompleteTextView.setVisibility(View.VISIBLE);
        autoCompleteTextView.clearListSelection();
        autoCompleteTextView.setText("");
        isAutoCompleteVisible = true;
        autoCompleteTextView.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(autoCompleteTextView, InputMethodManager.SHOW_IMPLICIT);
        fab.hide();
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
            case R.id.action_locations_gplay:
                if (!Helpers.isInternetAvailable(this)) {
                    showInternetDialog();
                    break;
                }

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this , new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_PERMISSION_FINE_LOCATION);
                }
                else {
                    if (!googleApiClient.isConnected()) {
                        googleApiClient.connect();
                    }
                    refreshCurrentPosition();
                    if (currentLocation != null) {
                        getCurrentLocationFromApi();
                    }
                    else {
                        showLocationSnackbar();
                    }
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showLocationSnackbar() {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, getApplicationContext().getString(R.string.make_sure_location_is_enabled), Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.enable_location, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });
        snackbar.show();
    }

    private void getCurrentLocationFromApi() {
        double lat = currentLocation.getLatitude();
        double lon = currentLocation.getLongitude();

        LocationApi api = new LocationApi();
        Request request = api.buildGpsLocationRequest(lat, lon);

        GpsLocationTask task = new GpsLocationTask(this) {
            @Override
            protected void onPostExecute(Location location) {
                super.onPostExecute(location);
                if (location != null) {
                    if (Location.find(Location.class, "key = ?", location.getKey()).size() == 0) {
                        location.save();
                        ManageLocationsActivity.this.locations.add(location);
                        locationAdapter.notifyItemInserted(ManageLocationsActivity.this.locations.size() - 1);
                        Toast.makeText(ManageLocationsActivity.this, location.getLocationName() + getString(R.string.added), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ManageLocationsActivity.this, location.getLocationName() + getString(R.string.already_exists), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
        task.execute(request);
    }

    private void showLocationDialog() {
        DialogBuilders builders = new DialogBuilders(this,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                },
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        AlertDialog dialog = builders.createLocationDialog();
        dialog.show();
    }

    private void showInternetDialog() {
        DialogBuilders builder = new DialogBuilders(ManageLocationsActivity.this,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                        startActivity(intent);
                    }
                },
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        AlertDialog dialog = builder.createWifiDialog();
        dialog.show();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION_FINE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, R.string.location_permission_granted, Toast.LENGTH_SHORT).show();
                    if (googleApiClient != null) {
                        googleApiClient.connect();
                    }
                }
                break;
        }
    }

    @SuppressWarnings("MissingPermission")
    private void refreshCurrentPosition() {
        currentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            refreshCurrentPosition();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(ManageLocationsActivity.class.getSimpleName(), "onConnectionFailed: ");
    }
}
