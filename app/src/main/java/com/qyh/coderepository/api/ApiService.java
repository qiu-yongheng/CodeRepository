package com.qyh.coderepository.api;

import com.qyh.coderepository.entity.Weather;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author 邱永恒
 * @time 2018/1/23  13:32
 * @desc ${TODD}
 */

public interface ApiService {
    @GET("s6/weather")
    Observable<Weather> getWeather(@Query("key") String key, @Query("location") String location);
}
