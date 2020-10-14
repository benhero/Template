package com.ben.framework.util

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.databinding.BindingAdapter

/**
 * Drawable XML 简化工具类
 *
 * @author Benhero
 * @date   2020/2/20
 */
/**
 * 渐变色矩形
 * @param start 起始颜色
 * @param center 中心颜色
 * @param end 末尾颜色
 * @param solidColor 背景色
 * @param cornerRadius 圆角弧度
 */
@BindingAdapter(
    "gradientStart", "gradientCenter", "gradientEnd",
    "solidColor", "cornerRadius",
    "gradientAngle", "strokeColor", "strokeWidth", requireAll = false
)
fun setShape(
    view: View, start: Any?, center: Any?, end: Any?,
    solidColor: Any?, cornerRadius: Int, angle: Int?,
    strokeColor: Any?, strokeWidth: Int?
) {
    val drawable = GradientDrawable()
    val parseAngle = parseAngle(angle)
    drawable.orientation = parseAngle
    if (solidColor != null) {
        // 纯色
        drawable.setColor(exchangeColor(solidColor))
    } else if (start != null && end != null) {
        // 渐变色
        if (center == null) {
            drawable.colors = intArrayOf(exchangeColor(start), exchangeColor(end))
        } else {
            drawable.colors =
                intArrayOf(exchangeColor(start), exchangeColor(center), exchangeColor(end))
        }
    }

    drawable.setSize(view.width, view.height)
    drawable.shape = GradientDrawable.RECTANGLE
    drawable.cornerRadius = cornerRadius.toFloat().dp

    if (strokeColor != null) {
        drawable.setStroke((strokeWidth ?: 1).dp, exchangeColor(strokeColor))
    }
    view.background = drawable
}

private fun parseAngle(angle: Int? = 0): GradientDrawable.Orientation {
    return when (angle) {
        0 -> GradientDrawable.Orientation.LEFT_RIGHT
        45 -> GradientDrawable.Orientation.BL_TR
        90 -> GradientDrawable.Orientation.BOTTOM_TOP
        135 -> GradientDrawable.Orientation.BR_TL
        180 -> GradientDrawable.Orientation.RIGHT_LEFT
        225 -> GradientDrawable.Orientation.TR_BL
        270 -> GradientDrawable.Orientation.TOP_BOTTOM
        315 -> GradientDrawable.Orientation.TL_BR
        else -> GradientDrawable.Orientation.LEFT_RIGHT
    }
}

/**
 * 将String、Int类型转成颜色值
 */
private fun exchangeColor(color: Any): Int {
    return when (color) {
        is Int -> {
            color.toInt()
        }
        is String -> {
            Color.parseColor(color)
        }
        else -> {
            0
        }
    }
}