package com.ben.template.function.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ben.template.R
import kotlinx.android.synthetic.main.motion_01_basic.*

class MotionLayoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.motion_01_basic)

        button2.setOnClickListener {
            if (motionLayout.progress > 0.5f) {
                motionLayout.transitionToStart()
            } else {
                motionLayout.transitionToEnd()
            }
        }
    }
}