package com.qyh.coderepository.menu.ipc.aidl

import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.IBinder
import android.os.RemoteCallbackList
import android.os.SystemClock
import android.util.Log
import com.qyh.coderepository.IBookManager
import com.qyh.coderepository.IOnNewBookArrivedListener
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @author 邱永恒
 *
 * @time 2018/3/17  16:31
 *
 * @desc ${TODO}
 *
 */

class AidlService : Service() {
    companion object {
        val TAG = "AidlService"
    }

    /**
     * CopyOnWriteArrayList: 支持并发读/写
     * AIDL在服务端的Binder线程池中执行, 所以要处理线程同步问题
     */
    private val bookList: CopyOnWriteArrayList<Book> = CopyOnWriteArrayList()
    /**
     * service是否存活
     */
    private val isServiceDestoryed: AtomicBoolean = AtomicBoolean(false)
    /**
     * 保存客户端订阅的监听回调(多次跨进程同一个对象会在服务端生成多个不同的对象, 不能解绑listener, 需要替换方案)
     */
//    private val listenerList: CopyOnWriteArrayList<IOnNewBookArrivedListener> = CopyOnWriteArrayList()
    /**
     * RemoteCallbackList: 系统专门用于删除跨进程listener的接口
     * key: IBinder
     * value: callback
     * 原理: 进程的底层Binder对象是同一个, 可以根据这个来删除
     */
    private val listenerList: RemoteCallbackList<IOnNewBookArrivedListener> = RemoteCallbackList()

    override fun onBind(intent: Intent?): IBinder? {
        val check = checkCallingOrSelfPermission("com.qyh.coderepository.ACCESS_BOOK_SERVICE")
        if (check == PackageManager.PERMISSION_DENIED) {
            Log.d(TAG, "没有权限使用该AIDL服务")
            return null
        }
        return AidlBinder()
    }

    override fun onCreate() {
        super.onCreate()
        // 每10秒新增一本书, 通知订阅者
        Thread(Runnable {
            while (!isServiceDestoryed.get()) {
                SystemClock.sleep(5000)
                val bookId = bookList.size + 1
                val book = Book("new book#$bookId", bookId)
                onNewBookArrived(book)
            }
        }).start()
    }

    override fun onDestroy() {
        isServiceDestoryed.set(true)
        super.onDestroy()
    }

    /**
     * 遍历所有监听器, 回调
     */
    private fun onNewBookArrived(book: Book) {
        bookList.add(book)
        // beginBroadcast和finishBroadcast必须配套使用
        val n = listenerList.beginBroadcast()
        for (i in 0 until n) {
            listenerList.getBroadcastItem(i).onNewBookArrived(book)
        }
        listenerList.finishBroadcast()
    }

    inner class AidlBinder : IBookManager.Stub() {
        override fun registerListener(listener: IOnNewBookArrivedListener?) {
            listenerList.register(listener)
        }

        override fun unregisterListener(listener: IOnNewBookArrivedListener?) {
            listenerList.unregister(listener)
        }

        override fun getBookList(): MutableList<Book> {
            return this@AidlService.bookList
        }

        override fun addBook(book: Book?) {
            this@AidlService.bookList.add(book!!)
        }

    }
}