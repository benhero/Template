package com.ben.template.function.extra

import android.service.wallpaper.WallpaperService

/**
 * 动态壁纸
 *
 * @author Benhero
 * @date 2021/1/20
 */
class LiveWallpaperService : WallpaperService() {
    override fun onCreateEngine(): Engine {
        return Engine()
    }


}

//class eng : WallpaperService.Engine() {
//
//}