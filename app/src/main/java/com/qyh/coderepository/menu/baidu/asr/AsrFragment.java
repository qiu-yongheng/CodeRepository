package com.qyh.coderepository.menu.baidu.asr;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.speech.asr.SpeechConstant;
import com.qyh.coderepository.R;
import com.qyh.coderepository.app.App;
import com.qyh.coderepository.baidu.asr.control.MyRecognizer;
import com.qyh.coderepository.baidu.asr.control.MyWakeup;
import com.qyh.coderepository.baidu.asr.recognization.ChainRecogListener;
import com.qyh.coderepository.baidu.asr.recognization.EventRecogListener;
import com.qyh.coderepository.baidu.asr.recognization.MessageStatusRecogListener;
import com.qyh.coderepository.baidu.asr.recognization.NluResult;
import com.qyh.coderepository.baidu.asr.recognization.RecogResult;
import com.qyh.coderepository.baidu.asr.recognization.offline.OfflineRecogParams;
import com.qyh.coderepository.baidu.asr.recognization.online.OnlineRecogParams;
import com.qyh.coderepository.baidu.asr.ui.BaiduASRDigitalDialog;
import com.qyh.coderepository.baidu.asr.ui.DigitalDialogInput;
import com.qyh.coderepository.baidu.asr.util.Logger;
import com.qyh.coderepository.baidu.asr.wakeup.IWakeupListener;
import com.qyh.coderepository.baidu.asr.wakeup.RecogWakeupListener;
import com.qyh.coderepository.baidu.tts.TtsUtil;
import com.qyh.coderepository.menu.baidu.asr.domain.AppDomain;
import com.qyh.coderepository.menu.baidu.asr.domain.Domain;
import com.qyh.coderepository.menu.baidu.asr.domain.NormalDomain;
import com.qyh.coderepository.menu.baidu.asr.domain.WeatherDomain;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.qyh.coderepository.baidu.asr.recognization.IStatus.STATUS_WAKEUP_SUCCESS;

/**
 * @author 邱永恒
 * @time 2018/1/18  15:59
 * @desc ${TODD}
 */

public class AsrFragment extends Fragment {
    @BindView(R.id.btn_stop)
    Button btnStop;
    @BindView(R.id.btn_start)
    Button btnStart;
    @BindView(R.id.ll_btn)
    LinearLayout llBtn;
    @BindView(R.id.tv_log)
    TextView tvLog;
    Unbinder unbinder;
    /**
     * 0: 方案1， 唤醒词说完后，直接接句子，中间没有停顿。
     * >0 : 方案2： 唤醒词说完后，中间有停顿，然后接句子。推荐4个字 1500ms
     * <p>
     * backTrackInMs 最大 15000，即15s
     */
    private int backTrackInMs = 1500;

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.obj != null && tvLog != null) {
                tvLog.append(msg.obj.toString() + "\n");
            }

            if (msg.what == STATUS_WAKEUP_SUCCESS) {
                // 此处 开始正常识别流程
                myRecognizer.cancel();
                startAsr(OnlineRecogParams.fetchOnlineParams(true));
            }
        }
    };
    private MyRecognizer myRecognizer;
    protected boolean enableOffline = true;
    private ChainRecogListener chainRecogListener;
    private boolean running;
    private MyWakeup myWakeup;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_asr, container, false);
        unbinder = ButterKnife.bind(this, view);
        initAsr();
        initListener();
        TtsUtil.getInstance().init(getContext());
        return view;
    }

    /**
     * 初始化语音识别:
     * 1. 创建识别监听器
     * 2. 创建MyRecognizer对象, 把监听器传入
     * 3. 根据是否需要使用离线识别词, 去初始化离线识别
     */
    private void initAsr() {
        /** 语音识别 */
        Logger.setHandler(handler);
        chainRecogListener = new ChainRecogListener();
        chainRecogListener.addListener(new MessageStatusRecogListener(handler));
        chainRecogListener.addListener(eventRecogListener);
        myRecognizer = new MyRecognizer(getContext(), chainRecogListener);
        if (enableOffline) {
            myRecognizer.loadOfflineEngine(OfflineRecogParams.fetchOfflineParams());
        }

        /** 语音唤醒 */
        IWakeupListener listener = new RecogWakeupListener(handler);
        myWakeup = new MyWakeup(getContext(), listener);
    }

    private void initListener() {
        /** 开始 */
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvLog.setText("");
                startWakeup();
            }
        });

        /** 停止 */
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRecognizer.stop();
            }
        });
    }

    /**
     * 启动语音识别
     *
     * @param params
     */
    private void startAsr(Map<String, Object> params) {
        // BaiduASRDigitalDialog的输入参数
        DigitalDialogInput input = new DigitalDialogInput(myRecognizer, chainRecogListener, params);
        Intent intent = new Intent(getContext(), BaiduASRDigitalDialog.class);
        // 在BaiduASRDialog中读取
        ((App) getContext().getApplicationContext()).setDigitalDialogInput(input);
        running = true;
        startActivityForResult(intent, 2);
    }

    /**
     * 启动语音唤醒
     */
    private void startWakeup() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(SpeechConstant.WP_WORDS_FILE, "assets:///WakeUp.bin");
        myWakeup.start(params);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("==", "返回");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (myRecognizer != null) {
            myRecognizer.release();
        }
    }

    /**
     * 处理语义
     */
    EventRecogListener eventRecogListener = new EventRecogListener() {
        @Override
        public void onAsrOnlineNluResult(String nluResult) {
            super.onAsrOnlineNluResult(nluResult);
            NluResult result = NluResult.parseJson(nluResult);
            Domain domain = new NormalDomain(result);
            if (!TextUtils.isEmpty(result.getDomain())) {
                switch (result.getDomain()) {
                    case "weather":
                        domain = new WeatherDomain(result);
                        break;
                    case "app":
                        domain = new AppDomain(result);
                        break;
                }
            }

            domain.handle();
        }

        @Override
        public void onAsrFinalResult(String[] results, RecogResult recogResult) {
            super.onAsrFinalResult(results, recogResult);
            String result = recogResult.getOrigalJson();

            if (result.contains("抢麦")) {
                TtsUtil.getInstance().speak("抢麦成功");
            } else if (result.contains("放麦")) {
                TtsUtil.getInstance().speak("放麦成功");
            } else if (result.contains("紧急抢麦")) {
                TtsUtil.getInstance().speak("紧急抢麦成功");
            } else if (result.contains("sos") || result.contains("SOS")) {
                TtsUtil.getInstance().speak("sos发送成功");
            } else if (result.contains("求救")) {
                TtsUtil.getInstance().speak("sos发送成功");
            }
        }
    };
}
