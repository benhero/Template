package com.ben.template.function.ipc.aidl

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ben.template.R
import com.ben.template.aidl.pool.IMinus
import com.ben.template.aidl.pool.IPlus
import kotlinx.android.synthetic.main.activity_aidl_pool.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * AIDL连接池页面
 *
 * @author Benhero
 * @date   2021/7/6
 */
class AidlPoolActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aidl_pool)

        aidl_pool_btn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                // 加法
                val iPlus = IPlus.Stub.asInterface(
                    BinderPool.getInstance(this@AidlPoolActivity)
                        .queryBinder(BinderPool.BINDER_PLUS)
                )
                val plusResult = iPlus?.caculate(1, 1)
                Log.i("JKL", "onServiceConnected: " + plusResult)
                withContext(Dispatchers.Main) {
                    aidl_pool_text.text = "" + plusResult
                }

                // 减法
                val iMinus = IMinus.Stub.asInterface(
                    BinderPool.getInstance(this@AidlPoolActivity)
                        .queryBinder(BinderPool.BINDER_MINUS)
                )
                val minusResult = iMinus?.caculate(10, 1)
                Log.i("JKL", "onServiceConnected: " + minusResult)
                withContext(Dispatchers.Main) {
                    aidl_pool_text2.text = "" + minusResult
                }
            }
        }
    }


}