package com.ben.template.function.aidl

import com.ben.template.aidl.pool.IMinus

/**
 * 减法实现
 *
 * @author Benhero
 * @date   2021/7/7
 */
class MinusImpl : IMinus.Stub() {
    override fun caculate(a: Int, b: Int): Int {
        return a - b
    }
}