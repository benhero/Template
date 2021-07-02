package com.ben.template.function.aidl

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ben.framework.MainHandler
import com.ben.template.R
import com.ben.template.aidl.Book
import com.ben.template.aidl.IBookManager
import com.ben.template.aidl.IOnNewBookArrivedListener
import kotlinx.android.synthetic.main.activity_aidl.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * AIDL页面
 *
 * @author Benhero
 */
class AidlActivity : AppCompatActivity() {
    var bookManager: IBookManager? = null
    var bookIndex = 0

    private val listener = object : IOnNewBookArrivedListener.Stub() {
        override fun onNewBookArrived(newBook: Book) {
            Log.i("JKL", "收到新书: " + newBook)
            MainHandler.post {
                book_list.text = book_list.text.toString() + "\n" + newBook
            }
        }
    }

    private val connection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            bookManager = IBookManager.Stub.asInterface(service)
            Log.i("JKL", "连接上服务器，书库包含了: " + bookManager?.bookList)
            var bookListString = ""
            bookManager?.bookList?.forEachIndexed { index, book ->
                bookListString += (if (index == 0) "" else "\n") + book
            }

            MainHandler.post {
                book_list.text = book_list.text.toString() + bookListString
            }

            bookManager?.registerListener(listener)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            bookManager?.unRegisterListener(listener)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aidl)
        val intent = Intent(this, AidlService::class.java)
        bindService(intent, connection, BIND_AUTO_CREATE)
        aidl_btn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                bookIndex++
                bookManager?.addBook(Book(bookIndex, "火影忍者$bookIndex"))
            }
        }
    }

    override fun onDestroy() {
        if (bookManager?.asBinder()?.isBinderAlive == true) {
            bookManager?.unRegisterListener(listener)
        }
        unbindService(connection)
        super.onDestroy()
    }
}