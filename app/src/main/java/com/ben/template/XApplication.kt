package com.ben.template

import android.app.Application
import android.util.Log
import com.ben.framework.XFramework
import com.maning.librarycrashmonitor.MCrashMonitor
import com.maning.librarycrashmonitor.listener.MCrashCallBack
import java.io.File

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
    }
}