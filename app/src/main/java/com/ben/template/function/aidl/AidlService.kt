package com.ben.template.function.aidl

import android.app.Service
import android.content.Intent
import android.os.IBinder
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
    private val listenerList = CopyOnWriteArrayList<IOnNewBookArrivedListener>()

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
            listenerList.forEach {
                it.onNewBookArrived(book)
            }
        }

        override fun registerListener(l: IOnNewBookArrivedListener) {
            if (!listenerList.contains(l)) {
                listenerList.add(l)
            }
        }

        override fun unRegisterListener(l: IOnNewBookArrivedListener) {
            listenerList.remove(l)
        }

    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }
}