package com.ben.template.function

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ben.framework.util.setVisible
import com.ben.template.R
import kotlinx.android.synthetic.main.activity_constraint_layout.*

class ConstraintLayoutActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constraint_layout)

        textView5.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            textView5 -> {
                v?.setVisible(false)
            }

            else -> {
            }
        }
    }
}