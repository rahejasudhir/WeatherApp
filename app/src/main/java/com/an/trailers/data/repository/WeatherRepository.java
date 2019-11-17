package com.an.trailers.data.repository;

import com.an.trailers.data.remote.api.WeatherApiService;
import com.an.trailers.data.remote.model.WeatherResponse;

import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class WeatherRepository implements Repository{

    private WeatherApiService weatherApiService;

    public WeatherRepository(WeatherApiService movieApiService) {
        this.weatherApiService = movieApiService;
    }

    @Override
    public Observable<WeatherResponse> fetchWeatherForeCasts() {
        return weatherApiService.fetchCurrentWeather();
    }
    @Override
    public Observable<WeatherResponse> fetchNext1Weather() {
        return weatherApiService.fetchNext1Weather();
    }
    @Override
    public Observable<WeatherResponse> fetchNext2Weather() {
        return weatherApiService.fetchNext2Weather();
    }
    @Override
    public Observable<WeatherResponse> fetchNext3Weather() {
        return weatherApiService.fetchNext3Weather();
    }
    @Override
    public Observable<WeatherResponse> fetchNext4Weather() {
        return weatherApiService.fetchNext4Weather();
    }
    @Override
    public Observable<WeatherResponse> fetchNext5Weather() {
        return weatherApiService.fetchNext5Weather();
    }
}
