package com.ben.template.function.ipc.aidl

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * Aidl连接池
 *
 * @author Benhero
 * @date   2021/7/6
 */
class AidlPoolService : Service() {

    override fun onBind(intent: Intent?): IBinder {
        return BinderPool.BinderPoolImpl()
    }
}