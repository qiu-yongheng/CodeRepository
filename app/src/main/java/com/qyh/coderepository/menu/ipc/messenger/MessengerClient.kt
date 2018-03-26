package com.qyh.coderepository.menu.ipc.messenger

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log

/**
 * @author 邱永恒
 *
 * @time 2018/3/17  15:43
 *
 * @desc ${TODO}
 *
 */

object MessengerClient {
    // 服务端返回的messenger对象
    private var serverMessenger: Messenger? = null
    private val messengerConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            // 获取服务端的binder
            serverMessenger = Messenger(service)
            val bundle = Bundle()
            bundle.putString("msg", "hello, this is client.")
            sendMsg(bundle)

        }
    }

    // 传递给服务端的messenger对象
    private val clientMessenger: Messenger = Messenger(MessengerHandler())
    class MessengerHandler : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            when (msg?.what) {
                MessengerService.MSG_FORM_SERVER -> {
                    Log.d(MessengerService.TAG, "收到服务端的信息: ${msg.data.getString("reply")}")

                    // 获取客户端接收回复的messenger
//                    val bundle = Bundle()
//                    bundle.putString("msg", "hello, this is client.")
//                    sendMsg(bundle)
                }
            }
        }
    }

    /**
     * 发送数据, 使用data存储数据, 因为object只能存储系统实现parcelable接口的对象, 自己实现的不行
     */
    fun sendMsg(bundle: Bundle) {
        val message = Message.obtain(null, MessengerService.MSG_FROM_CLIENT)
        message.data = bundle
        // 把客户端接收消息的messenger传递给服务端, 用来给服务端回复
        message.replyTo = clientMessenger

        serverMessenger?.send(message)
    }

    fun bindService(context: Context) {
        context.bindService(Intent(context, MessengerService::class.java), messengerConnection, Context.BIND_AUTO_CREATE)
    }

    fun unBindService(context: Context) {
        context.unbindService(messengerConnection)
    }
}