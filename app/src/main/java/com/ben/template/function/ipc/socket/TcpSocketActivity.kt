package com.ben.template.function.ipc.socket

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.os.SystemClock
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ben.template.R
import kotlinx.android.synthetic.main.activity_tcp_socket.*
import java.io.*
import java.net.Socket
import java.text.SimpleDateFormat
import java.util.*

/**
 * Socket服务页面
 *
 * @author Benhero
 * @date   2021/6/29
 */
class TcpSocketActivity : AppCompatActivity(), View.OnClickListener {
    private var mClientSocket: Socket? = null

    @SuppressLint("HandlerLeak")
    private val mHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MESSAGE_SOCKET_CONNECTED -> {
                    socket_like_btn.isEnabled = true
                    socket_coin_btn.isEnabled = true
                    socket_collect_btn.isEnabled = true
                }
                MESSAGE_RECEIVE_NEW_MSG ->
                    socket_text.text = socket_text.text.toString() + msg.obj as String
                else -> {
                }
            }
        }
    }
    private var mPrintWrite: PrintWriter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tcp_socket)
        //绑定服务
        val intent = Intent(this, TCPServerService::class.java)
        startService(intent)
        object : Thread() {
            override fun run() {
                connectTCPServer()
            }
        }.start()
        socket_like_btn.setOnClickListener(this)
        socket_coin_btn.setOnClickListener(this)
        socket_collect_btn.setOnClickListener(this)
    }

    /**
     * 连接Socket
     */
    private fun connectTCPServer() {
        // 连接服务器
        var socket: Socket? = null
        while (socket == null) {
            // 循环尝试连接服务器
            Log.w("JKL", "客户端: Create - 尝试连接服务器")
            try {
                // 创建套接字
                socket = Socket("localhost", 8868)
                mClientSocket = socket
                // 发送数据
                mPrintWrite =
                    PrintWriter(BufferedWriter(OutputStreamWriter(socket.getOutputStream())), true)
                mHandler.sendEmptyMessage(MESSAGE_SOCKET_CONNECTED)
                Log.i("JKL", "客户端: Connect - 连接服务器成功!")
            } catch (e: IOException) {
                SystemClock.sleep(1000)
                e.printStackTrace()
            }
        }

        //接收服务端的消息
        try {
            val br = BufferedReader(InputStreamReader(socket.getInputStream()))
            while (!this.isFinishing) {
                Log.i("JKL", "客户端: Wait - 等待服务器消息")
                // 读取服务器消息，阻塞类型
                val msg = br.readLine()
                Log.i("JKL", "客户端: Receive - 收到服务器消息 :$msg")
                if (msg != null) {
                    val time = formatDateTime(System.currentTimeMillis())
                    val showMsg = "server $time:$msg\n"
                    mHandler.obtainMessage(MESSAGE_RECEIVE_NEW_MSG, showMsg).sendToTarget()
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    //当Activity退出时则关闭当前Socket
    override fun onDestroy() {
        if (mClientSocket != null) {
            try {
                Log.e("JKL", "客户端: 退出, 关闭Socket")
                mClientSocket!!.shutdownInput()
                mClientSocket!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        super.onDestroy()
    }

    /**
     * 点击Btn发送信息
     * @param v
     */
    override fun onClick(v: View) {
        when (v) {
            socket_like_btn -> {
                send("点赞")
            }
            socket_coin_btn -> {
                send("投币")
            }
            socket_collect_btn -> {
                send("收藏")
            }
        }
    }

    private fun send(msg: String) {
        if (mPrintWrite != null) {
            object : Thread() {
                override fun run() {
                    mPrintWrite!!.println(msg)
                }
            }.start()
        }
    }

    private fun formatDateTime(l: Long): String {
        return SimpleDateFormat("HH:mm:ss").format(Date(l))
    }

    companion object {
        private const val MESSAGE_SOCKET_CONNECTED = 1
        private const val MESSAGE_RECEIVE_NEW_MSG = 2
    }
}