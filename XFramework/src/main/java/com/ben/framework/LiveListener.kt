package com.ben.framework

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

/**
 * 生命周期感知型监听器
 *
 * @author Benhero
 * @date   2021/4/21
 */
class LiveListener<T>(lifecycle: Lifecycle, listener: T) {

    private var wrapper: T? = listener

    init {
        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (event == Lifecycle.Event.ON_DESTROY) {
                    wrapper = null
                }
            }
        })
    }

    fun listener(): T? {
        return wrapper
    }
}