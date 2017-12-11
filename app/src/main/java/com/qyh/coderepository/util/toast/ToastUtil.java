package com.qyh.coderepository.util.toast;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * @author 邱永恒
 * @time 2017/8/29  9:28
 * @desc 吐司工具类
 */

public class ToastUtil implements IToast{
    private Toast mToast;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Context mContext;

    public void init(Context context) {
        mContext = context;
    }

    public void tip(final String tip) {
        if (mContext == null) {
            throw new NullPointerException("ToastUtil is no init, please init in application");
        }

        runOnMainThread(new Runnable() {
            @Override
            public void run() {
                if (mToast == null) {
                    mToast = Toast.makeText(mContext, tip, Toast.LENGTH_SHORT);
                } else {
                    mToast.setText(tip);
                    mToast.setDuration(Toast.LENGTH_SHORT);
                }
                mToast.show();
            }
        });
    }

    private void runOnMainThread(Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
        } else {
            handler.post(runnable);
        }
    }
}
