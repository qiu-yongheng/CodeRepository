package com.kc.common.base;

/**
 * @author 邱永恒
 * @time 2017/12/14  15:26
 * @desc ${TODD}
 */

public interface BasePresenter<V extends BaseView> {
    void attachView(V view);

    void detachView();
}
