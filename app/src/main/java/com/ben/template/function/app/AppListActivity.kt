package com.ben.template.function.app

import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.ben.template.R
import com.ben.template.function.recycler.DemoAdapter
import kotlinx.android.synthetic.main.activity_recycler.*

/**
 * 获取应用列表
 *
 * @author Benhero
 * @date   2021-01-22
 * https://blog.csdn.net/fza3230200/article/details/94026421
 */
class AppListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_list)
        recycler_view.adapter = AppListAdapter(getRunningApp())
        recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun getRunningApp(): List<PackageInfo> {
        val localPackageManager = packageManager
        val runningList: List<PackageInfo> = localPackageManager.getInstalledPackages(0)
        val l = ArrayList<PackageInfo>()
        for (i in runningList.indices) {
            val localPackageInfo1 = runningList[i]
            val str1 = localPackageInfo1.packageName.split(":").toTypedArray()[0]
            if (ApplicationInfo.FLAG_SYSTEM and localPackageInfo1.applicationInfo.flags == 0
                && ApplicationInfo.FLAG_UPDATED_SYSTEM_APP and localPackageInfo1.applicationInfo.flags == 0
                && ApplicationInfo.FLAG_STOPPED and localPackageInfo1.applicationInfo.flags == 0
            ) {
                l.add(localPackageInfo1)
                Log.e("JKL", str1)
            }
        }

        runningList.filter {
            !(ApplicationInfo.FLAG_SYSTEM and it.applicationInfo.flags == 0
                    && ApplicationInfo.FLAG_UPDATED_SYSTEM_APP and it.applicationInfo.flags == 0
                    && ApplicationInfo.FLAG_STOPPED and it.applicationInfo.flags == 0)
        }

        return l
    }
}