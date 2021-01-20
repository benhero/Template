package com.ben.template.function

import android.app.KeyguardManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
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
        DataBindingUtil.setContentView<ActivityShapeBinding>(this,
            R.layout.activity_shape
        )

    }
}