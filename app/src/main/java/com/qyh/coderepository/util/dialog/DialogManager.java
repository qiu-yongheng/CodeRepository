package com.qyh.coderepository.util.dialog;

/**
 * @author 邱永恒
 * @time 2017/11/16  15:53
 * @desc ${TODD}
 */

public class DialogManager {
    private static IDialog innerInstance;
    private static IDialog externalInstance;

    public static void setDialoger(IDialog dialoger) {
        if (externalInstance == null && dialoger != null) {
            externalInstance = dialoger;
        }
    }

    public static IDialog getDialoger() {
        if (innerInstance == null) {
            synchronized (DialogManager.class) {
                if (innerInstance == null) {
                    if (externalInstance != null) {
                        innerInstance = externalInstance;
                    } else {
                        innerInstance = new DialogUtil();
                    }
                }
            }
        }
        return innerInstance;
    }
}
