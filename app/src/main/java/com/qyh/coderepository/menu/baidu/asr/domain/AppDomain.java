package com.qyh.coderepository.menu.baidu.asr.domain;

import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.qyh.coderepository.baidu.asr.recognization.NluResult;
import com.qyh.coderepository.baidu.tts.TtsUtil;
import com.qyh.coderepository.menu.baidu.asr.object.AppObject;

/**
 * @author 邱永恒
 * @time 2018/1/20  13:36
 * @desc ${TODD}
 */

public class AppDomain extends Domain<AppObject>{

    private final AppObject object;

    public AppDomain(NluResult result) {
        super(result);
        object = getObject(new TypeToken<AppObject>() {}.getType());
    }

    @Override
    public void handle() {
        switch (result.getIntent()) {
            case "open":
                TtsUtil.getInstance().speak("正在为您打开" + object.getAppname());
                break;
            case "uninstall":
                TtsUtil.getInstance().speak("我没有这个权限卸载" + object.getAppname());
                break;
            case "update":
                TtsUtil.getInstance().speak("正在为您更新" + object.getAppname());
                break;
            case "download":
                TtsUtil.getInstance().speak("正在" + (TextUtils.isEmpty(object.getChannel()) ? "" : object.getChannel()) + "下载" + object.getAppname());
                break;
            case "search":
                TtsUtil.getInstance().speak("正在搜索" + object.getAppname());
                break;
            case "recommend":
                TtsUtil.getInstance().speak("推荐" + object.getAppname());
                break;
        }
    }
}
