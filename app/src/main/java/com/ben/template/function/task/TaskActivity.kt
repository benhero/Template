package com.ben.template.function.task

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ben.template.R

/**
 * 任务流
 * @author Benhero
 */
class TaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

        TaskFlow().start()
    }
}