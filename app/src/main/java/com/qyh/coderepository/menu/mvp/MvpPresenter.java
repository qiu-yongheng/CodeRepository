package com.qyh.coderepository.menu.mvp;

import android.content.Context;

import com.kc.common.base.MyPresenter;

/**
 * @author 邱永恒
 * @time 2017/12/14  22:23
 * @desc ${TODD}
 */


public class MvpPresenter extends MyPresenter<MvpContract.View> implements MvpContract.Presenter {
    private int i = 0;

    MvpPresenter(Context context, MvpContract.View view) {
        super(context, view);
    }

    @Override
    public void getResult() {
        i++;
        if (i < 3) {
            getView().showError("出错, 请重试");
        } else {
            getView().showResult(true);
        }
    }

    @Override
    public void unSubscribe() {
        getCompositeDisposable().clear();
    }
}
