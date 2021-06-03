package com.ben.template.function

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ben.template.R

/**
 * Kotlin语法研究
 * @author Benhero
 */
class KotlinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
        array()
    }

    private fun array() {
        // 创建指定大小的数组
        val array1 = Array(1) { i -> i * i }

        // 空数组
        val array2 = IntArray(10)
        val arr5 = IntArray(6) { it }

        // 创建指定类型指定大小的空数组
        val array3 = arrayOfNulls<Int>(5)

        // 创建基本类型的数组 明确元素
        val array4 = intArrayOf(1, 2, 3, 4, 5)

        // 创建空数组
        val empty = emptyArray<Int>()

        Log.i("JKL", "onCreate: array1 : " + array1.contentToString())
        Log.i("JKL", "onCreate: array2 : " + array2.contentToString())
        Log.i("JKL", "onCreate: arr5 : " + arr5.contentToString())
        Log.i("JKL", "onCreate: array3 : " + array3.contentToString())
        Log.i("JKL", "onCreate: array4 : " + array4.contentToString())
        Log.i("JKL", "onCreate: empty : " + empty.contentToString())
    }
}