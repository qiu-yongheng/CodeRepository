package com.kc.common.util.path;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * @author 邱永恒
 * @time 2017/12/17  12:14
 * @desc 获取路径
 */

public class DirPathUtil {
    /**
     * 获取应用缓存路径
     * @param context
     * @return
     */
    public static String getDiskCacheDir(Context context) {
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }

    /**
     * 获取请求缓存路径
     * @param context
     * @return
     */
    public static File getOffHttpDir(Context context) {
        File file = new File(getDiskCacheDir(context), "okhttpcache");
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }

    /**
     * 获取录音文件路径
     *
     * @param context
     * @return
     */
    public static String getRecordDir(Context context) {
        return getDiskCacheDir(context) + "/" + "record";
    }
}
