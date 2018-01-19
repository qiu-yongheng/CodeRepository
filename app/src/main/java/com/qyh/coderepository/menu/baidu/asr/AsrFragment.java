package com.qyh.coderepository.menu.baidu.asr;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qyh.coderepository.R;
import com.qyh.coderepository.app.App;
import com.qyh.coderepository.baidu.asr.control.MyRecognizer;
import com.qyh.coderepository.baidu.asr.recognization.ChainRecogListener;
import com.qyh.coderepository.baidu.asr.recognization.IRecogListener;
import com.qyh.coderepository.baidu.asr.recognization.MessageStatusRecogListener;
import com.qyh.coderepository.baidu.asr.recognization.NluResult;
import com.qyh.coderepository.baidu.asr.recognization.RecogResult;
import com.qyh.coderepository.baidu.asr.recognization.offline.OfflineRecogParams;
import com.qyh.coderepository.baidu.asr.recognization.online.OnlineRecogParams;
import com.qyh.coderepository.baidu.asr.ui.BaiduASRDigitalDialog;
import com.qyh.coderepository.baidu.asr.ui.DigitalDialogInput;
import com.qyh.coderepository.baidu.asr.util.Logger;
import com.qyh.coderepository.baidu.tts.TtsUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

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

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.obj != null) {
                tvLog.append(msg.obj.toString() + "\n");
            }
        }
    };
    private MyRecognizer myRecognizer;
    protected boolean enableOffline = true;
    private ChainRecogListener chainRecogListener;
    private boolean running;


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


    private void initAsr() {
        Logger.setHandler(handler);
        chainRecogListener = new ChainRecogListener();
        chainRecogListener.addListener(new MessageStatusRecogListener(handler));
        chainRecogListener.addListener(new IRecogListener() {
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

            @Override
            public void onAsrOnlineNluResult(String nluResult) {
                NluResult result = NluResult.parseJson(nluResult);
                if (result.getDomain().equals("app") && result.getIntent().equals("open")) {
                    TtsUtil.getInstance().speak("好的, 我正在" + result.getRawText());
                } else if (result.getDomain().equals("app") && result.getIntent().equals("uninstall")) {
                    TtsUtil.getInstance().speak("我没有权限去做这件事");
                } else if (result.getRawText().contains("傻逼")) {
                    TtsUtil.getInstance().speak("是啊, 汪明是傻逼");
                } else {
                    TtsUtil.getInstance().speak("傻逼, 不知道你在说什么");
                }
            }

            @Override
            public void onOfflineLoaded() {

            }

            @Override
            public void onOfflineUnLoaded() {

            }
        });
        myRecognizer = new MyRecognizer(getContext(), chainRecogListener);
        if (enableOffline) {
            myRecognizer.loadOfflineEngine(OfflineRecogParams.fetchOfflineParams());
        }
    }

    private void initListener() {
        /** 开始 */
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvLog.setText("");

                // BaiduASRDigitalDialog的输入参数
                DigitalDialogInput input = new DigitalDialogInput(myRecognizer, chainRecogListener, OnlineRecogParams.fetchOnlineParams(true));
                Intent intent = new Intent(getContext(), BaiduASRDigitalDialog.class);
                // 在BaiduASRDialog中读取
                ((App) getContext().getApplicationContext()).setDigitalDialogInput(input);
                running = true;
                startActivityForResult(intent, 2);
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
}
