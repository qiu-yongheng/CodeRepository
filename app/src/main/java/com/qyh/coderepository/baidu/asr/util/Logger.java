package com.qyh.coderepository.baidu.asr.util;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * @author 邱永恒
 * @time 2018/1/18  21:40
 * @desc 管理日志打印的对象
 */

public class Logger {
    private static final String TAG = "Logger";

    private static final String INFO = "INFO";

    private static final String ERROR = "ERROR";

    private static boolean ENABLE = true;

    private static Handler handler;

    public static void info(String message) {
        info(TAG, message);
    }

    public static void info(String tag, String message) {
        log(INFO, tag, message);
    }

    public static void error(String message) {
        error(TAG, message);
    }

    public static void error(String tag, String message) {
        log(ERROR, tag, message);
    }

    public static void setHandler(Handler handler) {
        Logger.handler = handler;
    }

    private static void log(String level, String tag, String message) {
        if (!ENABLE) {
            return;
        }
        if (level.equals(INFO)) {
            Log.i(tag, message);

        } else if (level.equals(ERROR)) {
            Log.e(tag, message);
        }
        if (handler != null) {
            Message msg = Message.obtain();
            msg.obj = "[" + level + "]" + message + "\n";
            handler.sendMessage(msg);
        }
    }

    public static void setEnable(boolean isEnable) {
        ENABLE = isEnable;
    }
}
