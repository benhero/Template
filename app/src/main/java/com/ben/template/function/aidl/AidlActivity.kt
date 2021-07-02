package com.ben.template.function.aidl

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ben.template.R
import com.ben.template.aidl.Book
import com.ben.template.aidl.IBookManager
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

    private val connection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            bookManager = IBookManager.Stub.asInterface(service)
            Log.i("JKL", "onServiceConnected: " + bookManager?.bookList)
        }

        override fun onServiceDisconnected(name: ComponentName?) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aidl)
        val intent = Intent(this, AidlService::class.java)
        bindService(intent, connection, BIND_AUTO_CREATE)
        aidl_btn.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                bookIndex++
                bookManager?.addBook(Book(bookIndex, "火影忍者$bookIndex"))
                Log.i("JKL", "onServiceConnected: " + bookManager?.bookList)
            }
        }
    }

    override fun onDestroy() {
        unbindService(connection)
        super.onDestroy()
    }
}