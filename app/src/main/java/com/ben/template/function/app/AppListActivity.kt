package com.ben.template.function.app

import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ben.framework.MainHandler
import com.ben.template.R
import kotlinx.android.synthetic.main.activity_app_list.*
import kotlinx.android.synthetic.main.activity_recycler.recycler_view

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
        val appListAdapter = AppListAdapter(getRunningApp())
        recycler_view.adapter = appListAdapter
        recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler_view.itemAnimator = FlyAnimator()

        clear_btn.setOnClickListener {
            appListAdapter.notifyItemRangeRemoved(0, appListAdapter.list.size)
            appListAdapter.list.clear()
            MainHandler.post(1000) { finish() }
        }
    }

    private fun getRunningApp(): ArrayList<PackageInfo> {
        val localPackageManager = packageManager
        val runningList: List<PackageInfo> = localPackageManager.getInstalledPackages(0)
        val l = ArrayList<PackageInfo>()
        for (i in runningList.indices) {
            val localPackageInfo1 = runningList[i]
            if (ApplicationInfo.FLAG_SYSTEM and localPackageInfo1.applicationInfo.flags == 0
                && ApplicationInfo.FLAG_UPDATED_SYSTEM_APP and localPackageInfo1.applicationInfo.flags == 0
                && ApplicationInfo.FLAG_STOPPED and localPackageInfo1.applicationInfo.flags == 0
            ) {
                l.add(localPackageInfo1)
            }
        }

        return l
    }
}