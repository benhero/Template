package com.ben.template.function.aidl

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.RemoteCallbackList
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

    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }
}