package com.kc.common.util;

import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;
import com.kc.common.base.ActivityLifeCycleEvent;
import com.kc.common.net.subscriber.MySubcriber;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * @author 邱永恒
 * @time 2018/2/6  23:12
 * @desc ${TODO}
 */


public class PingUtil {
    private static PingUtil mInstance;
    private final String HOST = "PING_HOST";

    private PingUtil() {
    }

    public static void init() {
        if (mInstance == null) {
            synchronized (PingUtil.class) {
                if (mInstance == null) {
                    mInstance = new PingUtil();
                }
            }
        }
    }

    public static PingUtil getInstance() {
        return mInstance;
    }

    public String getCacheHost() {
        return SPUtils.getInstance().getString(HOST);
    }

    public void saveHost(String host) {
        if (!TextUtils.isEmpty(host)) {
            SPUtils.getInstance().put(HOST, host);
        }
    }

    public void ping(final String host, final int pingCount, final onPingSendListener listener, final ActivityLifeCycleEvent event, PublishSubject<ActivityLifeCycleEvent> lifecycleSubject) {
        // 只发射第一个ActivityLifeCycleEvent.DESTROY事件
        lifecycleSubject.first(event);

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                String line;
                Process process;
                BufferedReader successReader = null;
                // ping命令
                String command = "ping " + (pingCount > 0 ? "-c " + pingCount : "") + " " + host;
                e.onNext("ping命令: " + command + "\n\n");

                process = Runtime.getRuntime().exec(command);
                if (process == null) {
                    e.onNext("ping 失败 : process is null.\n");
                } else {
                    // 读取ping内容
                    successReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    while ((line = successReader.readLine()) != null) {
                        e.onNext(line + "\n");
                    }

                    int status = process.waitFor();
                    if (status == 0) {
                        e.onNext(String.format("\nping %s 成功!\n", host));
                    } else {
                        e.onNext(String.format("\nping %s 失败!\n", host));
                    }

                    e.onNext(String.format("ping %s finish.\n", host));
                }
                if (process != null) {
                    process.destroy();
                }
                if (successReader != null) {
                    successReader.close();
                }

                e.onComplete();
            }
        })
                .takeUntil(lifecycleSubject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubcriber<String>() {
                    @Override
                    protected void _onNext(String msg) {
                        listener.onNotify(msg);
                    }

                    @Override
                    protected void _onError(String message, int httpExceptionCode) {
                        listener.onNotify("\n" + message + "\n");
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                });
    }

    public interface onPingSendListener {
        void onNotify(String msg);
    }
}
