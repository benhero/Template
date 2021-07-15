package com.ben.template.function.kotlin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.ben.framework.util.dp
import com.ben.template.MainActivity
import com.ben.template.R
import kotlinx.android.synthetic.main.activity_internal_function.*

/**
 * Kotlin内联扩展方法
 *
 * @author Benhero
 */
class InternalFunctionActivity : AppCompatActivity() {
    var a: Int? = null
    var b: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_internal_function)

        // 对象转换 + 减少临时变量的使用：run、apply都可以
        (internal_btn.layoutParams as ViewGroup.MarginLayoutParams).run {
            width = LinearLayout.LayoutParams.MATCH_PARENT
            marginStart = 16.dp
            marginEnd = 16.dp
            bottomMargin = 16.dp
        }

        a = 5
        b = 1

        val c = a?.let { aa ->
            b?.let { bb ->
                aa - bb
            }
        }
        val d = if (a != null && b != null) {
            a!! - b!!
        } else 0

        Log.i("JKL", "onCreate: $c - $d")

        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
    }
}