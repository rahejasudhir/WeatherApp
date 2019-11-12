package com.an.trailers.di.component;


import android.app.Application;

import com.an.trailers.AppController;
import com.an.trailers.di.module.ActivityModule;
import com.an.trailers.di.module.ApiModule;
import com.an.trailers.di.module.ViewModelModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {ApiModule.class, ViewModelModule.class,
        AndroidSupportInjectionModule.class, ActivityModule.class})
public interface ApiComponent {


    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        @BindsInstance
        Builder apiModule(ApiModule apiModule);

        ApiComponent build();
    }

    void inject(AppController appController);
}
