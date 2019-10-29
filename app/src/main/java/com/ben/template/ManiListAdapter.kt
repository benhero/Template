package com.ben.template

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * 主界面列表适配器
 *
 * @author Benhero
 * @date   2019/10/29
 */
class ManiListAdapter : RecyclerView.Adapter<ManiListAdapter.ViewHolder>() {
    private val list = MainListItems.ITEMS

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val root = LayoutInflater.from(context).inflate(R.layout.main_list_item_layout, parent, false)
        return ViewHolder(root)
    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, index: Int) {
        val context = holder.itemView.context
        val item = list[index]
        holder.title.text = item.content
        holder.itemView.setOnClickListener {
            try {
                context.startActivity(Intent(context, item.className))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.main_list_item_text)
    }
}