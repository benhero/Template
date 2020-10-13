package com.ben.framework.util

import android.view.MotionEvent
import android.view.View

/**
 * View拓展工具类
 *
 * @author Benhero
 * @date   2020/9/15
 */
fun View.setVisible(boolean: Boolean) {
    this.visibility = if (boolean) View.VISIBLE else View.GONE
}

fun View.isVisible() :Boolean {
    return this.visibility == View.VISIBLE
}

/**
 * 添加点击缩放效果
 */
fun View.addClickScale(scale: Float = 0.9f, duration: Long = 150) {
    this.setOnTouchListener(object : View.OnTouchListener {
        override fun onTouch(v: View, event: MotionEvent): Boolean {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    v.animate().scaleX(scale).scaleY(scale).setDuration(duration).start()
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    v.animate().scaleX(1f).scaleY(1f).setDuration(duration).start()
                }
            }
            // 点击事件处理，交给View自身
            return v.onTouchEvent(event)
        }
    })
}

/**
 * 添加点击缩放效果
 */
fun View.addClickAlpha(alpha: Float = 0.5f) {
    this.setOnTouchListener(object : View.OnTouchListener {
        override fun onTouch(v: View, event: MotionEvent): Boolean {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    v.alpha = alpha
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    v.alpha = 1f
                }
            }
            // 点击事件处理，交给View自身
            return v.onTouchEvent(event)
        }
    })
}