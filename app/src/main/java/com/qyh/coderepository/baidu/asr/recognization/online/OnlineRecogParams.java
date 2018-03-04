package com.qyh.coderepository.baidu.asr.recognization.online;

import com.baidu.speech.asr.SpeechConstant;
import com.qyh.coderepository.baidu.asr.recognization.PidBuilder;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 邱永恒
 * @time 2018/1/19  11:43
 * @desc 在线参数配置
 */
public class OnlineRecogParams {

    private static final String TAG = "OnlineRecogParams";

    /**
     * 获取在线识别的参数
     * @param enableOffline 是否开启离线命令词
     * @return 识别参数
     */
    public static Map<String, Object> fetchOnlineParams(boolean enableOffline) {
        Map<String, Object> params = new LinkedHashMap<>();
        if (enableOffline) {
            params.put(SpeechConstant.DECODER, 2);
            params.put(SpeechConstant.ASR_OFFLINE_ENGINE_GRAMMER_FILE_PATH, "assets:///baidu_speech_grammar.bsg");
            params.put(SpeechConstant.NLU, "enable");
        }
        params.put(SpeechConstant.PID, 15361); // 设置语义识别
        params.put(SpeechConstant.ACCEPT_AUDIO_VOLUME, true);
        return params;
    }

    /**
     * 获取唤醒识别的参数
     * @param backTrackInMs 间隔
     * @return 参数
     */
    public static Map<String, Object> fetchWakeUpRecogParams(int backTrackInMs) {
        Map<String, Object> params = new LinkedHashMap<>();
        params.put(SpeechConstant.ACCEPT_AUDIO_VOLUME, true);
        params.put(SpeechConstant.VAD, SpeechConstant.VAD_DNN);
        int pid = PidBuilder.create().model(PidBuilder.INPUT).toPId();
        // 如识别短句，不需要需要逗号，将PidBuilder.INPUT改为搜索模型PidBuilder.SEARCH
        params.put(SpeechConstant.PID, pid);
        if (backTrackInMs > 0) { // 方案1， 唤醒词说完后，直接接句子，中间没有停顿。
            params.put(SpeechConstant.AUDIO_MILLS, System.currentTimeMillis() - backTrackInMs);
        }
        return params;
    }
}
