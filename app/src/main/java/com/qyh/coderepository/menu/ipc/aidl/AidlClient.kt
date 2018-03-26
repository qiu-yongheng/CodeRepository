package com.qyh.coderepository.menu.ipc.aidl

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import com.qyh.coderepository.IBookManager
import com.qyh.coderepository.IOnNewBookArrivedListener

/**
 * @author 邱永恒
 *
 * @time 2018/3/17  16:34
 *
 * @desc ${TODO}
 *
 */

object AidlClient {
    const val TAG = "AidlClient"
    private var iBookManager: IBookManager? = null
    private val listener = object : IOnNewBookArrivedListener.Stub() {
        /**
         * 运行在客户端的binder线程池, 不能访问UI
         */
        override fun onNewBookArrived(newBook: Book?) {
            Log.d(TAG, "图书馆新增: $newBook")
        }
    }
    private val clientServiceconnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {

        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            iBookManager = IBookManager.Stub.asInterface(service)
            iBookManager?.addBook(Book("算法导论", 120))
            iBookManager?.addBook(Book("Java编程思想", 50))
            iBookManager?.addBook(Book("IOS", 333))
            Log.d(TAG, "订阅监听器: $listener")
            iBookManager?.registerListener(listener)
            Log.d(TAG, "图书馆藏书: ${iBookManager?.bookList}")
            Log.d(TAG, "List类型: ${iBookManager!!.bookList::class.java.canonicalName}")
        }
    }

    fun bindService(context: Context) {
        context.bindService(Intent(context, AidlService::class.java), clientServiceconnection, Context.BIND_AUTO_CREATE)
    }

    fun unregister() {
        Log.d(TAG, "取消监听器: $listener")
        iBookManager?.unregisterListener(listener)
    }
}