package com.qyh.coderepository.baidu.asr.recognization.online;

import com.baidu.speech.asr.SpeechConstant;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 邱永恒
 * @time 2018/1/19  11:43
 * @desc 在线参数配置
 */
public class OnlineRecogParams {

    private static final String TAG = "OnlineRecogParams";

    public static Map<String, Object> fetchOnlineParams(boolean enableOffline) {
        Map<String, Object> params = new LinkedHashMap<>();
        if (enableOffline) {
            params.put(SpeechConstant.DECODER, 2);
            params.put(SpeechConstant.ASR_OFFLINE_ENGINE_GRAMMER_FILE_PATH, "asset:///baidu_speech_grammar.bsg");
        }
        params.put(SpeechConstant.PID, 15361);
        params.put(SpeechConstant.ACCEPT_AUDIO_VOLUME, true);
        return params;
    }
}
