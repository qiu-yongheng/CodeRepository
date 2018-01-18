package com.kc.common.util;

import com.google.gson.Gson;

/**
 * @author 邱永恒
 * @time 2017/12/29  17:03
 * @desc ${TODD}
 */

public class GsonUtil {
    private static Gson gson;

    public static Gson gson() {
        if (gson == null) {
            synchronized (Gson.class) {
                if (gson == null) {
                    gson = new Gson();
                }
            }
        }
        return gson;
    }
}
