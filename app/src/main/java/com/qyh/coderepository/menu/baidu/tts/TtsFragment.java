package com.qyh.coderepository.menu.baidu.tts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.qyh.coderepository.R;
import com.qyh.coderepository.baidu.tts.TtsUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author 邱永恒
 * @time 2018/1/18  15:21
 * @desc ${TODD}
 */

public class TtsFragment extends Fragment {
    @BindView(R.id.et_tts)
    EditText etTts;
    @BindView(R.id.btn_tts)
    Button btnTts;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tts, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        initListener();
        return view;
    }

    public void init() {
        TtsUtil.getInstance().init(getContext());
    }

    private void initListener() {
        btnTts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tts = etTts.getText().toString().trim();
                if (TextUtils.isEmpty(tts)) {
                    TtsUtil.getInstance().speak("请输入发言内容");
                } else {
                    TtsUtil.getInstance().speak(tts);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        TtsUtil.getInstance().releaseSafe();
    }
}
