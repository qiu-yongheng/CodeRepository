package com.kc.common.net.cache.strategy;


import android.util.Log;

import com.kc.common.net.cache.ApiCache;
import com.kc.common.net.cache.CacheResult;
import com.kc.common.util.GsonUtil;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 邱永恒
 * @time 2017/12/29  17:13
 * @desc 缓存策略
 */
abstract class CacheStrategy<T> implements ICacheStrategy<T> {
    private final String TAG = getClass().getSimpleName();
    /**
     * 读取本地缓存数据
     * @param apiCache
     * @param key
     * @param type
     * @param <T>
     * @return
     */
    <T> Observable<CacheResult<T>> loadCache(final ApiCache apiCache, final String key, final Type type) {
        return apiCache.<T>get(key).filter(new Predicate<String>() {
            @Override
            public boolean test(String s) throws Exception {
                return s != null;
            }
        }).map(new Function<String, CacheResult<T>>() {
            @Override
            public CacheResult<T> apply(String s) throws Exception {
                T t = GsonUtil.gson().fromJson(s, type);
                Log.i(TAG, "loadCache result = " + t);
                // isCache: 是否是缓存获取的
                return new CacheResult<>(true, t);
            }
        });
    }

    /**
     * 保存数据到本地缓存后, 返回remote数据
     * @param apiCache
     * @param key
     * @param source
     * @param <T>
     * @return
     */
    <T> Observable<CacheResult<T>> loadRemote(final ApiCache apiCache, final String key, Observable<T> source) {
        return source.map(new Function<T, CacheResult<T>>() {
            @Override
            public CacheResult<T> apply(T t) throws Exception {
                Log.i(TAG, "loadRemote result = " + t);
                apiCache.put(key, t).subscribeOn(Schedulers.io()).subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean status) throws Exception {
                        Log.i(TAG, "save status => " + status);
                    }
                });
                // isCache: 是否是缓存获取的
                return new CacheResult<>(false, t);
            }
        });
    }
}
