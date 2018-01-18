package com.kc.common.net.cache;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.kc.common.common.HttpConfig;
import com.kc.common.net.cache.mode.CacheMode;
import com.kc.common.net.cache.strategy.ICacheStrategy;

import java.io.File;
import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 邱永恒
 * @time 2017/12/29  16:46
 * @desc 针对响应数据进行缓存管理 (磁盘缓存)
 */

public class ApiCache {
    private final DiskCache diskCache;
    private String cacheKey;
    private static final String TAG = "ApiCache";

    private ApiCache(Context context, String cacheKey, long time) {
        this.cacheKey = cacheKey;
        this.diskCache = new DiskCache(context).setCacheTime(time);
    }

    private ApiCache(Context context, File diskDir, long diskMaxSize, String cacheKey, long time) {
        this.cacheKey = cacheKey;
        diskCache = new DiskCache(context, diskDir, diskMaxSize).setCacheTime(time);
    }

    /**
     * 自定义的被订阅者, 让子类自己实现获取缓存方法
     * @param <T>
     */
    private static abstract class SimpleSubscribe<T> implements ObservableOnSubscribe<T> {
        @Override
        public void subscribe(ObservableEmitter<T> subscriber) throws Exception {
            try {
                // 获取 数据
                T data = execute();
                if (!subscriber.isDisposed() && data != null) {
                    subscriber.onNext(data);
                }
            } catch (Throwable e) {
                Log.e(TAG, e.toString());
                Exceptions.throwIfFatal(e);
                if (!subscriber.isDisposed()) {
                    subscriber.onError(e);
                }
                return;
            }
            if (!subscriber.isDisposed()) {
                subscriber.onComplete();
            }
        }

        abstract T execute() throws Throwable;
    }

    /**
     * 转换成指定缓存策略的class, 调用指定缓存的execute方法将数据转换成CacheResult
     * @param cacheMode 缓存策略枚举
     * @param cachekey 缓存key
     * @param type 缓存数据类型
     * @param <T>
     * @return
     */
    public <T> ObservableTransformer<T, CacheResult<T>> transformer(CacheMode cacheMode, String cachekey, final Type type) {
        // 获取缓存策略
        final ICacheStrategy strategy = loadStrategy(cacheMode);
        // 缓存key
        final String key = TextUtils.isEmpty(cachekey) ? ApiCache.this.cacheKey : cachekey;
        return new ObservableTransformer<T, CacheResult<T>>() {
            @Override
            public ObservableSource<CacheResult<T>> apply(Observable<T> apiResultObservable) {
                Log.i(TAG, "cacheKey = " + key);
                return strategy.execute(ApiCache.this, key, apiResultObservable, type);
            }
        };
    }

    /**
     * 获取数据
     * @param key
     * @return
     */
    public Observable<String> get(final String key) {
        return Observable.create(new SimpleSubscribe<String>() {
            @Override
            String execute() {
                return diskCache.get(key);
            }
        });
    }

    /**
     * 保存缓存
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public <T> Observable<Boolean> put(final String key, final T value) {
        return Observable.create(new SimpleSubscribe<Boolean>() {
            @Override
            Boolean execute() throws Throwable {
                diskCache.put(key, value);
                return true;
            }
        });
    }

    /**
     * 是否包含key对应的缓存
     * @param key
     * @return
     */
    public boolean containsKey(final String key) {
        return diskCache.contains(key);
    }

    /**
     * 移除key对应的缓存
     * @param key
     */
    public void remove(final String key) {
        diskCache.remove(key);
    }

    /**
     * 缓存是否关闭
     * @return
     */
    public boolean isClosed() {
        return diskCache.isClosed();
    }

    /**
     * 清空缓存
     * @return
     */
    public Disposable clear() {
        return Observable.create(new SimpleSubscribe<Boolean>() {
            @Override
            Boolean execute() throws Throwable {
                diskCache.clear();
                return true;
            }
        }).subscribeOn(Schedulers.io()).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean status) throws Exception {
                Log.i(TAG, "clear status => " + status);
            }
        });
    }

    /**
     * 根据类全名创建class
     * @param cacheMode 缓存策略枚举, getClassName()可以获取枚举封装的类名
     * @return
     */
    public ICacheStrategy loadStrategy(CacheMode cacheMode) {
        try {
            String pkName = ICacheStrategy.class.getPackage().getName();
            return (ICacheStrategy) Class.forName(pkName + "." + cacheMode.getClassName()).newInstance();
        } catch (Exception e) {
            throw new RuntimeException("loadStrategy(" + cacheMode + ") err!!" + e.getMessage());
        }
    }

    public static final class Builder {
        private final Context context;
        private File diskDir;
        private long diskMaxSize;
        private long cacheTime = DiskCache.CACHE_NEVER_EXPIRE;
        private String cacheKey = HttpConfig.REQUEST_SERVER_URL;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder(Context context, File diskDir, long diskMaxSize) {
            this.context = context;
            this.diskDir = diskDir;
            this.diskMaxSize = diskMaxSize;
        }

        public Builder cacheKey(String cacheKey) {
            this.cacheKey = cacheKey;
            return this;
        }

        public Builder cacheTime(long cacheTime) {
            this.cacheTime = cacheTime;
            return this;
        }

        public ApiCache build() {
            if (diskDir == null || diskMaxSize == 0) {
                return new ApiCache(context, cacheKey, cacheTime);
            } else {
                return new ApiCache(context, diskDir, diskMaxSize, cacheKey, cacheTime);
            }
        }
    }
}
