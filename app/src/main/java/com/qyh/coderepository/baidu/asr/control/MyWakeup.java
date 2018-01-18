package com.qyh.coderepository.baidu.asr.control;

import android.content.Context;

import com.baidu.speech.EventListener;
import com.baidu.speech.EventManager;
import com.baidu.speech.EventManagerFactory;
import com.baidu.speech.asr.SpeechConstant;
import com.qyh.coderepository.baidu.asr.util.Logger;
import com.qyh.coderepository.baidu.asr.wakeup.IWakeupListener;
import com.qyh.coderepository.baidu.asr.wakeup.WakeupEventAdapter;

import org.json.JSONObject;

import java.util.Map;

/**
 * @author 邱永恒
 * @time 2018/1/18  21:29
 * @desc 语音唤醒封装类
 */
public class MyWakeup {
    private static boolean isInited = false;

    private EventManager wp;
    private EventListener eventListener;

    private static final String TAG = "MyWakeup";

    public MyWakeup(Context context, EventListener eventListener) {
        if (isInited) {
            Logger.error(TAG, "还未调用release()，请勿新建一个新类");
            throw new RuntimeException("还未调用release()，请勿新建一个新类");
        }
        isInited = true;
        this.eventListener = eventListener;
        wp = EventManagerFactory.create(context, "wp");
        wp.registerListener(eventListener);
    }

    public MyWakeup(Context context, IWakeupListener eventListener) {
        this(context, new WakeupEventAdapter(eventListener));
    }

    public void start(Map<String, Object> params) {
        String json = new JSONObject(params).toString();
        Logger.info(TAG + ".Debug", "wakeup params(反馈请带上此行日志):" + json);
        wp.send(SpeechConstant.WAKEUP_START, json, null, 0, 0);
    }

    public void stop() {
        Logger.info(TAG, "唤醒结束");
        wp.send(SpeechConstant.WAKEUP_STOP, null, null, 0, 0);
    }

    public void release() {
        stop();
        wp.unregisterListener(eventListener);
        wp = null;
        isInited = false;
    }
}
