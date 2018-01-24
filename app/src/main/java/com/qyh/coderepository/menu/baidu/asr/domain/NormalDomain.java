package com.qyh.coderepository.menu.baidu.asr.domain;

import com.qyh.coderepository.baidu.asr.recognization.NluResult;
import com.qyh.coderepository.baidu.tts.TtsUtil;

/**
 * @author 邱永恒
 * @time 2018/1/20  13:33
 * @desc ${TODD}
 */

public class NormalDomain extends Domain<String> {
    public NormalDomain(NluResult result) {
        super(result);
    }

    @Override
    public void handle() {
        if (result.getRawText().contains("傻逼")) {
            TtsUtil.getInstance().speak("是啊, 对啊");
        } else {
            //TtsUtil.getInstance().speak("傻逼, 目前还没有这个功能");
        }
    }
}
