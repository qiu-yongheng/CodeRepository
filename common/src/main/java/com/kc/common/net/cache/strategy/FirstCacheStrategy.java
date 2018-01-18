package com.kc.common.net.cache.strategy;


import com.kc.common.net.cache.ApiCache;
import com.kc.common.net.cache.CacheResult;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * @author 邱永恒
 * @time 2017/12/29  17:13
 * @desc 缓存策略--优先缓存
 */
public class FirstCacheStrategy<T> extends CacheStrategy<T> {
    @Override
    public <T> Observable<CacheResult<T>> execute(ApiCache apiCache, String cacheKey, Observable<T> source, Type type) {
        // 获取本地缓存
        Observable<CacheResult<T>> cache = loadCache(apiCache, cacheKey, type);
        cache.onErrorReturn(new Function<Throwable, CacheResult<T>>() {
            @Override
            public CacheResult<T> apply(Throwable throwable) throws Exception {
                return null;
            }
        });

        /**
         * 缓存数据到本地
         */
        Observable<CacheResult<T>> remote = loadRemote(apiCache, cacheKey, source);

        // concat(cache, remote): 先发射cache, 再发射remote
        // firstElement: 只发送第一个元素
        return Observable.concat(cache, remote).filter(new Predicate<CacheResult<T>>() {
            @Override
            public boolean test(CacheResult<T> tCacheResult) throws Exception {
                return tCacheResult != null && tCacheResult.getCacheData() != null;
            }
        }).firstElement().toObservable();
    }
}
