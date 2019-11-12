package com.an.trailers.ui.weather;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
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

    WeatherViewModel weatherViewModel;

    private ProgressBar progressBar;
    private ProgressBar stdProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_activity);
        AndroidInjection.inject(this);
        initialiseViewModel();
        initialiseView();
        fetchWeatherDetails();
        observerWeatherViewModels();
    }

    private void initialiseView() {
        progressBar = findViewById(R.id.progress);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);

        Button stdDevBtn = findViewById(R.id.stdDevButton);
        stdDevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stdProgressBar = findViewById(R.id.std_progress);
                stdProgressBar.setIndeterminate(true);
                stdProgressBar.setVisibility(View.VISIBLE);
                findViewById(R.id.stdDevButton).setVisibility(View.GONE);
                weatherViewModel.fetchStandardDeviation();
            }
        });
    }

    private void initialiseViewModel() {
        weatherViewModel = ViewModelProviders.of(this, viewModelFactory).get(WeatherViewModel.class);
    }

    private void fetchWeatherDetails() {
        weatherViewModel.fetchWeatherForeCasts();
    }

    private void observerWeatherViewModels(){
        weatherViewModel.getWeatherLiveData().observe(this, new Observer<WeatherResponse>() {
            @Override
            public void onChanged(@Nullable WeatherResponse weatherResponse) {
                Log.v("Sudhir", "weatherResponse "+weatherResponse.toString());
                progressBar.setIndeterminate(false);
                progressBar.setVisibility(View.GONE);
                findViewById(R.id.contentView).setVisibility(View.VISIBLE);
                if(weatherResponse != null) {
                    ((AppCompatTextView) findViewById(R.id.current_temp)).setText(getString(R.string.current_temp, weatherResponse.getWeather().getTemp()));
                    ((AppCompatTextView) findViewById(R.id.wind_speed)).setText(getString(R.string.wind_speed, String.valueOf(weatherResponse.getWind().getSpeed())));
                    if(weatherResponse.getClouds().getCloudiness() > 50.0f) {
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
                stdProgressBar.setIndeterminate(false);
                stdProgressBar.setVisibility(View.GONE);
                findViewById(R.id.stdDevButton).setVisibility(View.GONE);
                AppCompatTextView stdDevTxtVw = ((AppCompatTextView) findViewById(R.id.std_dev_text));
                stdDevTxtVw.setVisibility(View.VISIBLE);
                stdDevTxtVw.setText(getString(R.string.std_dev_text, weatherResponse.doubleValue()));
            }
        });
    }


}
