package com.an.trailers.data.repository;

import com.an.trailers.data.remote.api.WeatherApiService;
import com.an.trailers.data.remote.model.WeatherResponse;

import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class WeatherRepository {

    private WeatherApiService weatherApiService;

    public WeatherRepository(WeatherApiService movieApiService) {
        this.weatherApiService = movieApiService;
    }

    public Observable<WeatherResponse> fetchWeatherForeCasts() {
        return weatherApiService.fetchCurrentWeather();
    }

    public Observable<WeatherResponse> fetchNext1Weather() {
        return weatherApiService.fetchNext1Weather();
    }

    public Observable<WeatherResponse> fetchNext2Weather() {
        return weatherApiService.fetchNext2Weather();
    }

    public Observable<WeatherResponse> fetchNext3Weather() {
        return weatherApiService.fetchNext3Weather();
    }

    public Observable<WeatherResponse> fetchNext4Weather() {
        return weatherApiService.fetchNext4Weather();
    }

    public Observable<WeatherResponse> fetchNext5Weather() {
        return weatherApiService.fetchNext5Weather();
    }
}
