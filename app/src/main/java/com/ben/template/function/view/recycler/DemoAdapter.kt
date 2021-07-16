package com.ben.template.function.view.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ben.template.R

/**
 * 列表适配器
 *
 * @author Benhero
 * @date   2020/8/26
 */
class DemoAdapter(private val list: List<ListItemBean>) :
    RecyclerView.Adapter<DemoAdapter.ViewHolder>() {

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
    }

    override fun getItemCount(): Int {
        return list.size
    }
}