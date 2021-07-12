package com.ben.template

import android.app.Application
import com.ben.framework.XFramework
import com.ben.framework.util.XBorderFormatter
import com.elvishew.xlog.LogConfiguration
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.XLog

/**
 * Application
 *
 * @author Benhero
 * @date   2020/8/25
 */
class XApplication : Application() {
    companion object {
        lateinit var app: Application
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        XFramework.init(this)
        initLog()
    }

    private fun initLog() {
        val builder = LogConfiguration.Builder()
        builder.tag("JKL")
        builder.borderFormatter(XBorderFormatter())
        XLog.init(LogLevel.ALL, builder.build())
    }
}