package com.ben.template

import com.ben.template.function.*
import com.ben.template.function.aidl.AidlActivity
import com.ben.template.function.aidl.AidlPoolActivity
import com.ben.template.function.app.AppListActivity
import com.ben.template.function.coroutine.CoroutineActivity
import com.ben.template.function.external.ExternalActivity
import com.ben.template.function.live.AsyncTestActivity
import com.ben.template.function.messenger.MessengerActivity
import com.ben.template.function.recycler.RecyclerViewActivity
import com.ben.template.function.retrofit.RetrofitActivity
import com.ben.template.function.socket.TcpSocketActivity
import com.ben.template.function.task.TaskActivity
import com.ben.template.function.transition.TransitionActivity1

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
        addItem(Item(ItemType.TITLE, null, "视图"))
        addItem(Item(ItemType.ITEM, RecyclerViewActivity::class.java, "RecyclerView"))
        addItem(Item(ItemType.ITEM, ConstraintLayoutActivity::class.java, "ConstraintLayout"))
        addItem(Item(ItemType.ITEM, MotionLayoutActivity::class.java, "MotionLayout"))
        addItem(Item(ItemType.ITEM, ShapeActivity::class.java, "Shape图形"))
        addItem(Item(ItemType.ITEM, AutoSizeActivity::class.java, "自适应"))
        addItem(Item(ItemType.ITEM, TransitionActivity1::class.java, "转场动画"))
        addItem(Item(ItemType.ITEM, ActionItem::class.java, ActionItem.WINDOW.action))

        addItem(Item(ItemType.TITLE, null, "工具"))
        addItem(Item(ItemType.ITEM, AsyncTestActivity::class.java, "异步回调监听处理"))
        addItem(Item(ItemType.ITEM, TaskActivity::class.java, "任务流"))
        addItem(Item(ItemType.ITEM, KotlinActivity::class.java, "Kotlin"))

        addItem(Item(ItemType.TITLE, null, "Binder"))
        addItem(Item(ItemType.ITEM, MessengerActivity::class.java, "Messenger"))
        addItem(Item(ItemType.ITEM, TcpSocketActivity::class.java, "TCP Socket"))
        addItem(Item(ItemType.ITEM, AidlActivity::class.java, "AIDL"))
        addItem(Item(ItemType.ITEM, AidlPoolActivity::class.java, "AIDL连接池"))

        addItem(Item(ItemType.TITLE, null, "Jetpack"))
        addItem(Item(ItemType.ITEM, CoroutineActivity::class.java, "协程"))

        addItem(Item(ItemType.TITLE, null, "杂项"))
        addItem(Item(ItemType.ITEM, RetrofitActivity::class.java, "Retrofit"))
        addItem(Item(ItemType.ITEM, ExternalActivity::class.java, "应用外弹出"))
        addItem(Item(ItemType.ITEM, AppListActivity::class.java, "应用列表"))
        addItem(Item(ItemType.ITEM, WebViewActivity::class.java, "浏览器"))
        addItem(Item(ItemType.ITEM, ActionItem::class.java, ActionItem.BROWSER.action))
    }

    private fun addItem(item: Item) {
        ITEMS.add(item)
        val className = item.className
        if (className != null) {
            ITEM_MAP[className] = item
        }
    }

    fun getIndex(className: Class<*>): Int {
        return ITEMS.indexOf(ITEM_MAP[className])
    }

    fun getClass(index: Int): Class<*>? {
        return ITEMS[index].className
    }

    class Item(
        val type: ItemType,
        val className: Class<*>?,
        val content: String
    ) {
        override fun toString(): String {
            return content
        }
    }

    enum class ItemType {
        TITLE, ITEM
    }
}