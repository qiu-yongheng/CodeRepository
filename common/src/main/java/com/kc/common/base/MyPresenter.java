package com.kc.common.base;

import android.content.Context;

/**
 * @author 邱永恒
 * @time 2017/12/14  15:26
 * @desc ${TODD}
 */

public abstract class MyPresenter<V extends BaseView>{
    protected Context context;
    private V view;

    public MyPresenter(Context context, V view) {
        this.context = context;
        this.view = view;
    }

    public V getView() {
        return view;
    }
}
