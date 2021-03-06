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
        } else if (result.getRawText().contains("抢麦")) {
            TtsUtil.getInstance().speak("抢麦成功");
        } else if (result.getRawText().contains("放麦")) {
            TtsUtil.getInstance().speak("放麦成功");
        } else if (result.getRawText().contains("紧急抢麦")) {
            TtsUtil.getInstance().speak("紧急抢麦成功");
        } else if (result.getRawText().contains("sos")) {
            TtsUtil.getInstance().speak("sos发送成功");
        } else if (result.getRawText().contains("求救")) {
            TtsUtil.getInstance().speak("sos发送成功");
        } else {
           TtsUtil.getInstance().speak("暂时不支持此功能");
        }
    }
}
