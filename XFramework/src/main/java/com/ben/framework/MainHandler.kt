package com.ben.framework

import android.os.Handler
import android.os.Looper

/**
 * 默认Handler
 *
 * @author Benhero
 * @date   2020/9/27
 */
object MainHandler {
    private val handler: Handler = Handler(Looper.getMainLooper())

    fun post(runnable:()-> Unit) {
        handler.post(runnable)
    }

    fun postDelay(runnable:()-> Unit, delayMillis: Long) {
        handler.postDelayed(runnable, delayMillis)
    }
}