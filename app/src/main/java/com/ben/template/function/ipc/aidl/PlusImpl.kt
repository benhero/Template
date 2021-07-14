package com.ben.template.function.ipc.aidl

import com.ben.template.aidl.pool.IPlus

/**
 * 加法实现
 *
 * @author Benhero
 * @date   2021/7/7
 */
class PlusImpl : IPlus.Stub() {
    override fun caculate(a: Int, b: Int): Int {
        return a + b
    }
}