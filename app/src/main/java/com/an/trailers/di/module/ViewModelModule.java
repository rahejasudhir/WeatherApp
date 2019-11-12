package com.an.trailers.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import com.an.trailers.factory.ViewModelFactory;
import com.an.trailers.ui.weather.WeatherViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(WeatherViewModel.class)
    protected abstract ViewModel weatherViewModel(WeatherViewModel moviesListViewModel);

}