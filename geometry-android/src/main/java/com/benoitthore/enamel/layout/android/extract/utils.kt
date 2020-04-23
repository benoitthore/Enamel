package com.benoitthore.enamel.layout.android.extract

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.res.Resources
import android.os.SystemClock

inline fun prepareAnimation(
    duration: Long = 1000L,
    init: ValueAnimator.() -> Unit = {},
    crossinline onUpdate: (progress: Float) -> Unit
): ValueAnimator =
    ValueAnimator.ofFloat(0f, 1f).apply {
        addUpdateListener { onUpdate(it.animatedFraction) }
        this.duration = duration
        init()
    }


internal inline val Number.dp: Int
    get() = (toFloat() * Resources.getSystem().displayMetrics.density).toInt()
