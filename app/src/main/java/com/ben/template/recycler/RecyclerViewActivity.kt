package com.ben.template.recycler

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ben.template.R
import kotlinx.android.synthetic.main.activity_recycler.*

/**
 * 列表页Activity
 *
 * @author Benhero
 * @date   2020/8/26
 */
class RecyclerViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)

        val list = ArrayList<ListItemBean>()
        for (i in 0..20) {
            list.add(ListItemBean())
        }
        recycler_view.adapter = DemoAdapter(list)
        recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

}