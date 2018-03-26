package com.qyh.coderepository.menu.baidu.asr.domain;

import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.kc.common.net.RetrofitManager;
import com.qyh.coderepository.api.ApiService;
import com.qyh.coderepository.baidu.asr.recognization.NluResult;
import com.qyh.coderepository.baidu.tts.TtsUtil;
import com.qyh.coderepository.entity.Weather;
import com.qyh.coderepository.menu.baidu.asr.object.WeatherObject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 邱永恒
 * @time 2018/1/20  13:18
 * @desc 天气领域
 */

public class WeatherDomain extends Domain<WeatherObject> {

    private final WeatherObject object;
    private final String param;

    public WeatherDomain(NluResult result) {
        super(result);
        object = getObject(new TypeToken<WeatherObject>() {
        }.getType());
        param = "key=50e68789a6924d4db1c9b5d2ce36736a&location=北京";
    }

    @Override
    public void handle() {
        switch (result.getIntent()) {
            // 一般查询: 查询天气
            case "query":
                String date = object.getDate();
                String region = object.getRegion();
                break;
            case "get": // 属性查询:
                break;
        }

        RetrofitManager.getInstance().create(ApiService.class)
                .getWeather("50e68789a6924d4db1c9b5d2ce36736a", "广州")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Weather>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(Weather weather) {
                        Weather.HeWeather6Bean bean = weather.getHeWeather6().get(0);
                        Weather.HeWeather6Bean.DailyForecastBean forecastBean = bean.getDaily_forecast().get(0);

                        String s = bean.getBasic().getLocation() + "明天" + forecastBean.getCond_txt_d() + ", " + forecastBean.getWind_dir() + forecastBean.getWind_spd() + "级, 最高气温" + forecastBean.getTmp_max() + ", 最低气温" + forecastBean.getTmp_min();
                        TtsUtil.getInstance().speak(s);
                        Log.d("==", "onNex: " + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("==", e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.d("==", "onComplete");
                    }
                });

        Log.d("WeatherDomain", object.toString());
    }
}
