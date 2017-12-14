package com.qyh.coderepository.menu.mvp;

import com.kc.common.base.BasePresenter;
import com.kc.common.base.BaseView;

/**
 * @author 邱永恒
 * @time 2017/12/14  22:01
 * @desc ${TODD}
 */


public interface MvpContract {
    interface View extends BaseView {
        void showResult(boolean isSuccess);
    }

    interface Presenter extends BasePresenter{
        void getResult();
    }
}
