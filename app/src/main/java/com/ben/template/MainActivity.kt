package com.ben.template

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ben.framework.MainHandler
import com.ben.template.function.recycler.RecyclerViewActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 主界面
 *
 * @author Benhero
 * @date 2019-10-29
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter = ManiListAdapter()
        main_list.adapter = adapter
        main_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // 自动点击的类
        val className = RecyclerViewActivity::class.java
        MainHandler.post(50) {
            val position = MainListItems.getIndex(className)
            // item若没有显示在画面上，则是不会加载到RecyclerView中，所以拿到的child可能为空
            val child = main_list.getChildAt(position) ?: return@post
            val childViewHolder = main_list.getChildViewHolder(child) as ManiListAdapter.ViewHolder
//            childViewHolder.itemView.performClick()
        }
//        XLog.t().st(3).b().i("hello")
    }
}