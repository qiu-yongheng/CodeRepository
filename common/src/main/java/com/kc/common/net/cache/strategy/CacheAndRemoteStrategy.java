package com.kc.common.net.cache.strategy;


import com.kc.common.net.cache.ApiCache;
import com.kc.common.net.cache.CacheResult;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.functions.Predicate;

/**
 * @author 邱永恒
 * @time 2017/12/29  17:13
 * @desc 缓存策略--缓存和网络
 */
public class CacheAndRemoteStrategy<T> extends CacheStrategy<T> {
    @Override
    public <T> Observable<CacheResult<T>> execute(ApiCache apiCache, String cacheKey, Observable<T> source, final Type type) {
        // 读取本地缓存
        Observable<CacheResult<T>> cache = loadCache(apiCache, cacheKey, type);
        // 网络数据保存到本地后, 返回
        final Observable<CacheResult<T>> remote = loadRemote(apiCache, cacheKey, source);

        // concat: Concat操作符连接多个Observable的输出，就好像它们是一个Observable，第一个Observable发射的所有数据在第二个Observable发射的任何数据前面
        return Observable.concat(cache, remote).filter(new Predicate<CacheResult<T>>() {
            @Override
            public boolean test(CacheResult<T> tCacheResult) throws Exception {
                // 过滤掉为空的数据
                return tCacheResult != null && tCacheResult.getCacheData() != null;
            }
        });
    }
}
