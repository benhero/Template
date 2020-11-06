package com.ben.framework.util

import com.elvishew.xlog.formatter.border.DefaultBorderFormatter
import com.elvishew.xlog.internal.SystemCompat

/**
 * 日志工具类边框格式
 *
 * @author Benhero
 * @date   2020/11/6
 */
class XBorderFormatter() : DefaultBorderFormatter() {
    override fun format(data: Array<out String>?): String {
        val format = super.format(data)
        return "|" + SystemCompat.lineSeparator + format
    }
}