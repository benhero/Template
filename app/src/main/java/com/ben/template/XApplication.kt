package com.ben.template

import android.app.Application

/**
 * com.ben.template
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
    }
}