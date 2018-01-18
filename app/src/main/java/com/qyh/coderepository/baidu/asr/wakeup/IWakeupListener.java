package com.qyh.coderepository.baidu.asr.wakeup;

/**
 * @author 邱永恒
 * @time 2018/1/18  21:45
 * @desc 语音唤醒监听
 */

public interface IWakeupListener {
    void onSuccess(String word, WakeUpResult result);

    void onStop();

    void onError(int errorCode, String errorMessge, WakeUpResult result);

    void onASrAudio(byte[] data, int offset, int length);
}
