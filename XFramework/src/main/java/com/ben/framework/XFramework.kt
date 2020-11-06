package com.ben.framework

import android.app.Application
import com.maning.librarycrashmonitor.MCrashMonitor

/**
 * 通用框架入口
 *
 * @author Benhero
 * @date   2020/10/13
 */
object XFramework {
    lateinit var app: Application

    fun init(application: Application) {
        app = application
        MCrashMonitor.init(app, BuildConfig.DEBUG)
    }
}