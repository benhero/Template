package com.ben.template

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 主界面
 *
 * @author Benhero
 * @date 2019-10-29
 */
class MainActivity : AppCompatActivity() {

    val i = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter = ManiListAdapter()
        main_list.adapter = adapter
        main_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
}