package com.kc.common.net.cache.strategy;


import com.kc.common.net.cache.ApiCache;
import com.kc.common.net.cache.CacheResult;

import java.lang.reflect.Type;

import io.reactivex.Observable;


/**
 * @author 邱永恒
 * @time 2017/12/29  17:13
 * @desc 缓存策略--只取网络
 */
public class OnlyRemoteStrategy<T> extends CacheStrategy<T> {
    @Override
    public <T> Observable<CacheResult<T>> execute(ApiCache apiCache, String cacheKey, Observable<T> source, Type type) {
        return loadRemote(apiCache, cacheKey, source);
    }
}
