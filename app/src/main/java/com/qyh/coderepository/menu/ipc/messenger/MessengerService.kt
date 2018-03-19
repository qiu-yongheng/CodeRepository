package com.qyh.coderepository.menu.ipc.messenger

import android.app.Service
import android.content.Intent
import android.os.*
import android.util.Log

/**
 * @author 邱永恒
 *
 * @time 2018/3/17  15:31
 *
 * @desc 服务端, 创建Messenger, 并返回binder给客户端
 *
 */

class MessengerService : Service(){
    companion object {
        val MSG_FROM_CLIENT = 10010
        val MSG_FORM_SERVER = 10011
        val TAG = "MessengerService"
    }
    private val messenger: Messenger = Messenger(MessengerHandler())
    override fun onBind(intent: Intent?): IBinder {
        return messenger.binder
    }

    class MessengerHandler : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            when (msg?.what) {
                MSG_FROM_CLIENT -> {
                    Log.d(TAG, "收到客户端的信息: ${msg.data.getString("msg")}")

                    // 获取客户端接收回复的messenger
                    val clientMessenger = msg.replyTo
                    val message = Message.obtain(null, MSG_FORM_SERVER)
                    val bundle = Bundle()
                    bundle.putString("reply", "你的消息我已经收到, 我稍后回复你")
                    message.data = bundle
                    clientMessenger.send(message)
                }
            }
        }
    }

}