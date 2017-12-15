package com.kc.common.util.log;

import com.orhanobut.logger.Logger;

/**
 * @author 邱永恒
 * @time 2017/11/10  9:08
 * @desc ${TODD}
 */

public class LoggerUtil{
    public void d(String message, Object... args) {

    }

    public static void d(Object object) {
        Logger.d(object);
    }

    public static void e(String message, Object... args) {
        Logger.e(message, args);
    }

    public static void e(Throwable throwable) {
        Logger.e(throwable, null);
    }

    public void e(Throwable throwable, String message, Object... args) {

    }

    public void w(String message, Object... args) {

    }

    public void i(String message, Object... args) {

    }

    public void v(String message, Object... args) {

    }

    public void wtf(String message, Object... args) {

    }

    public void json(String json) {

    }

    public void xml(String xml) {

    }

    public void log(int priority, String tag, String message, Throwable throwable) {

    }
}
