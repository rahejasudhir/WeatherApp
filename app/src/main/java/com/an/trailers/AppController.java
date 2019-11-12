package com.an.trailers;

import android.app.Activity;
import android.app.Application;
import com.an.trailers.di.component.DaggerApiComponent;
import com.an.trailers.di.module.ApiModule;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;


public class AppController extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerApiComponent.builder()
                .application(this)
                .apiModule(new ApiModule())
                .build()
            .inject(this);
    }
}
