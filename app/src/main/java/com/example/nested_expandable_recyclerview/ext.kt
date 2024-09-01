package com.example.nested_expandable_recyclerview

import android.animation.ValueAnimator
import android.view.View
import android.view.ViewGroup

/**
Created by Masum Mehedi on 8/27/2024.
masumehedissl@gmail.com
 **/

// Extension function to expand a View
fun View.expand() {
    measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    val targetHeight = measuredHeight

    layoutParams.height = 0
    visibility = View.VISIBLE

    val animator = ValueAnimator.ofInt(0, targetHeight)
    animator.addUpdateListener { animation ->
        layoutParams.height = animation.animatedValue as Int
        requestLayout()
    }
    animator.duration = 300 // Animation duration in milliseconds
    animator.start()
}

// Extension function to collapse a View
fun View.collapse() {
    val initialHeight = measuredHeight

    val animator = ValueAnimator.ofInt(initialHeight, 0)
    animator.addUpdateListener { animation ->
        val value = animation.animatedValue as Int
        layoutParams.height = value
        requestLayout()
        if (value == 0) {
            visibility = View.GONE
        }
    }
    animator.duration = 300 // Animation duration in milliseconds
    animator.start()
}