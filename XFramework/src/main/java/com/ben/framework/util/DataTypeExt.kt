package com.ben.framework.util

import android.content.res.Resources
import android.util.TypedValue

/**
 * 基础数据类型拓展库
 *
 * @author Benhero
 * @date   2020/10/13
 */

val Float.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )

val Int.dp
    get() = this.toFloat().dp.toInt()