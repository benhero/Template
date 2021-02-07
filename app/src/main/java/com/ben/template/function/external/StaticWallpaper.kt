package com.ben.template.function.external

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import android.os.Environment
import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder
import androidx.core.graphics.drawable.toBitmap
import com.ben.template.XApplication
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream


/**
 * 动态壁纸
 *
 * @author Benhero
 * @date 2021/1/20
 */
class StaticWallpaper : WallpaperService() {

    override fun onCreateEngine(): Engine {
        return StaticEngine()
    }

    companion object {
        /**
         * 是否正在应用中
         */
        var isUsing = false
    }

    inner class StaticEngine : WallpaperService.Engine() {

        override fun onCreate(surfaceHolder: SurfaceHolder?) {
            super.onCreate(surfaceHolder)
            if (!isPreview) {
                isUsing = true
            }
        }

        override fun onDestroy() {
            super.onDestroy()
            if (!isPreview) {
                isUsing = false
            }
        }

        override fun onSurfaceCreated(holder: SurfaceHolder?) {
            super.onSurfaceCreated(holder)
            val wallpaperManager = WallpaperManager.getInstance(XApplication.app.applicationContext)
            val wallpaperDrawable = wallpaperManager.drawable

            val canvas: Canvas = holder!!.lockCanvas()

            val saveBitmap = saveBitmap(wallpaperDrawable.toBitmap(), "/wallpaper.jpg")

            val bitmap =
                if (saveBitmap != null) BitmapFactory.decodeFile(saveBitmap) else wallpaperDrawable.toBitmap()

            val clipWidth = (1.0f * bitmap.height * canvas.width / canvas.height).toInt()

            val src = Rect(0, 0, clipWidth, bitmap.height)
            val dest = Rect(0, 0, canvas.width, canvas.height)
            canvas.drawBitmap(bitmap, src, dest, null)

            holder.unlockCanvasAndPost(canvas)
        }

        private fun saveBitmap(bitmap: Bitmap, fileName: String?): String? {
            return try {
                val path =
                    XApplication.app.getExternalFilesDir(Environment.DIRECTORY_DCIM)?.path + fileName
                if (isUsing) {
                    return path
                }
                val stream: OutputStream = FileOutputStream(path)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                stream.close()
                path
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
        }
    }
}