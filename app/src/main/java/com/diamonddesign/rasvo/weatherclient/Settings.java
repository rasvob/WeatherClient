package com.diamonddesign.rasvo.weatherclient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.diamonddesign.rasvo.weatherclient.enums.TemperatureUnits;
import com.diamonddesign.rasvo.weatherclient.enums.Units;
import com.diamonddesign.rasvo.weatherclient.sharedprefs.SharedPrefsState;
import com.diamonddesign.rasvo.weatherclient.sharedprefs.SharedPrefsWrapper;

public class Settings extends AppCompatActivity {
    private CheckBox temperatureCheckBox;
    private CheckBox unitsCheckBox;
    private SharedPrefsWrapper sharedPrefsWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.settings));
        }

        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

        temperatureCheckBox = (CheckBox) findViewById(R.id.settings_celsius_checkbox);
        unitsCheckBox = (CheckBox) findViewById(R.id.settings_metric_checkbox);

        sharedPrefsWrapper = new SharedPrefsWrapper(this);

        TemperatureUnits temoeratureUnits = sharedPrefsWrapper.getTemperatureUnits();
        Units units = sharedPrefsWrapper.getOtherUnits();


        temperatureCheckBox.setChecked(temoeratureUnits == TemperatureUnits.CELSIUS);
        unitsCheckBox.setChecked(units == Units.METRIC);

        temperatureCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sharedPrefsWrapper.setTemperatureUnits(isChecked ? TemperatureUnits.CELSIUS : TemperatureUnits.FAHRENHEIT);
                SharedPrefsState.getInstance().setTemperatureUnits(isChecked ? TemperatureUnits.CELSIUS : TemperatureUnits.FAHRENHEIT);
            }
        });

        unitsCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sharedPrefsWrapper.setOtherUnits(isChecked ? Units.METRIC : Units.IMPERIAL);
                SharedPrefsState.getInstance().setUnits(isChecked ? Units.METRIC : Units.IMPERIAL);
            }
        });
    }
}
