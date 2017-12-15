package com.qyh.coderepository.menu.mvp;

import android.view.View;

import com.kc.common.base.BaseFragment;
import com.qyh.coderepository.R;
import com.kc.common.util.toast.ToastManager;

/**
 * @author 邱永恒
 * @time 2017/12/14  21:59
 * @desc ${TODD}
 */


public class MvpFragment extends BaseFragment<MvpContract.Presenter> implements MvpContract.View, View.OnClickListener {
    @Override
    public void showError(String msg) {
        ToastManager.getToast().tip(msg);
    }

    @Override
    public void useNightMode(boolean isNight) {

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_mvp;
    }

    @Override
    protected void init(View view) {
        view.findViewById(R.id.btn_test).setOnClickListener(this);
    }

    @Override
    protected void createPresenter() {
        presenter = new MvpPresenter(getContext(), this);
    }

    @Override
    public void onClick(View v) {
        presenter.getResult();
    }

    @Override
    public void showResult(boolean isSuccess) {
        ToastManager.getToast().tip("结果: " + isSuccess);
    }
}
