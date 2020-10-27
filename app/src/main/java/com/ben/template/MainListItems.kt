package com.ben.template

import com.ben.template.function.coroutine.CoroutineActivity
import com.ben.template.function.ShapeActivity
import com.ben.template.function.recycler.RecyclerViewActivity

/**
 * 主界面列表选项
 *
 * @author Benhero
 * @date 2019-10-29
 */
object MainListItems {
    val ITEMS = ArrayList<Item>()
    private val ITEM_MAP = HashMap<Class<*>, Item>()

    init {
        addItem(Item(RecyclerViewActivity::class.java, "RecyclerView"))
        addItem(Item(ShapeActivity::class.java, "Shape图形"))
        addItem(Item(CoroutineActivity::class.java, "协程"))
    }

    private fun addItem(item: Item) {
        ITEMS.add(item)
        ITEM_MAP[item.className] = item
    }

    fun getIndex(className: Class<*>): Int {
        return ITEMS.indexOf(ITEM_MAP[className])
    }

    fun getClass(index: Int): Class<*> {
        return ITEMS[index].className
    }

    class Item(val className: Class<*>, val content: String) {
        override fun toString(): String {
            return content
        }
    }
}