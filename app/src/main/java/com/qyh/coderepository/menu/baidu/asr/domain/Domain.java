package com.qyh.coderepository.menu.baidu.asr.domain;

import com.google.gson.Gson;
import com.qyh.coderepository.baidu.asr.recognization.NluResult;

import java.lang.reflect.Type;

/**
 * @author 邱永恒
 * @time 2018/1/20  13:26
 * @desc 领域抽象类
 */

public abstract class Domain<T> {
    private Gson gson;
    public NluResult result;
    private T t;

    Domain(NluResult result) {
        if (result == null) {
            throw new RuntimeException("NluResult can not be null!");
        }
        this.result = result;
        //parseObject();
    }

    T getObject(Type type) {
        if (gson == null) {
            gson = new Gson();
        }
        return gson.fromJson(result.getObject(), type);
    }

    public abstract void handle();
}
