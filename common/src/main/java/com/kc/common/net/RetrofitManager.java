package com.kc.common.net;

import android.content.Context;

import com.kc.common.common.HttpConfig;
import com.kc.common.net.interceptor.CacheInterceptor;
import com.kc.common.net.interceptor.HttpLogInterceptor;
import com.kc.common.util.path.DirPathUtil;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author 邱永恒
 * @time 2017/12/15  10:43
 * @desc ${TODD}
 */

public class RetrofitManager {
    private static RetrofitManager instance;
    private final Context mContext;
    private final Retrofit retrofit;
    private final OkHttpClient httpClient;

    private RetrofitManager(Context context) {
        this.mContext = context.getApplicationContext();
        // 设置离线缓存
        // 设置连接超时
        // 错误重连
        httpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new CacheInterceptor()) // 设置离线缓存
                .addInterceptor(new CacheInterceptor())
                .addInterceptor(new HttpLogInterceptor().setLevel(HttpLogInterceptor.Level.HEADERS))
                .cache(new Cache(DirPathUtil.getOffHttpDir(mContext), HttpConfig.MAX_OFFLINE_SIZE))
                .connectTimeout(30, TimeUnit.SECONDS) // 设置连接超时
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true) // 错误重连
                .build();

        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(HttpConfig.BASE_URL)
                .client(httpClient)
                .build();
    }

    public static void init(Context context) {
        if (instance == null) {
            synchronized (RetrofitManager.class) {
                instance = new RetrofitManager(context);
            }
        }
    }

    /**
     * 创建单例
     */
    public static RetrofitManager getInstance() {
        return instance;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public <T> T create(Class<T> service) {
        return retrofit.create(service);
    }
}
