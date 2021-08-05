package com.ben.framework

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

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

/**
 * 获取代理后的对象，支持生命周期感知型监听器
 */
fun <T> Any.live(lifecycle: Lifecycle): T {
    return getProxy(lifecycle, this)
}

/**
 * 获取代理后的对象，支持生命周期感知型监听器
 */
private fun <T> getProxy(lifecycle: Lifecycle, any: Any): T {
    val listener = LiveListener(lifecycle, any)
    val newProxyInstance = Proxy.newProxyInstance(
        any.javaClass.classLoader,
        any.javaClass.interfaces,
        DynamicProxyHandler(listener)
    )
    return newProxyInstance as T
}

/**
 * 动态代理处理器
 */
class DynamicProxyHandler<T>(private val target: LiveListener<T>) : InvocationHandler {

    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any? {
        Log.i("JKL", "拦截方法调用 : " + method.toString())
        target.listener()?.run {
            return method?.invoke(this, *args.orEmpty())
        }
        return null
    }
}