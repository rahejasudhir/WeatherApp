package com.an.trailers.ui.weather;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.an.trailers.data.remote.api.WeatherApiService;
import com.an.trailers.data.remote.model.WeatherResponse;
import com.an.trailers.data.repository.Repository;
import com.an.trailers.data.repository.WeatherRepository;
import com.an.trailers.schedulers.BaseSchedulerProvider;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class WeatherViewModel extends ViewModel {

    private Repository repository;
    private CompositeDisposable mDisposables = new CompositeDisposable();

    public MutableLiveData<WeatherResponse> getWeatherLiveData() {
        return weatherLiveData;
    }

    public MutableLiveData<Throwable> getThrowable() {
        return throwableMutableLiveData;
    }


    private MutableLiveData<WeatherResponse> weatherLiveData = new MutableLiveData<>();

    private MutableLiveData<Throwable> throwableMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<Double> getStdDevLiveData() {
        return stdDevLiveData;
    }

    private MutableLiveData<Double> stdDevLiveData = new MutableLiveData<>();
    private BaseSchedulerProvider schedulerProvider;

    @Inject
    public WeatherViewModel(WeatherApiService weatherApiService, BaseSchedulerProvider provider){
        this.repository = new WeatherRepository(weatherApiService);
        this.schedulerProvider = provider;

    }

    public void fetchWeatherForeCasts() {
        mDisposables.add(repository.fetchWeatherForeCasts()
                .subscribeOn(this.schedulerProvider.io()).observeOn(this.schedulerProvider.ui())
                .subscribe(this::onForeCastFetched, throwable -> {
                    onForesCastFetchFailed(throwable);
                    throwable.printStackTrace();
                }));
    }

    private void onForesCastFetchFailed(Throwable throwable) {
        throwableMutableLiveData.postValue(throwable);
    }

    private void onForeCastFetched(WeatherResponse forecastModel) {
        weatherLiveData.postValue(forecastModel);
    }

    public void fetchStandardDeviation() {

        Disposable disposable = Observable.zip(repository.fetchNext1Weather(),
                repository.fetchNext2Weather(),
                repository.fetchNext3Weather(),
                repository.fetchNext4Weather(),
                repository.fetchNext5Weather(),
                (w1, w2, w3, w4, w5) -> {
                    Log.v("Sudhir1", w1.toString());
                    Log.v("Sudhir2", w2.toString());
                    Log.v("Sudhir3", w3.toString());
                    Log.v("Sudhir4", w4.toString());
                    Log.v("Sudhir5", w5.toString());
                    double temp1  = w1.getWeather().getTemp();
                    double temp2  = w2.getWeather().getTemp();
                    double temp3  = w3.getWeather().getTemp();
                    double temp4  = w4.getWeather().getTemp();
                    double temp5  = w5.getWeather().getTemp();

                    return summation(temp1,temp2,temp3,temp4,temp5);
                }).subscribeOn(schedulerProvider.io()).observeOn(schedulerProvider.ui())
                .subscribe(weatherResponse -> {
                    Log.v("SudhirDouble", weatherResponse.toString());
                    stdDevLiveData.postValue(weatherResponse);
                }, throwable -> {

                });

        mDisposables.add(disposable);
    }

    public double summation(double temp1, double temp2, double temp3, double temp4, double temp5){
        double averageTemp = temp1 + temp2 + temp3 + temp4 + temp5;
        averageTemp = averageTemp / 5 ;

        //Log.v("SudhirMean", String.valueOf(averageTemp));

        double sumation =  (Math.pow((temp1 - averageTemp), 2) + Math.pow((temp2 - averageTemp), 2) +Math.pow((temp3 - averageTemp), 2) +Math.pow((temp4 - averageTemp), 2) + Math.pow((temp5 - averageTemp), 2));
        sumation = sumation / 4;
        sumation = Math.sqrt(sumation);

        return sumation;
    }

    @Override
    protected void onCleared() {
        if(mDisposables != null && !mDisposables.isDisposed()) {
            mDisposables.clear();
        }
        super.onCleared();
    }
}
