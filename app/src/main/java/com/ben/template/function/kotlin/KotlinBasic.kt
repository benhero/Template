package com.ben.template.function.kotlin

import android.util.Log

/**
 * Kotlin基础
 *
 * @author Benhero
 * @date   2021/7/20
 */
object KotlinBasic {
    // 长整型不能是用小写的l作为结尾，必须是大写的L
    val longValue = 3L

    // 浮点型大小写F都可以
    val floatValueMin = 1.0f
    val floatValueMax = 2.0F

    // 数字可以用下划线来作为间隔，便于阅读
    val oneMillion = 1_000_000

    init {
//        forTest()
    }

    fun test() {
        byTest()
    }

    private fun calculateTest() {
        //  shl(bits) – 有符号左移 (Java 的 <<)
        //  shr(bits) – 有符号右移 (Java 的 >>)
        //  ushr(bits) – 无符号右移 (Java 的 >>>)
        //  and(bits) – 位与
        //  or(bits) – 位或
        //  xor(bits) – 位异或
        //  inv() – 位非
    }

    private fun arrayTest() {
        // 1. 通用数组和基础数据类型数组：前者会进行装箱，后者不会
        arrayOf(1, "j", 3)
        intArrayOf(2)
        floatArrayOf(4.0f, 5.0f)

        // 2. 上面的是设置带默认值的创建方式，下面是动态设置的方式
        // 2.1 创建空数组
        val j = IntArray(5)
        val l: IntArray
        //  2.2 在空数组内的构造方法，根据索引设置初始值
        l = IntArray(3) { i -> i * i }
        j[0] = 1
        j[2] = 3
        j.forEach {
            Log.i("JKL", "arrayTest: $it")
        }
    }

    private fun stringTest() {
        // 原始字符串
        val text = """
            >Tell me and I forget.
            >Teach me and I remember.
            >Involve me and I learn.
            >(Benjamin Franklin)
            """.trimMargin(">")
        // 默认 | 用作边界前缀，可以选择其他字符并作为参数传入
        // 字符串模板
        Log.i("JKL", "$text.length is ${text.length}")
    }

    private fun ifTest() {
        val a = 1
        val b = 2

        Log.i("JKL", "ifTest: " + if (a > b) a * a else b * b)

        val max = if (a > b) {
            Log.i("JKL", "choose a")
            // 返回值必须是放在代码块的最后才会生效
            a
        } else {
            Log.i("JKL", "choose b")
            b
        }
        Log.i("JKL", "ifTest: " + max)
    }

    private fun whenTest() {
        val a = 5
        when (a) {
            1 -> Log.i("JKL", "whenTest: " + 1)
            5 -> Log.i("JKL", "whenTest: " + 5)
            else -> Log.i("JKL", "whenTest: else")
        }

        // 当有返回值时，与if类似，放在最后才生效
        val b = when (a) {
            // 相同处理的条件，可以逗号隔开
            4, 5 -> {
                Log.i("JKL", "Match: " + a)
                a * a
            }
            // 不需要Break，即符合某个条件，则不会进入其他条件
            // 判断在某个范围内
            in 1..5 -> {
                Log.i("JKL", "In 1 .. 5")
                a
            }
            // 判断不在某个范围内
            !in 19..53 -> 0
            // 判断类型，符合则智能转换，可以访问该类型的方法与属性而无需任何额外的检测。
            is Int -> a.div(2)
            else -> a / 2.0
        }
        Log.i("JKL", "whenTest: " + b)

        // 可以用于取代 if-else if链
        when {
            a > 5 -> Log.i("JKL", "a > 5")
            a < 6 -> Log.i("JKL", "a < 6")
            else -> Log.i("JKL", "Unknown")
        }
    }

    private fun forTest() {
        val a = intArrayOf(1, 3, 5, 7, 9)
        // 迭代器遍历
        for (i in a) {
            Log.i("JKL", "forTest: $i")
        }

        // 索引遍历
        for (i in a.indices) {
            if (i == 2) {
                continue
            }
            Log.i("JKL", "forTest: " + a[i])
        }

        // 库函数 withIndex
        for ((index, value) in a.withIndex()) {
            Log.i("JKL", "the element at $index is $value")
        }
    }

    /**
     * 通过 by 关键字将接口的实现委托给传入的成员变量，
     * 需要修改的函数再去override即可
     */
    private fun byTest() {
        val test = TestListener()
        object : ITestListener by test {
            override fun onListener() {
                test.onListener()
                Log.i("JKL", "onListener: === 2")
            }
        }.onListener()
    }

    interface ITestListener {
        fun onListener()
    }

    class TestListener :ITestListener {

        override fun onListener() {
            Log.i("JKL", "onListener: === 1")
        }
    }

}