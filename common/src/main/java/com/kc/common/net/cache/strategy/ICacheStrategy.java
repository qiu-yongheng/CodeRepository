package com.kc.common.net.cache.strategy;


import com.kc.common.net.cache.ApiCache;
import com.kc.common.net.cache.CacheResult;

import java.lang.reflect.Type;

import io.reactivex.Observable;

/**
 * @author 邱永恒
 * @time 2017/12/29  17:14
 * @desc 缓存策略接口
 */

public interface ICacheStrategy<T> {
    /**
     * 根据缓存策略进行处理
     * @param apiCache 缓存管理类
     * @param cacheKey 缓存key(对URL进行MD5获取)
     * @param source 网络数据源
     * @param type bean.class
     * @param <T> 网络数据源bean
     * @return
     */
    <T> Observable<CacheResult<T>> execute(ApiCache apiCache, String cacheKey, Observable<T> source, Type type);
}
