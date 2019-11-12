package com.an.trailers.di.module;

import com.an.trailers.ui.weather.WeatherActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector()
    abstract WeatherActivity contributeWeatherActivity();
}