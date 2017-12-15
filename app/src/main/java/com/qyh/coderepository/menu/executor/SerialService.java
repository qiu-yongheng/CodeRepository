package com.qyh.coderepository.menu.executor;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import com.kc.common.util.log.LoggerUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 邱永恒
 * @time 2017/12/13  12:46
 * @desc ${TODD}
 */

public class SerialService extends Service {

    private Thread thread;
    private final List<String> queue = new ArrayList<>();
    private final List<String> a = new ArrayList<>();
    private int i = 10;
    private Object lock = new Object();

    class SerialBinder extends Binder {
        public SerialService getService() {
            return SerialService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new SerialBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (thread == null) {
            thread = new Thread(new SerialRunnable());
        }
        if (!thread.isAlive()) {
            thread.start();
        }

        for (int i = 0; i < 10; i++) {
            a.add(String.valueOf(i));
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    class SerialRunnable implements Runnable {
        @Override
        public void run() {
            while (true) {
                synchronized (lock) {
                    if (!queue.isEmpty()) {
                        LoggerUtil.d("收到任务块");
                        queue.remove(0);
                        while (!a.isEmpty()) {
                            if (exe(a.get(0))) {
                                a.remove(0);
                            }
                        }
                    }
                }
                SystemClock.sleep(1000);
            }
        }
    }

    public boolean exe(String a) {
        if (a.equals("4")) {
            i--;
            LoggerUtil.e(new RuntimeException("模拟异常"));
            SystemClock.sleep(2000);
            if (i < 0) {
                LoggerUtil.d("重试成功");
                return true;
            }
            return false;
        }
        LoggerUtil.d("执行任务: " + a);
        return true;
    }

    public void addQueue(String queue) {
        this.queue.add(queue);
    }

    public void addList(String a) {
        this.a.add(a);
    }
}
