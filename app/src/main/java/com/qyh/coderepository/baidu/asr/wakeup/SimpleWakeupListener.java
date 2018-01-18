package com.qyh.coderepository.baidu.asr.wakeup;


import com.qyh.coderepository.baidu.asr.util.Logger;

/**
 * @author 邱永恒
 * @time 2018/1/18  21:46
 * @desc 只处理语音唤醒的监听
 */

public class SimpleWakeupListener implements IWakeupListener {

    private static final String TAG = "SimpleWakeupListener";

    @Override
    public void onSuccess(String word, WakeUpResult result) {
        Logger.info(TAG, "唤醒成功，唤醒词：" + word);
    }

    @Override
    public void onStop() {
        Logger.info(TAG, "唤醒词识别结束：");
    }

    @Override
    public void onError(int errorCode, String errorMessge, WakeUpResult result) {
        Logger.info(TAG, "唤醒错误：" + errorCode + ";错误消息：" + errorMessge + "; 原始返回" + result.getOrigalJson());
    }

    @Override
    public void onASrAudio(byte[] data, int offset, int length) {
        Logger.error(TAG, "audio data： " + data.length);
    }

}
