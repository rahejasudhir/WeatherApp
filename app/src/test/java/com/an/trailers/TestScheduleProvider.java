package com.an.trailers;

import android.support.annotation.NonNull;

import com.an.trailers.schedulers.BaseSchedulerProvider;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.TestScheduler;

/**
 * Created by DK on 2019-11-14.
 */
public class TestScheduleProvider implements BaseSchedulerProvider {

    private final TestScheduler testScheduler;

    public TestScheduleProvider(TestScheduler testScheduler) {
        this.testScheduler = testScheduler;
    }

    @NonNull
    @Override
    public Scheduler computation() {
        return testScheduler;
    }

    @NonNull
    @Override
    public Scheduler io() {
        return testScheduler;
    }

    @NonNull
    @Override
    public Scheduler ui() {
        return testScheduler;
    }
}
