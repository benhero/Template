package com.ben.template

import com.ben.template.function.*
import com.ben.template.function.app.AppListActivity
import com.ben.template.function.coroutine.CoroutineActivity
import com.ben.template.function.external.ExternalActivity
import com.ben.template.function.live.AsyncTestActivity
import com.ben.template.function.recycler.RecyclerViewActivity
import com.ben.template.function.retrofit.RetrofitActivity

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
        addItem(Item(AutoSizeActivity::class.java, "自适应"))
        addItem(Item(ConstraintLayoutActivity::class.java, "ConstraintLayout"))
        addItem(Item(RetrofitActivity::class.java, "Retrofit"))
        addItem(Item(MotionLayoutActivity::class.java, "MotionLayout"))
        addItem(Item(ExternalActivity::class.java, "应用外弹出"))
        addItem(Item(AppListActivity::class.java, "应用列表"))
        addItem(Item(WebViewActivity::class.java, "浏览器"))
        addItem(Item(ActionItem::class.java, ActionItem.BROWSER.action))
        addItem(Item(AsyncTestActivity::class.java, "异步回调监听处理"))
        addItem(Item(KotlinActivity::class.java, "Kotlin"))
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