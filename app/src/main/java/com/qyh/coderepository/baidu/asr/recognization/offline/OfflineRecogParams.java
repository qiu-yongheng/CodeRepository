package com.qyh.coderepository.baidu.asr.recognization.offline;

import com.baidu.speech.asr.SpeechConstant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 邱永恒
 * @time 2018/1/19  11:43
 * @desc 离线参数配置
 */
public class OfflineRecogParams{

    private static final String TAG = "OnlineRecogParams";

    /**
     * 读取assets文件夹下的离线关键词
     * @return
     */
    public static Map<String, Object> fetchOfflineParams() {
        Map<String, Object> params = new LinkedHashMap<>();
        params.put(SpeechConstant.DECODER, 2);
        params.put(SpeechConstant.ASR_OFFLINE_ENGINE_GRAMMER_FILE_PATH, "assets://baidu_speech_grammar.bsg");
        return params;
    }

    /**
     * 替换对应字段的离线关键词
     * @return
     */
    public static Map<String, Object> fetchSlotDataParam() {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            JSONObject json = new JSONObject();
            json.put("name", new JSONArray().put("赵六").put("汪明"))
                    .put("appname", new JSONArray().put("手百").put("度秘"));
            map.put(SpeechConstant.SLOT_DATA, json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

}
