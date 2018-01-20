package com.qyh.coderepository.menu.baidu.asr.domain;

import com.google.gson.reflect.TypeToken;
import com.qyh.coderepository.baidu.asr.recognization.NluResult;
import com.qyh.coderepository.menu.baidu.asr.object.WeatherObject;

/**
 * @author 邱永恒
 * @time 2018/1/20  13:18
 * @desc 天气领域
 */

public class WeatherDomain extends Domain<WeatherObject> {

    private final WeatherObject object;

    public WeatherDomain(NluResult result) {
        super(result);
        object = getObject(new TypeToken<WeatherObject>() {
        }.getType());
    }

    @Override
    public void handle() {
        switch (result.getIntent()) {
            case "query":
                break;
            case "get":
                break;
        }
    }
}
