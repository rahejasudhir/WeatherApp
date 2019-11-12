package com.an.trailers.ui.weather;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.an.trailers.R;
import com.an.trailers.data.remote.model.WeatherResponse;
import com.an.trailers.factory.ViewModelFactory;
import com.an.trailers.ui.base.BaseActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class WeatherActivity extends BaseActivity {

    @Inject
    ViewModelFactory viewModelFactory;

    private WeatherViewModel weatherViewModel;
    private ProgressBar progressBar;
    private ProgressBar stdProgressBar;

    private static final String CURRENT_TEMPERATURE = "current_temperature";
    private static final String WIND_SPEED = "wind_speed";
    private static final String CLOUDINESS = "cloudiness";
    private static final String STD_DEV_TEMP = "std_dev_temp";

    private float currentTemperature = 0.0f;
    private float windSpeed = 0.0f;
    private float cloudiness = 0.0f;
    private String stdDevTemp =null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_activity);
        AndroidInjection.inject(this);
        initialiseViewModel();
        initialiseView(savedInstanceState);
        observerWeatherViewModels();
    }

    private void initialiseView(Bundle savedInstanceState) {

        progressBar = findViewById(R.id.progress);
        stdProgressBar = findViewById(R.id.std_progress);

        Button stdDevBtn = findViewById(R.id.stdDevButton);

        if (savedInstanceState == null) {
            progressBar.setIndeterminate(true);
            progressBar.setVisibility(View.VISIBLE);
            fetchWeatherDetails();
        } else {

            progressBar.setIndeterminate(false);
            progressBar.setVisibility(View.GONE);
            findViewById(R.id.contentView).setVisibility(View.VISIBLE);

            currentTemperature = savedInstanceState.getFloat(CURRENT_TEMPERATURE);
            windSpeed = savedInstanceState.getFloat(WIND_SPEED);
            cloudiness = savedInstanceState.getFloat(CLOUDINESS);
            stdDevTemp = savedInstanceState.getString(STD_DEV_TEMP);

            ((AppCompatTextView) findViewById(R.id.current_temp)).setText(getString(R.string.current_temp, currentTemperature));
            ((AppCompatTextView) findViewById(R.id.wind_speed)).setText(getString(R.string.wind_speed, String.valueOf(windSpeed)));
            if (cloudiness > 50.0f) {
                findViewById(R.id.cloud).setVisibility(View.VISIBLE);
            } else {
                findViewById(R.id.cloud).setVisibility(View.GONE);
            }
            if (TextUtils.isEmpty(stdDevTemp)) {
                stdDevBtn.setVisibility(View.VISIBLE);
            } else {
                stdDevBtn.setVisibility(View.GONE);
                AppCompatTextView stdDevTxtVw = ((AppCompatTextView) findViewById(R.id.std_dev_text));
                stdDevTxtVw.setVisibility(View.VISIBLE);
                stdDevTxtVw.setText(getString(R.string.std_dev_text, Double.parseDouble(stdDevTemp)));
            }
        }

        stdDevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                stdProgressBar.setIndeterminate(true);
                stdProgressBar.setVisibility(View.VISIBLE);
                stdDevBtn.setVisibility(View.GONE);

                weatherViewModel.fetchStandardDeviation();
            }
        });

    }

    private void initialiseViewModel() {
        weatherViewModel = ViewModelProviders.of(this, viewModelFactory).get(WeatherViewModel.class);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putFloat(CURRENT_TEMPERATURE, currentTemperature);
        outState.putFloat(WIND_SPEED, windSpeed);
        outState.putFloat(CLOUDINESS, cloudiness);
        if(!TextUtils.isEmpty(stdDevTemp)) {
            outState.putString(STD_DEV_TEMP, stdDevTemp);
        }
    }

    private void fetchWeatherDetails() {
        weatherViewModel.fetchWeatherForeCasts();
    }

    private void observerWeatherViewModels() {
        weatherViewModel.getWeatherLiveData().observe(this, new Observer<WeatherResponse>() {
            @Override
            public void onChanged(@Nullable WeatherResponse weatherResponse) {
                Log.v("Sudhir", "weatherResponse " + weatherResponse.toString());
                progressBar.setIndeterminate(false);
                progressBar.setVisibility(View.GONE);
                currentTemperature = weatherResponse.getWeather().getTemp();
                windSpeed = weatherResponse.getWind().getSpeed();
                cloudiness = weatherResponse.getClouds().getCloudiness();

                findViewById(R.id.contentView).setVisibility(View.VISIBLE);
                if (weatherResponse != null) {
                    ((AppCompatTextView) findViewById(R.id.current_temp)).setText(getString(R.string.current_temp, weatherResponse.getWeather().getTemp()));
                    ((AppCompatTextView) findViewById(R.id.wind_speed)).setText(getString(R.string.wind_speed, String.valueOf(weatherResponse.getWind().getSpeed())));
                    if (weatherResponse.getClouds().getCloudiness() > 50.0f) {
                        findViewById(R.id.cloud).setVisibility(View.VISIBLE);
                    } else {
                        findViewById(R.id.cloud).setVisibility(View.GONE);
                    }
                }
            }
        });

        weatherViewModel.getStdDevLiveData().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(@Nullable Double weatherResponse) {
                stdDevTemp = weatherResponse.toString();
                stdProgressBar.setIndeterminate(false);
                stdProgressBar.setVisibility(View.GONE);
                findViewById(R.id.stdDevButton).setVisibility(View.GONE);
                AppCompatTextView stdDevTxtVw = findViewById(R.id.std_dev_text);
                stdDevTxtVw.setVisibility(View.VISIBLE);
                stdDevTxtVw.setText(getString(R.string.std_dev_text, weatherResponse.doubleValue()));
            }
        });
    }


}
