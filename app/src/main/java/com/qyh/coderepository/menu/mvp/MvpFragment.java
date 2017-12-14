package com.qyh.coderepository.menu.mvp;

import android.view.View;

import com.kc.common.base.BaseFragment;
import com.qyh.coderepository.R;

/**
 * @author 邱永恒
 * @time 2017/12/14  21:59
 * @desc ${TODD}
 */


public class MvpFragment extends BaseFragment<MvpContract.Presenter> implements MvpContract.View, View.OnClickListener {
    @Override
    public void showError(String msg) {

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

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void showResult(boolean isSuccess) {

    }
}
