package com.qyh.coderepository.baidu.tts;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.util.Pair;

import com.baidu.tts.auth.AuthInfo;
import com.baidu.tts.client.SpeechSynthesizeBag;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.TtsMode;
import com.qyh.coderepository.baidu.tts.listener.MessageListener;
import com.qyh.coderepository.baidu.tts.util.OfflineResource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 邱永恒
 * @time 2017/12/22  8:41
 * @desc 百度离在线语音合成
 */

public class TtsUtil {
    private static final String TAG = "TtsUtil";
    private final String APPID = "10711638";
    private final String APPKEY = "YYKX4Zf5l86D0yzlqufWnb3W";
    private final String SECRETKEY = "f7a875ea89d67d713b1bf6a24464a8ac";
    // TtsMode.MIX; 离在线融合，在线优先； TtsMode.ONLINE 纯在线； 没有纯离线
    private TtsMode ttsMode = TtsMode.MIX;
    // 离线发音选择，VOICE_FEMALE即为离线女声发音。
    private String offlineVoice = OfflineResource.VOICE_FEMALE;
    // ===============初始化参数设置完毕，更多合成参数请至getParams()方法中设置 =================

    private static TtsUtil instance;
    private boolean isInitied = false;
    private Context context;
    private OfflineResource offlineResource;
    private SpeechSynthesizer mSpeechSynthesizer;
    private HandlerThread hThread;
    private Handler tHandler;
    private final int INIT = 1;
    private final int RELEASE = 11;

    private TtsUtil() {}

    public static TtsUtil getInstance() {
        if (instance == null) {
            synchronized (TtsUtil.class) {
                if (instance == null) {
                    instance = new TtsUtil();
                }
            }
        }
        return instance;
    }

    public void init(final Context context) {
        hThread = new HandlerThread("Synthesizer-thread");
        hThread.start();
        tHandler = new Handler(hThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case INIT:
                        boolean isSuccess = initTts(context);
                        if (isSuccess) {
                             //speak("初始化成功");
                             Log.i(TAG, "初始化成功");
                        } else {
                            Log.e(TAG, "合成引擎初始化失败, 请查看日志");
                        }
                        break;
                    case RELEASE:
                        release();
                        if (Build.VERSION.SDK_INT < 18) {
                            getLooper().quit();
                        }
                        break;
                }
            }
        };

        runInHandlerThread(INIT);
    }

    private void runInHandlerThread(int action) {
        runInHandlerThread(action, null);
    }

    private void runInHandlerThread(int action, Object obj) {
        Message msg = Message.obtain();
        msg.what = action;
        msg.obj = obj;
        tHandler.sendMessage(msg);
    }

    private boolean initTts(Context context) {
        if (isInitied) {
            // SpeechSynthesizer.getInstance() 不要连续调用
            throw new RuntimeException("MySynthesizer 类里面 SpeechSynthesizer还未释放，请勿新建一个新类");
        }
        this.context = context.getApplicationContext();
        isInitied = true;

        Log.i(TAG, "初始化开始");
        boolean isMix = ttsMode.equals(TtsMode.MIX);
        if (isMix) {
            try {
                offlineResource = new OfflineResource(context, offlineVoice);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "【error】:copy files from assets failed." + e.getMessage());
                return false;
            }
        }

        // 初始化语音合成类
        mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        mSpeechSynthesizer.setContext(context);
        mSpeechSynthesizer.setSpeechSynthesizerListener(new MessageListener());

        // 请替换为语音开发者平台上注册应用得到的App ID ,AppKey ，Secret Key ，填写在SynthActivity的开始位置
        mSpeechSynthesizer.setAppId(APPID);
        mSpeechSynthesizer.setApiKey(APPKEY, SECRETKEY);

        if (isMix) {
            // 设置语音合成文本模型文件
            mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, offlineResource.getTextFilename()); // 文本模型文件路径 (离线引擎使用)
            // 设置语音合成声音模型文件
            mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, offlineResource.getModelFilename());  // 声学模型文件路径 (离线引擎使用)
            Log.i(TAG, "离线资源路径：" + offlineResource.getTextFilename() + ":" + offlineResource.getModelFilename());

            // 授权检测接口(只是通过AuthInfo进行检验授权是否成功。选择纯在线可以不必调用auth方法。
            AuthInfo authInfo = mSpeechSynthesizer.auth(ttsMode);
            if (!authInfo.isSuccess()) {
                // 离线授权需要网站上的应用填写包名。本demo的包名是com.baidu.tts.sample，定义在build.gradle中
                String errorMsg = authInfo.getTtsError().getDetailMessage();
                Log.e(TAG, "鉴权失败 =" + errorMsg);
                return false;
            } else {
                Log.i(TAG, "验证通过，离线正式授权文件存在。");
            }
        }

        setParams(getParams());
        // 初始化tts
        int result = mSpeechSynthesizer.initTts(ttsMode);
        if (result != 0) {
            Log.e(TAG, "【error】initTts 初始化失败 + errorCode：" + result);
            return false;
        }
        Log.i(TAG, "合成引擎初始化成功");
        return true;
    }

    /**
     * 合成并播放
     *
     * @param text 小于1024 GBK字节，即512个汉字或者字母数字
     * @return
     */
    public int speak(String text) {
        Log.i(TAG, "speak text:" + text);
        return mSpeechSynthesizer.speak(text);
    }

    /**
     * 合成并播放
     *
     * @param text        小于1024 GBK字节，即512个汉字或者字母数字
     * @param utteranceId 用于listener的回调，默认"0"
     * @return
     */
    public int speak(String text, String utteranceId) {
        return mSpeechSynthesizer.speak(text, utteranceId);
    }

    /**
     * 只合成不播放
     *
     * @param text
     * @return
     */
    public int synthesize(String text) {
        return mSpeechSynthesizer.synthesize(text);
    }

    /**
     * 只合成不播放
     *
     * @param text
     * @param utteranceId
     * @return
     */
    public int synthesize(String text, String utteranceId) {
        return mSpeechSynthesizer.synthesize(text, utteranceId);
    }

    /**
     * 批量播放
     *
     * @param texts
     * @return
     */
    public int batchSpeak(List<Pair<String, String>> texts) {
        List<SpeechSynthesizeBag> bags = new ArrayList<SpeechSynthesizeBag>();
        for (Pair<String, String> pair : texts) {
            SpeechSynthesizeBag speechSynthesizeBag = new SpeechSynthesizeBag();
            speechSynthesizeBag.setText(pair.first);
            if (pair.second != null) {
                speechSynthesizeBag.setUtteranceId(pair.second);
            }
            bags.add(speechSynthesizeBag);

        }
        return mSpeechSynthesizer.batchSpeak(bags);
    }

    /**
     * 暂停播放
     *
     * @return
     */
    public int pause() {
        return mSpeechSynthesizer.pause();
    }

    /**
     * 继续播放
     *
     * @return
     */
    public int resume() {
        return mSpeechSynthesizer.resume();
    }

    /**
     * 停止播放
     *
     * @return
     */
    public int stop() {
        return mSpeechSynthesizer.stop();
    }

    /**
     * 引擎在合成时该方法不能调用！！！
     * 注意 只有 TtsMode.MIX 才可以切换离线发音
     *
     * @param voiceType OfflineResource.VOICE_MALE 或者 OfflineResource.VOICE_FEMALE
     * @return
     */
    public int loadModel(String voiceType) {
        if (offlineResource == null) {
            throw new RuntimeException("offlineResource = null， 注意只有 初始化选TtsMode.MIX才可以切换离线发音");
        }
        int res = -9999;
        try {
            offlineResource.setOfflineVoiceType(voiceType);
            res = mSpeechSynthesizer.loadModel(offlineResource.getModelFilename(), offlineResource.getTextFilename());
        } catch (IOException e) {
            throw new RuntimeException("切换离线发音人失败", e);
        }
        String voiceStr = voiceType.equals(OfflineResource.VOICE_FEMALE) ? "离线女声" : "离线男声";
        Log.i(TAG, "切换离线发音人成功。当前发音人：" + voiceStr);
        return res;
    }

    /**
     * 设置播放音量，默认已经是最大声音
     * 0.0f为最小音量，1.0f为最大音量
     *
     * @param leftVolume  [0-1] 默认1.0f
     * @param rightVolume [0-1] 默认1.0f
     */
    public void setStereoVolume(float leftVolume, float rightVolume) {
        mSpeechSynthesizer.setStereoVolume(leftVolume, rightVolume);
    }

    /**
     * 如果不使用了, 释放资源
     */
    private void release() {
        Log.i(TAG, "百度语音释放资源");
        if (mSpeechSynthesizer != null) {
            mSpeechSynthesizer.stop();
            mSpeechSynthesizer.release();
            mSpeechSynthesizer = null;
            isInitied = false;
        }
    }

    public void releaseSafe() {
        runInHandlerThread(RELEASE);
    }

    /**
     * 设置参数
     *
     * @param params
     */
    public void setParams(Map<String, String> params) {
        if (params != null) {
            for (Map.Entry<String, String> e : params.entrySet()) {
                mSpeechSynthesizer.setParam(e.getKey(), e.getValue());
            }
        }
    }

    /**
     * 合成的参数，可以初始化时填写，也可以在合成前设置。
     *
     * @return
     */
    protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();
        // 以下参数均为选填
        params.put(SpeechSynthesizer.PARAM_SPEAKER, "0"); // 设置在线发声音人： 0 普通女声（默认） 1 普通男声 2 特别男声 3 情感男声<度逍遥> 4 情感儿童声<度丫丫>
        params.put(SpeechSynthesizer.PARAM_VOLUME, "9"); // 设置合成的音量，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_SPEED, "5");// 设置合成的语速，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_PITCH, "5");// 设置合成的语调，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_DEFAULT);         // 该参数设置为TtsMode.MIX生效。即纯在线模式不生效。
        // MIX_MODE_DEFAULT 默认 ，wifi状态下使用在线，非wifi离线。在线状态下，请求超时6s自动转离线
        // MIX_MODE_HIGH_SPEED_SYNTHESIZE_WIFI wifi状态下使用在线，非wifi离线。在线状态下， 请求超时1.2s自动转离线
        // MIX_MODE_HIGH_SPEED_NETWORK ， 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线
        // MIX_MODE_HIGH_SPEED_SYNTHESIZE, 2G 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线
        return params;
    }
}
