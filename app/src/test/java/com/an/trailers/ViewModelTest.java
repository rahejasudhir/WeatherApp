package com.an.trailers;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.Observer;

import com.an.trailers.data.remote.api.WeatherApiService;
import com.an.trailers.data.remote.model.WeatherResponse;
import com.an.trailers.ui.weather.WeatherViewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(JUnit4.class)
public class ViewModelTest {

  @Rule
  public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

  @Mock
  WeatherApiService apiService;

  private WeatherViewModel weatherViewModel;
  private TestScheduler testScheduler;

  @Mock
  Observer<WeatherResponse> observer;

  @Mock
  Observer<Throwable> throwableObserver;


  @Test
  public void addition_isCorrect() {
    assertEquals(4, 2 + 2);
  }

  @Before
  public void setup() {

    MockitoAnnotations.initMocks(this);

    testScheduler = new TestScheduler();

    TestScheduleProvider testSchedulerProvider = new TestScheduleProvider(testScheduler);

    weatherViewModel = new WeatherViewModel(apiService,testSchedulerProvider);
    weatherViewModel.getWeatherLiveData().observeForever(observer);
    weatherViewModel.getThrowable().observeForever(throwableObserver);
  }

  @Test
  public void testNull() {
    when(apiService.fetchCurrentWeather()).thenReturn(null);
    assertNotNull(weatherViewModel.getWeatherLiveData());
    assertTrue(weatherViewModel.getWeatherLiveData().hasObservers());
  }

  @Test
  public void fetchCurrentWeatherSuccess(){
    WeatherResponse response = new WeatherResponse();
    response.setName("Yo");
    when(apiService.fetchCurrentWeather()).thenReturn(Observable.just(response));
    weatherViewModel.fetchWeatherForeCasts();
    //verify(observer,times(1)).onChanged(response);
  }

  @Test
  public void fetchCurrentWeatherError() {
    Throwable api_error = new Throwable("XYX");
    when(apiService.fetchCurrentWeather()).thenReturn(Observable.error(api_error));
    weatherViewModel.getThrowable().observeForever(throwableObserver);
    weatherViewModel.fetchWeatherForeCasts();
    //verify(throwableObserver).onChanged(api_error);
  }


  @Test
  public void testSummation(){
    double temp1 = 16.829999923706055,temp2 = 11.149999618530273, temp3 = 14.199999809265137, temp4 = 9.880000114440918, temp5 = 19.190000534057617;

    double summation = weatherViewModel.summation(temp1, temp2, temp3, temp4, temp5);

    assertEquals("3.8655338010029467",String.valueOf(summation));
  }

  @After
  public void tearDown() {
    testScheduler = null;
    weatherViewModel = null;
    apiService = null;
  }

  /*@Test
  public void testDeviation(){
    WeatherResponse response = new WeatherResponse();

    WeatherApiService service = spy(new WeatherApiService() {
      @Override
      public Observable<WeatherResponse> fetchCurrentWeather() {
        return Observable.just(response);
      }

      @Override
      public Observable<WeatherResponse> fetchNext1Weather() {
        return Observable.just(response);
      }

      @Override
      public Observable<WeatherResponse> fetchNext2Weather() {
        return Observable.just(response);
      }

      @Override
      public Observable<WeatherResponse> fetchNext3Weather() {
        return Observable.just(response);
      }

      @Override
      public Observable<WeatherResponse> fetchNext4Weather() {
        return Observable.just(response);
      }

      @Override
      public Observable<WeatherResponse> fetchNext5Weather() {
        return Observable.just(response);
      }
    });

    weatherViewModel.fetchStandardDeviation();

    verify(service).fetchCurrentWeather();
    verify(service).fetchNext1Weather();
    verify(service).fetchNext2Weather();
    verify(service).fetchNext3Weather();
    verify(service).fetchNext4Weather();
    verify(service).fetchNext5Weather();

  }*/
}
