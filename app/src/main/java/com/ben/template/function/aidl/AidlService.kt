package com.ben.template.function.aidl

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.ben.template.aidl.Book
import com.ben.template.aidl.IBookManager
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Aidl服务
 *
 * @author Benhero
 */
class AidlService : Service() {

    private val remoteBookList = CopyOnWriteArrayList<Book>()

    override fun onCreate() {
        super.onCreate()
        remoteBookList.add(Book(0, "亲热天堂"))
    }

    private val binder = object : IBookManager.Stub() {
        override fun getBookList(): MutableList<Book> {
            Log.i("JKL", "AidlService - getBookList: ${Thread.currentThread().name}")
            return remoteBookList
        }

        override fun addBook(book: Book) {
            bookList.add(book)
        }

    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }
}