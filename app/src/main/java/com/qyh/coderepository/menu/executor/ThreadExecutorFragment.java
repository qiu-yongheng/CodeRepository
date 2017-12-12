package com.qyh.coderepository.menu.executor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.qyh.coderepository.R;
import com.qyh.coderepository.util.log.LoggerUtil;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    private ExecutorService executor;

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
                executor = Executors.newSingleThreadExecutor();
                for (int i = 1; i <= 10; i++) {
                    final int index = i;
                    executor.submit(new Runnable() {
                        @Override
                        public void run() {
                            String threadName = Thread.currentThread().getName();
                            LoggerUtil.d("线程：" + threadName + ",正在执行第" + index + "个任务");
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
//                            if (index == 1) {
//                                throw new RuntimeException("模拟报错");
//                            }
                        }
                    });
                }
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
