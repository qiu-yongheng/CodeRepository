package com.qyh.coderepository.menu.ipc.file

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.ObjectInputStream

/**
 * @author 邱永恒
 *
 * @time 2018/3/17  11:32
 *
 * @desc ${TODO}
 *
 */

class FileService : Service(){
    companion object {
        val TAG = "FileService"
    }
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        recoverFromFile()
        return super.onStartCommand(intent, flags, startId)
    }

    /**
     * 反序列化
     */
    private fun recoverFromFile() = Thread(Runnable {
        val file = File(cacheDir.path + File.separator + "ipc", "Book")
        if (file.exists()) {
            var objectInputStream: ObjectInputStream? = null
            try {
                objectInputStream = ObjectInputStream(FileInputStream(file))
                val book = objectInputStream.readObject() as Book
                Log.d(TAG, "反序列化: $book")
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                objectInputStream?.close()
            }
        }
    }).start()
}