package com.kc.common.base;

import android.content.Context;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author 邱永恒
 * @time 2017/12/14  15:26
 * @desc ${TODD}
 */

public abstract class MyPresenter<V extends BaseView>{
    private CompositeDisposable compositeDisposable;
    protected Context context;
    private V view;

    public MyPresenter(Context context, V view, CompositeDisposable compositeDisposable) {
        this.context = context;
        this.view = view;
        this.compositeDisposable = compositeDisposable;
    }

    public V getView() {
        return view;
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }
}
