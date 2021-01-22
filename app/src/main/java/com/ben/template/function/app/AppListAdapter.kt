package com.ben.template.function.app

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ben.template.R
import com.ben.template.XApplication


/**
 * 列表适配器
 *
 * @author Benhero
 * @date   2021-01-22
 */
class AppListAdapter(private val list: List<PackageInfo>) :
    RecyclerView.Adapter<AppListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon: ImageView = itemView.findViewById(R.id.item_icon)
        val title: TextView = itemView.findViewById(R.id.item_title)
        val desc: TextView = itemView.findViewById(R.id.item_desc)
        val divider: View = itemView.findViewById(R.id.item_divider)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.divider.visibility = if (position == list.size - 1) View.GONE else View.VISIBLE
        val packageName = list[position].packageName.split(":").toTypedArray()[0]
        holder.title.text = getAppName(XApplication.app, packageName)
        holder.desc.text = packageName
        holder.icon.setImageDrawable(getBitmap(XApplication.app, packageName))
    }

    /**
     * 获取应用程序名称
     */
    @Synchronized
    fun getAppName(context: Context, packageName: String): String? {
        try {
            val packageManager: PackageManager = context.packageManager
            val applicationInfo = packageManager.getApplicationInfo(packageName, 0)
            return packageManager.getApplicationLabel(applicationInfo).toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 获取图标 bitmap
     */
    @Synchronized
    fun getBitmap(context: Context, packageName: String): Drawable {
        var packageManager: PackageManager? = null
        var applicationInfo: ApplicationInfo?
        try {
            packageManager = context.applicationContext
                .packageManager
            applicationInfo = packageManager.getApplicationInfo(
                packageName, 0
            )
        } catch (e: PackageManager.NameNotFoundException) {
            applicationInfo = null
        }
        return packageManager!!.getApplicationIcon(applicationInfo!!)
    }


    override fun getItemCount(): Int {
        return list.size
    }
}