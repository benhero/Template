package com.ben.template.function.transition

import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.ChangeClipBounds
import android.transition.ChangeImageTransform
import android.transition.TransitionSet
import androidx.appcompat.app.AppCompatActivity
import com.ben.template.R

/**
 * Transition共享元素转场
 *
 * @author Benhero
 */
class TransitionActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val transitionSet = TransitionSet()
        transitionSet.addTransition(ChangeBounds())
        transitionSet.addTransition(ChangeClipBounds())
        transitionSet.addTransition(ChangeImageTransform())
        window.sharedElementEnterTransition = transitionSet
        window.sharedElementExitTransition = transitionSet

        setContentView(R.layout.activity_transition2)

    }
}