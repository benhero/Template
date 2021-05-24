package com.ben.template.function.external

import android.Manifest
import android.app.Activity
import android.app.KeyguardManager
import android.app.WallpaperManager
import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.ben.framework.MainHandler
import com.ben.template.R
import com.ben.template.XApplication
import kotlinx.android.synthetic.main.activity_external.*

/**
 * 外部弹出Activity
 * @author Benhero
 * @date 2021/1/20
 */
class ExternalActivity : AppCompatActivity(), View.OnClickListener {
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
        button.setOnClickListener(this)
        btn_wallpaper.setOnClickListener(this)
        check_wallpaper.isChecked = isLiveWallpaperRunning(this, packageName)
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
        try {
            startLiveWallpaperPreview(this, packageName, StaticWallpaper::class.java.canonicalName)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }

    /**
     * 去往某个动态壁纸的预览页面,那里可以设置壁纸
     *
     * @param packageName 动态壁纸的包名
     * @param classFullName 动态壁纸service类的类全名
     */
    private fun startLiveWallpaperPreview(
        activity: Activity, packageName: String, classFullName: String
    ) {
        val componentName = ComponentName(packageName, classFullName)
        val intent = Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER)
        intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, componentName)
        activity.startActivityForResult(intent, 124)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 124) {
            check_wallpaper.isChecked = isLiveWallpaperRunning(this, packageName)
        }
    }

    /**
     * 判断一个动态壁纸是否已经在运行
     *
     * @param context :上下文
     * @param targetPackageName :要判断的动态壁纸的包名
     * @return
     */
    private fun isLiveWallpaperRunning(context: Context, targetPackageName: String): Boolean {
        val wallpaperManager = WallpaperManager.getInstance(context) // 得到壁纸管理器
        val wallpaperInfo = wallpaperManager.wallpaperInfo // 如果系统使用的壁纸是动态壁纸话则返回该动态壁纸的信息,否则会返回null
        if (wallpaperInfo != null) { // 如果是动态壁纸,则得到该动态壁纸的包名,并与想知道的动态壁纸包名做比较
            val currentLiveWallpaperPackageName = wallpaperInfo.packageName
            if (currentLiveWallpaperPackageName == targetPackageName) {
                return true
            }
        }
        return false
    }

    override fun onClick(v: View?) {
        when (v) {
            button -> {
                MainHandler.post(5000) {
                    startActivity()
                }
                finish()
            }
            btn_wallpaper -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(
                        arrayOf(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ), 1
                    )

                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {

                        setLiveWallPaper()
                    }
                }
            }
            else -> {
            }
        }
    }
}