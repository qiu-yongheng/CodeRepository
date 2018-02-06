package com.qyh.coderepository.menu.ping;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.NetworkUtils;
import com.qyh.coderepository.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author 邱永恒
 * @time 2018/2/6  22:52
 * @desc ${TODO}
 */
public class PingFragment extends Fragment implements View.OnClickListener {
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ping, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initListener();
        return view;
    }

    private void initView() {

    }

    private void initListener() {
        btnStart.setOnClickListener(this);
        btnClear.setOnClickListener(this);
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
                NetworkUtils.isAvailableByPing()
                break;
            case R.id.btn_clear:
                break;
        }
    }
}
