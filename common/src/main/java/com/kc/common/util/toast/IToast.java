package com.kc.common.util.toast;

import android.content.Context;

/**
 * @author 邱永恒
 * @time 2017/11/16  15:54
 * @desc ${TODD}
 */

public interface IToast {
    void init(Context context);
    void tip(String tip);
}
