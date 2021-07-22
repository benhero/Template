package com.ben.template.function.thread

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ben.template.R
import kotlinx.android.synthetic.main.activity_thread_status.*
import java.util.concurrent.locks.LockSupport
import kotlin.concurrent.thread

/**
 * 线程状态页面
 *
 * @author Benhero
 * @date 2021-7-22
 * https://mp.weixin.qq.com/s?__biz=Mzk0MjE3NDE0Ng==&mid=2247494601&idx=1&sn=0b14d6cfa65c349fa0785422dd6e54bc&chksm=c2c59164f5b21872008fe57abb51137e6cab446fc8a0e1fffb2a56dcb3c9e7443184f72ebd69&scene=178&cur_album_id=1703494881072955395#rd
 */
class ThreadStatusActivity : AppCompatActivity(), View.OnClickListener {
    private val lock = Object()
    var waitKey = false
    var loopKey = false
    var joinKey = false
    var parkKey = false

    private val defaultThread = Thread {
        Log.i("JKL", "Start: ${Thread.currentThread().name}")
        synchronized(lock) {
            while (!loopKey) {
                if (waitKey) {
                    waitKey = false
                    Log.w("JKL", "Waiting: ${Thread.currentThread().name}")
                    lock.wait()
                }
                Thread.sleep(1000)
                Log.i("JKL", "Looping: ${Thread.currentThread().name}")

                if (parkKey) {
                    parkKey = false
                    Log.w("JKL", "Park: ${Thread.currentThread().name}")
                    LockSupport.park()
                }

                if (joinKey) {
                    // 判断是否需要被插队
                    if (joinThread.isAlive) {
                        joinThread.join()
                    }
                    joinKey = false
                }
            }
        }
        Log.e("JKL", "Stop: =============== ${Thread.currentThread().name}")
    }.apply {
        name = "Default"
    }

    private val joinThread = Thread {
        Log.i("JKL", "Start --- ${Thread.currentThread().name}")
        for (i in 0..5) {
            Thread.sleep(1000)
            Log.i("JKL", "Looping --- ${Thread.currentThread().name}")
        }
        Log.i("JKL", "Stop --- ${Thread.currentThread().name}")
    }.apply { name = "Join" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thread_status)
        defaultThread.start()

        thread_status_btn_0.setOnClickListener(this)
        thread_status_btn_1.setOnClickListener(this)
        thread_status_btn_2.setOnClickListener(this)
        thread_status_btn_3.setOnClickListener(this)
        thread_status_btn_4.setOnClickListener(this)
        thread_status_btn_5.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            thread_status_btn_0 -> {
                waitKey = true
            }
            thread_status_btn_1 -> {
                thread(true) {
                    synchronized(lock) {
                        Log.i("JKL", "NotifyAll From : ${Thread.currentThread().name}")
                        lock.notifyAll()
                    }
                }
            }
            thread_status_btn_2 -> {
                loopKey = true
            }
            thread_status_btn_3 -> {
                joinThread.start()
                joinKey = true
            }
            thread_status_btn_4 -> {
                parkKey = true
            }
            thread_status_btn_5 -> {
                Log.w("JKL", "UnPark : ${defaultThread.name}")
                LockSupport.unpark(defaultThread)
            }
            else -> {
            }
        }
    }

}