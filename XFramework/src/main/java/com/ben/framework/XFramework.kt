package com.ben.framework

import android.app.Application

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
    }
}