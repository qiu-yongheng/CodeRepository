package com.qyh.coderepository.util.toast;

/**
 * @author 邱永恒
 * @time 2017/11/16  15:53
 * @desc ${TODD}
 */

public class ToastManager {
    private static IToast innerInstance;
    private static IToast externalInstance;

    public static void setToast(IToast toast) {
        if (externalInstance == null && toast != null) {
            externalInstance = toast;
        }
    }

    public static IToast getToast() {
        if (innerInstance == null) {
            synchronized (ToastManager.class) {
                if (innerInstance == null) {
                    if (externalInstance != null) {
                        innerInstance = externalInstance;
                    } else {
                        innerInstance = new ToastUtil();
                    }
                }
            }
        }
        return innerInstance;
    }
}
