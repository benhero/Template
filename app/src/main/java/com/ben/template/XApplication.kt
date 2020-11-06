package com.ben.template

import android.app.Application
import com.ben.framework.XFramework
import com.elvishew.xlog.LogConfiguration
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.XLog
import com.elvishew.xlog.formatter.border.DefaultBorderFormatter
import com.elvishew.xlog.internal.SystemCompat
import com.maning.librarycrashmonitor.MCrashMonitor

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
        XFramework.app = this

        MCrashMonitor.init(this, BuildConfig.DEBUG)
        initLog()
    }

    private fun initLog() {
        val builder = LogConfiguration.Builder()
        builder.tag("JKL")
        builder.borderFormatter(object : DefaultBorderFormatter() {
            override fun format(data: Array<out String>?): String {
                val format = super.format(data)
                return "|" + SystemCompat.lineSeparator + format
            }
        })
        XLog.init(LogLevel.ALL, builder.build())
    }
}