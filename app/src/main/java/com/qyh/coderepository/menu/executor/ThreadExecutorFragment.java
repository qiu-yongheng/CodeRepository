package com.qyh.coderepository.menu.executor;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.qyh.coderepository.R;
import com.kc.common.util.log.LoggerUtil;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author 邱永恒
 * @time 2017/12/12  20:12
 * @desc ${TODD}
 */


public class ThreadExecutorFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.btn_start)
    Button btnStart;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_reload)
    Button btnReload;
    @BindView(R.id.btn_clear)
    Button btnClear;
    Unbinder unbinder;
    private Executor executor;
    private int index = 0;

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LoggerUtil.d("绑定service");
            s = ((SerialService.SerialBinder) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LoggerUtil.d("断开service");
        }
    };
    private SerialService s;
    private Intent intent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thread, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initListener();
        intent = new Intent(getContext(), SerialService.class);
        getActivity().startService(intent);
        getActivity().bindService(intent, connection, Service.BIND_AUTO_CREATE);
    }

    private void initView() {

    }

    private void initListener() {
        btnAdd.setOnClickListener(this);
        btnStart.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnReload.setOnClickListener(this);
        btnClear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                if (executor == null) {
//                    executor = Executors.newSingleThreadExecutor();
                                        executor = new SerialExecutor(Executors.newSingleThreadExecutor());
                }

                if (s != null) {
                    s.addQueue("net");
                }

                break;
            case R.id.btn_start:
                s.addList("6");
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        getActivity().unbindService(connection);
        if (intent != null) {
            getActivity().stopService(intent);
        }
    }

}
