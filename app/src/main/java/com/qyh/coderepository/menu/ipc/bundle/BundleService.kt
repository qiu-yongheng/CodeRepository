package com.qyh.coderepository.menu.ipc.bundle

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

/**
 * @author 邱永恒
 *
 * @time 2018/3/17  11:14
 *
 * @desc ${TODO}
 *
 */

class BundleService : Service(){
    companion object {
        val TAG = "BundleService"
        val DATA_KEY = "BUNDLE_KEY"
    }
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val bundle = intent?.extras
         Log.d(TAG, "进程间通信: ${bundle?.getString(DATA_KEY)}")
        return super.onStartCommand(intent, flags, startId)
    }
}