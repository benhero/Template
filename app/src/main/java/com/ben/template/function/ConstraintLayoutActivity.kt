package com.ben.template.function

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.ben.framework.util.isVisible
import com.ben.framework.util.setVisible
import com.ben.template.R
import kotlinx.android.synthetic.main.activity_constraint_layout.*

class ConstraintLayoutActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constraint_layout)

        textView5.setOnClickListener(this)
        textView13.setOnClickListener(this)
        textView21.setOnClickListener(this)
        textView24.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            textView5, textView21 -> {
                v?.setVisible(false)
            }
            textView13 -> {
                val layoutParams = v?.layoutParams as ConstraintLayout.LayoutParams
                when (layoutParams.horizontalChainStyle) {
                    ConstraintLayout.LayoutParams.CHAIN_SPREAD -> {
                        layoutParams.horizontalChainStyle =
                            ConstraintLayout.LayoutParams.CHAIN_SPREAD_INSIDE
                        textView13.text = "spread_inside"
                    }
                    ConstraintLayout.LayoutParams.CHAIN_SPREAD_INSIDE -> {
                        layoutParams.horizontalChainStyle =
                            ConstraintLayout.LayoutParams.CHAIN_PACKED
                        textView13.text = "packed"
                    }
                    ConstraintLayout.LayoutParams.CHAIN_PACKED -> {
                        layoutParams.horizontalChainStyle =
                            ConstraintLayout.LayoutParams.CHAIN_SPREAD
                        textView13.text = "spread"
                    }
                }
            }
            textView24 -> {
                barrier_group.setVisible(!barrier_group.isVisible())
            }
            else -> {
            }
        }
    }
}