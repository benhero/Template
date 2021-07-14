package com.ben.template.function.ipc.messenger

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ben.template.R
import com.ben.template.function.ipc.messenger.MessengerService.Companion.MSG_FROM_SERVICE
import kotlinx.android.synthetic.main.activity_messenger.*

/**
 * Messenger通讯
 *
 * @author Benhero
 */
class MessengerActivity : AppCompatActivity() {

    /**
     * 回应信使的消息处理
     */
    private val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                MSG_FROM_SERVICE -> {
                    val get = msg.data.get(MSG_KEY)
                    Log.i("JKL", "客户端收到消息: $get")
                }
                else -> {
                }
            }
        }
    }

    /**
     * 发送的信使
     */
    private var sendMessenger: Messenger? = null

    /**
     * 回应的信使
     */
    private val replyMessenger = Messenger(handler)


    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.i("JKL", "客户端: 发送信使绑定服务")
            // 发送信使绑定服务
            sendMessenger = Messenger(service)
        }

        override fun onServiceDisconnected(name: ComponentName?) {

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messenger)
        // 绑定服务
        val intent = Intent(this, MessengerService::class.java)
        bindService(intent, connection, BIND_AUTO_CREATE)

        messenger_send.setOnClickListener {
            val message: Message = Message.obtain(null, MSG_FROM_CLIENT)
            val bundle = Bundle()
            bundle.putString(MSG_KEY, "你好,我是客户端")
            message.data = bundle
            message.replyTo = replyMessenger
            sendMessenger?.send(message)
        }
    }

    override fun onDestroy() {
        unbindService(connection)
        super.onDestroy()
    }

    companion object {
        const val MSG_FROM_CLIENT = 1354

        const val MSG_KEY = "msg_key"
    }
}