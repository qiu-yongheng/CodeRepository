package com.kc.common.net.interceptor;

import android.util.Log;

import com.blankj.utilcode.util.NetworkUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author 邱永恒
 * @time 2017/12/17  11:28
 * @desc 缓存处理
 */

public class CacheInterceptor implements Interceptor {
    private static final int MAX_AGE_OFFLINE = 28 * 24 * 60 * 60;//最大离线缓存时间4周（秒）
    private final String TAG = "CacheInterceptor";
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if(!NetworkUtils.isConnected()){
            //无网的时候强制使用缓存
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
            Log.d(TAG, "no network");
        }
        Response originalResponse = chain.proceed(request);
        if(NetworkUtils.isConnected()){
            // max-age = 0, 在线不缓存, 如果不为0, 在有网的情况下, 会根据你设置的max-age时间内, 读缓存, 超过max-age后重新请求
            // max-stale 设置离线缓存失效时间
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, max-age=" + 0 + ", max-stale=" + MAX_AGE_OFFLINE)
                    .removeHeader("Pragma")
                    .build();
        }else{
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                    .removeHeader("Pragma")
                    .build();
        }
    }
}
