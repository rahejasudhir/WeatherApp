package com.an.trailers.ui.weather;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.an.trailers.data.remote.api.WeatherApiService;
import com.an.trailers.data.remote.model.WeatherResponse;
import com.an.trailers.data.repository.WeatherRepository;
import com.an.trailers.schedulers.SchedulerProvider;


import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function5;
import io.reactivex.schedulers.Schedulers;

public class WeatherViewModel extends ViewModel {

    private WeatherRepository weatherRepository;
    private CompositeDisposable mDisposables = new CompositeDisposable();

    public MutableLiveData<WeatherResponse> getWeatherLiveData() {
        return weatherLiveData;
    }

    private MutableLiveData<WeatherResponse> weatherLiveData = new MutableLiveData<>();

    public MutableLiveData<Double> getStdDevLiveData() {
        return stdDevLiveData;
    }

    private MutableLiveData<Double> stdDevLiveData = new MutableLiveData<>();
    private SchedulerProvider schedulerProvider;

    @Inject
    public WeatherViewModel(WeatherApiService weatherApiService){
        weatherRepository = new WeatherRepository(weatherApiService);
        schedulerProvider = SchedulerProvider.getInstance();

    }

    public void fetchWeatherForeCasts() {
        mDisposables.add(weatherRepository.fetchWeatherForeCasts()
                .subscribeOn(schedulerProvider.io()).observeOn(schedulerProvider.ui())
                .subscribe(this::onForeCastFetched, throwable -> {
                    onForesCastFetchFailed();
                    throwable.printStackTrace();
                }));
    }

    private void onForesCastFetchFailed() {

    }

    private void onForeCastFetched(WeatherResponse forecastModel) {
        weatherLiveData.postValue(forecastModel);
    }

    public void fetchStandardDeviation() {


        Disposable disposable = Observable.zip(weatherRepository.fetchNext1Weather().subscribeOn(Schedulers.newThread()), weatherRepository.fetchNext2Weather().subscribeOn(Schedulers.newThread()), weatherRepository.fetchNext3Weather().subscribeOn(Schedulers.newThread()), weatherRepository.fetchNext4Weather().subscribeOn(Schedulers.newThread()), weatherRepository.fetchNext5Weather().subscribeOn(Schedulers.newThread()), new Function5<WeatherResponse, WeatherResponse, WeatherResponse,WeatherResponse, WeatherResponse, Double>() {

            @Override
            public Double apply(WeatherResponse w1, WeatherResponse w2, WeatherResponse w3, WeatherResponse w4, WeatherResponse w5) throws Exception {
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

                double averageTemp = temp1 + temp2 + temp3 + temp4 + temp5;
                averageTemp = averageTemp / 5 ;

                Log.v("SudhirMean", String.valueOf(averageTemp));

                double sumation =  (Math.pow((temp1 - averageTemp), 2) + Math.pow((temp2 - averageTemp), 2) +Math.pow((temp3 - averageTemp), 2) +Math.pow((temp4 - averageTemp), 2) + Math.pow((temp5 - averageTemp), 2));
                sumation = sumation / 4;
                sumation = Math.sqrt(sumation);

                return sumation;
            }
        }).subscribeOn(schedulerProvider.io()).observeOn(schedulerProvider.ui())
                .subscribe(new Consumer<Double>() {
                    @Override
                    public void accept(Double weatherResponse) throws Exception {
                        Log.v("SudhirDouble", weatherResponse.toString());
                        stdDevLiveData.postValue(weatherResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });

        mDisposables.add(disposable);
    }

    @Override
    protected void onCleared() {
        if(mDisposables != null && !mDisposables.isDisposed()) {
            mDisposables.clear();
        }
        super.onCleared();
    }
}
