package com.qyh.coderepository.base;

import android.support.v4.app.Fragment;

import com.kc.common.base.ActivityLifeCycleEvent;
import com.kc.common.util.log.LoggerUtil;

import io.reactivex.subjects.PublishSubject;

/**
 * @author 邱永恒
 * @time 2018/2/7  16:57
 * @desc ${TODD}
 */

public class BaseFragment extends Fragment{
    public final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject = PublishSubject.create();

    @Override
    public void onDestroy() {
        super.onDestroy();
        LoggerUtil.d(this.getClass().getSimpleName() + " onDestroy()");
        lifecycleSubject.onNext(ActivityLifeCycleEvent.DESTROY);
    }
}
