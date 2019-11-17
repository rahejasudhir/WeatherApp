package com.an.trailers.data.repository;

import com.an.trailers.data.remote.model.WeatherResponse;

import io.reactivex.Observable;

/**
 * Created by DK on 2019-11-14.
 */
public interface Repository {
    Observable<WeatherResponse> fetchWeatherForeCasts();
    Observable<WeatherResponse> fetchNext1Weather();
    Observable<WeatherResponse> fetchNext2Weather();
    Observable<WeatherResponse> fetchNext3Weather();
    Observable<WeatherResponse> fetchNext4Weather();
    Observable<WeatherResponse> fetchNext5Weather();
}
