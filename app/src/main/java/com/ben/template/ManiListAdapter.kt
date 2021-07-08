package com.ben.template

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import com.ben.template.databinding.MainListBottomLayoutBindingImpl
import com.ben.template.databinding.MainListTitleLayoutBindingImpl
import com.ben.template.function.ActionItem
import com.ben.template.function.dispatchAction

/**
 * 主界面列表适配器
 *
 * @author Benhero
 * @date   2019/10/29
 */
class ManiListAdapter : RecyclerView.Adapter<ManiListAdapter.AbsViewHolder>() {
    private val list = MainListItems.ITEMS

    companion object {
        private const val TYPE_ITEM = 1
        private const val TYPE_TITLE = 2
        private const val TYPE_BOTTOM = 3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbsViewHolder {
        return when (viewType) {
            TYPE_TITLE -> {
                TitleViewHolder(
                    DataBindingUtil.inflate<MainListTitleLayoutBindingImpl>(
                        LayoutInflater.from(parent.context),
                        R.layout.main_list_title_layout,
                        parent,
                        false
                    ).root
                )
            }
            TYPE_BOTTOM -> {
                BottomViewHolder(
                    DataBindingUtil.inflate<MainListBottomLayoutBindingImpl>(
                        LayoutInflater.from(parent.context),
                        R.layout.main_list_bottom_layout,
                        parent,
                        false
                    ).root
                )
            }
            else -> {
                ItemViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.main_list_item_layout, parent, false)
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AbsViewHolder, index: Int) {
        val context = holder.itemView.context
        val item = list[index]

        val itemViewType = getItemViewType(index)
        when (itemViewType) {
            TYPE_ITEM -> {
                (holder as ItemViewHolder).title.text = item.content
                holder.itemView.setOnClickListener {
                    if (item.className == ActionItem::class.java) {
                        dispatchAction(item.content)
                    } else {
                        try {
                            context.startActivity(Intent(context, item.className))
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }
            TYPE_TITLE -> {
                (holder as TitleViewHolder).title.text = item.content
            }
            TYPE_BOTTOM -> {

            }
            else -> {
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (list[position].type) {
            MainListItems.ItemType.TITLE -> {
                TYPE_TITLE
            }
            MainListItems.ItemType.BOTTOM -> {
                TYPE_BOTTOM
            }
            else -> {
                TYPE_ITEM
            }
        }
    }

    abstract class AbsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class ItemViewHolder(itemView: View) : AbsViewHolder(itemView) {
        val title: TextView
            get() = itemView.findViewById(R.id.main_list_item_text)
    }

    class TitleViewHolder(itemView: View) : AbsViewHolder(itemView) {
        val title: TextView
            get() = itemView.findViewById(R.id.main_list_title_text)
    }

    class BottomViewHolder(itemView: View) : AbsViewHolder(itemView)

    class GridSpanSizeLookup : SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            return when (MainListItems.ITEMS[position].type) {
                MainListItems.ItemType.TITLE,
                MainListItems.ItemType.BOTTOM -> {
                    2
                }
                else -> {
                    1
                }
            }
        }
    }
}