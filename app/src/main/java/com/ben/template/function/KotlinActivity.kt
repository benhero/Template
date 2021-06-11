package com.ben.template.function

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ben.template.R
import java.util.*

/**
 * Kotlin语法研究
 * @author Benhero
 */
class KotlinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
//        array()
        random()
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

    private fun random() {
        val randomBox = RandomBox(intArrayOf(1, 2, 3, 4, 5).asList(), true)
        for (i in 0..20) {
            Log.i("JKL", "random: " + randomBox.random())
        }
    }


    /**
     * 随机盒子
     * 返回指定队列里的数据，且周期内不重复返回
     * @param list 指定队列
     * @param isRecycled 是否循环复用：若随机取出全部数据后，是否重新开始；否则返回null
     */
    class RandomBox<T>(list: List<T>, private val isRecycled: Boolean) {
        private val src = ArrayList<T>(list.size)
        private val linkedList = LinkedList(list)

        init {
            list.forEach {
                src.add(it)
            }
        }

        fun random(): T? {
            if (linkedList.isEmpty()) {
                if (isRecycled) {
                    linkedList.addAll(src)
                } else {
                    return null
                }
            }
            val random = linkedList.random()
            linkedList.remove(random)
            return random
        }
    }
}