package com.ben.template.function.ipc.aidl

import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.IBinder
import android.os.Parcel
import android.os.RemoteCallbackList
import android.util.Log
import com.ben.template.aidl.Book
import com.ben.template.aidl.IBookManager
import com.ben.template.aidl.IOnNewBookArrivedListener
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Aidl服务
 *
 * @author Benhero
 */
class AidlService : Service() {
    companion object {
        const val PERMISSION_NAME = "com.ben.template.permission.ACCESS_BOOK_SERVICE"

        /**
         * 权限验证方式：
         * 1. 在连接时拦截
         * 2. 在数据处理时进行权限+包名的拦截
         */
        const val PERMISSION_CHECK_TYPE_1 = false
    }

    private val remoteBookList = CopyOnWriteArrayList<Book>()

    /**
     * 回调接口列表：RemoteCallbackList解决跨进程对象不同的问题，内部通过Binder对象相同来查找对象实现
     */
    private val listenerList = RemoteCallbackList<IOnNewBookArrivedListener>()

    override fun onCreate() {
        super.onCreate()
        remoteBookList.add(Book(0, "亲热天堂"))
    }

    private val binder = object : IBookManager.Stub() {
        override fun getBookList(): MutableList<Book> {
            return remoteBookList
        }

        override fun addBook(book: Book) {
            bookList.add(book)
            val count = listenerList.beginBroadcast()
            for (i in 0 until count) {
                listenerList.getBroadcastItem(i)?.onNewBookArrived(book)
            }
            listenerList.finishBroadcast()
        }

        override fun registerListener(l: IOnNewBookArrivedListener) {
            listenerList.register(l)
        }

        override fun unRegisterListener(l: IOnNewBookArrivedListener) {
            listenerList.unregister(l)
        }

        /**
         * 用这种方法判断权限只是AIDL接口方法会失效，客户端还是会得到访问客户端的binder
         */
        override fun onTransact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {
            if (!PERMISSION_CHECK_TYPE_1) {
                val check =
                    checkCallingOrSelfPermission(PERMISSION_NAME)
                if (check == PackageManager.PERMISSION_DENIED) {
                    return false
                }
                var packageName: String? = null

                //拿到调用者的包名
                val packages = packageManager.getPackagesForUid(getCallingUid())
                if (packages != null && packages.isNotEmpty()) {
                    packageName = packages[0]
                }
                if (!packageName!!.startsWith("com.ben.template")) {
                    return false
                }
            }
            return super.onTransact(code, data, reply, flags)
        }

    }

    override fun onBind(intent: Intent): IBinder? {
        if (PERMISSION_CHECK_TYPE_1) {
            val check =
                checkCallingOrSelfPermission(PERMISSION_NAME)
            if (check != PackageManager.PERMISSION_GRANTED) {
                Log.e("JKL", "客户端缺少必要权限!")
                return null
            }
        }
        return binder
    }
}