package com.kc.common.net.cache;

import android.content.Context;

/**
 * @author 邱永恒
 * @time 2017/12/29  17:35
 * @desc 获取缓存操作类
 */

public class CacheManager {

    private static Context mContext;
    private static ApiCache.Builder apiCacheBuilder;
    private static ApiCache apiCache;

    public static void init(Context context) {
        mContext = context.getApplicationContext();
        apiCacheBuilder = new ApiCache.Builder(context);
    }

    public static ApiCache getApiCache() {
        if (apiCache == null || apiCache.isClosed()) {
            apiCache = getApiCacheBuilder().build();
        }
        return apiCache;
    }

    public static ApiCache.Builder getApiCacheBuilder() {
        if (apiCacheBuilder == null) {
            throw new IllegalStateException("Please call CacheManager.init(this) in Application to initialize!");
        }
        return apiCacheBuilder;
    }
}
