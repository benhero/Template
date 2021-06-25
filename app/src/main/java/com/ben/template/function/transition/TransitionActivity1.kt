package com.ben.template.function.transition

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ben.template.R
import kotlinx.android.synthetic.main.activity_transition1.*

/**
 * Transition共享元素转场
 *
 * @author Benhero
 */
class TransitionActivity1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transition1)

        transition_img_1.setOnClickListener {
            val p1 = android.util.Pair(transition_img_1 as View, "img")
//            val p2 = android.util.Pair(transition_text_1 as View, "text")
            val options =
                ActivityOptions.makeSceneTransitionAnimation(this, p1)

            val intent = Intent(this, TransitionActivity2::class.java)
            startActivity(intent, options.toBundle())
        }
    }
}