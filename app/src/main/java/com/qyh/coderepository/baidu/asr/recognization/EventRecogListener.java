package com.qyh.coderepository.baidu.asr.recognization;

/**
 * @author 邱永恒
 * @time 2018/1/19  15:16
 * @desc 处理识别后的意图
 */

public class EventRecogListener implements IRecogListener{
    @Override
    public void onAsrReady() {

    }

    @Override
    public void onAsrBegin() {

    }

    @Override
    public void onAsrEnd() {

    }

    @Override
    public void onAsrPartialResult(String[] results, RecogResult recogResult) {

    }

    @Override
    public void onAsrFinalResult(String[] results, RecogResult recogResult) {

    }

    @Override
    public void onAsrFinish(RecogResult recogResult) {

    }

    @Override
    public void onAsrFinishError(int errorCode, int subErrorCode, String errorMessage, String descMessage, RecogResult recogResult) {

    }

    @Override
    public void onAsrLongFinish() {

    }

    @Override
    public void onAsrVolume(int volumePercent, int volume) {

    }

    @Override
    public void onAsrAudio(byte[] data, int offset, int length) {

    }

    @Override
    public void onAsrExit() {

    }

    /**
     * 语义识别结果
     * @param nluResult
     */
    @Override
    public void onAsrOnlineNluResult(String nluResult) {
        NluResult result = NluResult.parseJson(nluResult);
    }

    @Override
    public void onOfflineLoaded() {

    }

    @Override
    public void onOfflineUnLoaded() {

    }
}
