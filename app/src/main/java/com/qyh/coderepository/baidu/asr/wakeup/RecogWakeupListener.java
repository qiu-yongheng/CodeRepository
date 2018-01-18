package com.qyh.coderepository.baidu.asr.wakeup;

import android.os.Handler;

import com.qyh.coderepository.baidu.asr.recognization.IStatus;


/**
 * @author 邱永恒
 * @time 2018/1/18  21:48
 * @desc 唤醒并识别的监听
 */

public class RecogWakeupListener extends SimpleWakeupListener implements IStatus {

    private static final String TAG = "RecogWakeupListener";

    private Handler handler;

    public RecogWakeupListener(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void onSuccess(String word, WakeUpResult result) {
        super.onSuccess(word, result);
        handler.sendMessage(handler.obtainMessage(STATUS_WAKEUP_SUCCESS));
    }
}
