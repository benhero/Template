package com.ben.template.function.ipc.aidl

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.os.IBinder.DeathRecipient
import android.os.RemoteException
import android.util.Log
import com.ben.template.aidl.pool.IBinderPool
import java.util.concurrent.CountDownLatch

/**
 * Aidl连接池
 *
 * @author Benhero
 * @date   2021/7/7
 */
class BinderPool private constructor(context: Context) {

    private val mContext: Context = context.applicationContext
    private var mBinderPool: IBinderPool? = null
    private var mConnectBinderPoolCountDownLatch: CountDownLatch? = null

    private val mBinderPoolConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            Log.i("JKL", "BinderPool - onServiceConnected: ")
            mBinderPool = IBinderPool.Stub.asInterface(service)
            try {
                mBinderPool!!.asBinder().linkToDeath(mBinderPoolDeathRecipient, 0)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
            mConnectBinderPoolCountDownLatch!!.countDown()
        }

        override fun onServiceDisconnected(name: ComponentName) {}
    }

    private val mBinderPoolDeathRecipient: DeathRecipient = object : DeathRecipient {
        override fun binderDied() {
            mBinderPool!!.asBinder().unlinkToDeath(this, 0)
            mBinderPool = null
            connectBinderPoolService()
        }
    }

    init {
        connectBinderPoolService()
    }

    private fun connectBinderPoolService() {
        mConnectBinderPoolCountDownLatch = CountDownLatch(1)
        val service = Intent(mContext, AidlPoolService::class.java)
        mContext.bindService(service, mBinderPoolConnection, Context.BIND_AUTO_CREATE)
        try {
            mConnectBinderPoolCountDownLatch!!.await()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    fun queryBinder(binderCode: Int): IBinder? {
        var binder: IBinder? = null
        if (mBinderPool != null) {
            try {
                binder = mBinderPool!!.queryBiner(binderCode)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }
        return binder
    }

    class BinderPoolImpl : IBinderPool.Stub() {

        /**
         * Binder返回Binder
         */
        @Throws(RemoteException::class)
        override fun queryBiner(binderCode: Int): IBinder? {
            var binder: IBinder? = null
            when (binderCode) {
                BINDER_PLUS -> {
                    binder = PlusImpl()
                }
                BINDER_MINUS -> {
                    binder = MinusImpl()
                }
            }
            return binder
        }
    }

    companion object {
        const val BINDER_NONE = -1
        const val BINDER_PLUS = 0
        const val BINDER_MINUS = 1

        @Volatile
        private var sInstance: BinderPool? = null

        fun getInstance(context: Context): BinderPool {
            if (sInstance == null) {
                synchronized(BinderPool::class.java) {
                    if (sInstance == null) {
                        sInstance = BinderPool(context)
                    }
                }
            }
            return sInstance!!
        }
    }
}
