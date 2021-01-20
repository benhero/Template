package com.ben.template.function.extra

import android.app.KeyguardManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.ben.framework.MainHandler
import com.ben.template.R
import com.ben.template.XApplication
import kotlinx.android.synthetic.main.activity_external.*

/**
 * 外部弹出Activity
 * @author Benhero
 * @date 2021/1/20
 */
class ExternalActivity : AppCompatActivity() {
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        // 锁屏上弹出
        window.addFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                    WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        allowOnLockScreen()
        setContentView(R.layout.activity_external)
        button.setOnClickListener {
            MainHandler.post(5000) {
                startActivity()
            }
            finish()
        }
    }

    /**
     * 锁屏上弹出
     */
    private fun allowOnLockScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
            val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
            keyguardManager.requestDismissKeyguard(this, null)
        } else {
            this.window.addFlags(
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
            )
        }
    }

    private fun startActivity() {
        val intent = Intent()
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.setClass(XApplication.app.applicationContext, ExternalActivity::class.java)
        ActivityUtils.openExt(XApplication.app.applicationContext, intent)
    }

    private fun setLiveWallPaper() {
        val intent = Intent()
        intent.action = "android.service.wallpaper.CHANGE_LIVE_WALLPAPER"
        intent.putExtra(
            "android.service.wallpaper.extra.LIVE_WALLPAPER_COMPONENT", ComponentName(
                packageName,
                LiveWallpaperService::class.java.getCanonicalName()
            )
        )
        startActivity(intent)
    }
}