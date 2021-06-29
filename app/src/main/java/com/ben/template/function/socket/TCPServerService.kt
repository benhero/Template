package com.ben.template.function.socket

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.io.*
import java.net.ServerSocket
import java.net.Socket
import java.util.*


/**
 * Socket服务
 *
 * @author Benhero
 * @date   2021/6/29
 */
class TCPServerService : Service() {
    private var mIsServiceDestroyed = false
    private val mDefinedMessage = arrayOf(
        "给次机会我啊",
        "怎么给你机会？",
        "我以前没得选，现在我选回做好人",
        "好啊，你去跟法官说啊，看他给不给你当",
        "你这是让我死？",
        "对唔住，我是差人"
    )

    override fun onCreate() {
        Log.i("JKL", "TCPServerService - onCreate: ")
        Thread(TcpServer()).start()
        super.onCreate()
    }

    private inner class TcpServer : Runnable {
        override fun run() {
            val serverSocket = try {
                //监听本地8688接口
                ServerSocket(8868)
            } catch (e: IOException) {
                System.err.println("establish tcp server failed,port：8868")
                e.printStackTrace()
                return
            }
            while (!mIsServiceDestroyed) {
                try {
                    //接收客户请求
                    Log.i("JKL", "run: 接收客户请求")
                    val client = serverSocket.accept()
                    println("accept")
                    try {
                        responseClient(client)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun responseClient(client: Socket) {
        Log.i("JKL", "响应客户端")
        //用于接收客户消息
        val input = BufferedReader(InputStreamReader(client.getInputStream()))
        //用于向客户发送信息
        val out = PrintWriter(
            BufferedWriter(OutputStreamWriter(client.getOutputStream())),
            true
        )
        out.println("欢迎来到聊天室！")
        while (!mIsServiceDestroyed) {
            val str = input.readLine()
            println("msg from client:$str")
            if (str == null) {
                //客户端断开连接
                break
            }
            val i = Random().nextInt(mDefinedMessage.size)
            val msg = mDefinedMessage[i]
            out.println("收到《$str》")
            out.println(msg)
            println("send :$msg")
        }
        //客户端退出的操作
        println("client quit")
        //关闭流
        out.close()
        input.close()
        client.close()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        mIsServiceDestroyed = true
        super.onDestroy()
    }
}