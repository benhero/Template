package com.ben.template

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ben.framework.MainHandler
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
        val className = ShapeActivity::class.java
        MainHandler.post(50) {
            val position = MainListItems.getIndex(className)
            val child = main_list.getChildAt(position)
            val childViewHolder = main_list.getChildViewHolder(child) as ManiListAdapter.ViewHolder
//            childViewHolder.itemView.performClick()
        }
    }
}