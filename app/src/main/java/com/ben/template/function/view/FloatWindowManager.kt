package com.ben.template.function.view

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.Gravity
import android.view.WindowManager
import android.widget.Button


/**
 * 悬浮窗
 *
 * @author Benhero
 * @date   2021/6/7
 */
object FloatWindowManager {

    fun show(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !Settings.canDrawOverlays(context)) {
            // 检测权限
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            intent.data = Uri.parse("package:" + context.packageName)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
            return
        }

        val btn = Button(context)
        btn.text = "Button"
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            0, 0,
            PixelFormat.TRANSPARENT
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && Settings.canDrawOverlays(context)) {
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            params.type = WindowManager.LayoutParams.TYPE_PHONE
        } else { /*以下代码块使得android6.0之后的用户不必再去手动开启悬浮窗权限*/
            val permission =
                PackageManager.PERMISSION_GRANTED == context.packageManager.checkPermission(
                    "android.permission.SYSTEM_ALERT_WINDOW",
                    context.packageName
                )
            if (permission) {
                params.type = WindowManager.LayoutParams.TYPE_PHONE
            } else {
                params.type = WindowManager.LayoutParams.TYPE_TOAST
            }
        }

        params.flags =
                // 在此模式下，系统会将当前Window区域以外的单击事件传递给底层的Window，当前Window区域以内的单击事件则自己处理。
                // 这个标记很重要，一般来说都需要开启此标记，否则其他Window将无法收到单击事件。
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL xor
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//        xor WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
        params.gravity = Gravity.CENTER
//        params.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_NOSENSOR
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.addView(btn, params)

        btn.setOnClickListener {
            windowManager.removeView(btn)
        }
    }
}