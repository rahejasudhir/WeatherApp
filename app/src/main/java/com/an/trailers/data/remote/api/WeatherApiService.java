package com.an.trailers.data.remote.api;

import com.an.trailers.data.remote.model.WeatherResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WeatherApiService {

    @GET("current.json")
    Observable<WeatherResponse> fetchCurrentWeather();


    @GET("future_1.json")
    Observable<WeatherResponse> fetchNext1Weather();

    @GET("future_2.json")
    Observable<WeatherResponse> fetchNext2Weather();

    @GET("future_3.json")
    Observable<WeatherResponse> fetchNext3Weather();

    @GET("future_4.json")
    Observable<WeatherResponse> fetchNext4Weather();

    @GET("future_5.json")
    Observable<WeatherResponse> fetchNext5Weather();
}
