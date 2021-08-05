package com.ben.template.function.live

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ben.framework.LiveListener
import com.ben.framework.live
import com.ben.template.R
import kotlinx.android.synthetic.main.fragment_async.*
import kotlinx.coroutines.*

/**
 * 异步回调测试
 * @author Benhero
 */
class AsyncTestFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_async, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        async_button1.setOnClickListener(this)
        async_button2.setOnClickListener(this)
        async_button3.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            async_button1 -> {
                Test().back(object : AsyncListener {
                    override fun onAsyncDone() {
                        Log.i("JKL", "Btn1: ${async_button1.hashCode()}")
                    }
                })
                activity?.onBackPressed()
            }
            async_button2 -> {
                Test().back2(LiveListener(lifecycle, object : AsyncListener {
                    override fun onAsyncDone() {
                        Log.i("JKL", "Btn2: ${async_button2.hashCode()}")
                    }
                }))
                activity?.onBackPressed()
            }
            async_button3 -> {
                Test().back3(object : AsyncListener {
                    override fun onAsyncDone() {
                        Log.i("JKL", "Btn3: ${async_button3.hashCode()}")
                    }
                }.live(lifecycle))
                activity?.onBackPressed()
            }
            else -> {
            }
        }
    }

    /**
     * 异步事件监听器
     */
    interface AsyncListener {
        fun onAsyncDone()
    }

    private class Test {

        fun back(listener: AsyncListener) {
            CoroutineScope(Dispatchers.IO).launch {
                delay(5000)
                withContext(Dispatchers.Main) {
                    listener.onAsyncDone()
                }
            }
        }

        fun back2(listener: LiveListener<AsyncListener>) {
            CoroutineScope(Dispatchers.IO).launch {
                delay(5000)
                withContext(Dispatchers.Main) {
                    listener.listener()?.onAsyncDone()
                }
            }
        }

        fun back3(listener: AsyncListener) {
            CoroutineScope(Dispatchers.IO).launch {
                delay(5000)
                withContext(Dispatchers.Main) {
                    listener.onAsyncDone()
                }
            }
        }
    }
}