package com.kc.common.net.interceptor;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Description: 不使用缓存功能
 * @author: <a href="http://www.xiaoyaoyou1212.com">DAWI</a>
 * @date: 16/12/31 21:17.
 */
public class NoCacheInterceptor implements Interceptor {

    /**
     * Cache-Control: no-cache 请求或响应数据不进行缓存
     * @param chain
     * @return
     * @throws IOException
     */
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        request = request.newBuilder().header("Cache-Control", "no-cache").build();
        Response originalResponse = chain.proceed(request);
        originalResponse = originalResponse.newBuilder().header("Cache-Control", "no-cache").build();
        return originalResponse;
    }
}
