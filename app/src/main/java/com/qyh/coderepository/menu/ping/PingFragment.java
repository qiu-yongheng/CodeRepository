package com.qyh.coderepository.menu.ping;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.kc.common.base.ActivityLifeCycleEvent;
import com.kc.common.util.PingUtil;
import com.kc.common.util.toast.ToastManager;
import com.qyh.coderepository.R;
import com.qyh.coderepository.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.subjects.PublishSubject;

/**
 * @author 邱永恒
 * @time 2018/2/6  22:52
 * @desc ${TODO}
 */
public class PingFragment extends BaseFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, PingUtil.onPingSendListener {
    @BindView(R.id.et_url)
    EditText etUrl;
    @BindView(R.id.check_box)
    CheckBox checkBox;
    @BindView(R.id.btn_start)
    Button btnStart;
    @BindView(R.id.btn_clear)
    Button btnClear;
    @BindView(R.id.tv_log)
    TextView tvLog;
    Unbinder unbinder;
    private boolean isChecked;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ping, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initListener();
        PublishSubject<Object> objectPublishSubject = PublishSubject.create();
        return view;
    }

    private void initView() {
        etUrl.setText(TextUtils.isEmpty(PingUtil.getInstance().getCacheHost()) ? "www.baidu.com" : PingUtil.getInstance().getCacheHost());
        tvLog.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    private void initListener() {
        btnStart.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        checkBox.setOnCheckedChangeListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                String host = etUrl.getText().toString();
                PingUtil.getInstance().saveHost(host);
                if (TextUtils.isEmpty(host)) {
                    ToastManager.getToast().tip("请输入host");
                } else {
                    tvLog.setText("");
                    PingUtil.getInstance().ping(host, isChecked ? 0 : 5, this, ActivityLifeCycleEvent.DESTROY, lifecycleSubject);
                }
                break;
            case R.id.btn_clear:
                tvLog.setText("");
                tvLog.scrollTo(0, 0);
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        this.isChecked = isChecked;
    }

    @Override
    public void onNotify(String msg) {
        tvLog.append(msg);
        // 行数 * 行高
        int offset = tvLog.getLineCount() * tvLog.getLineHeight();
        // 判断实际高度是否大于屏幕高度, 如果大于, 滚动到最后一行
        if (offset > tvLog.getHeight()) {
            // 参数一: X轴偏移; 参数二: Y轴偏移
            tvLog.scrollTo(0, offset - tvLog.getHeight());
        }
    }
}
