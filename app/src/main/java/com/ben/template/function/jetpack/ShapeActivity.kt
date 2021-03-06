package com.ben.template.function.jetpack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ben.template.R
import com.ben.template.databinding.ActivityShapeBinding

/**
 * ShapeDrawable样例
 * @author Benhero
 */
class ShapeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityShapeBinding>(
            this,
            R.layout.activity_shape
        )
    }
}