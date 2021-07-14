package com.ben.template.function.ipc.messenger

import android.app.Service
import android.content.Intent
import android.os.*
import android.util.Log
import com.ben.template.function.ipc.messenger.MessengerActivity.Companion.MSG_FROM_CLIENT
import com.ben.template.function.ipc.messenger.MessengerActivity.Companion.MSG_KEY

/**
 * 消息服务
 *
 * @author Benhero
 */
class MessengerService : Service() {

    private val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MSG_FROM_CLIENT -> {
                    // 分析消息
                    val msgReceive = msg.data.getString(MSG_KEY)

                    msgReceive?.let { Log.e("JKL", "服务器收到消息: $it") }

                    // 回应消息
                    val replyTo = msg.replyTo
                    val sendMsg = Message.obtain(null, MSG_FROM_SERVICE)
                    val bundle = Bundle()
                    bundle.putString(MSG_KEY, "你好,我是服务器")
                    sendMsg.data = bundle
                    try {
                        replyTo.send(sendMsg)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                else -> {
                }
            }
        }
    }

    private val messenger = Messenger(handler)

    override fun onBind(intent: Intent): IBinder {
        return messenger.binder
    }

    companion object {
        const val MSG_FROM_SERVICE = 1555
    }
}