package com.benoitthore.enamel.android.extract

import android.animation.ValueAnimator

fun prepareAnimation(duration: Long = 1000L, onUpdate: (Float) -> Unit): ValueAnimator =
    ValueAnimator.ofFloat(0f, 1f).apply {
        addUpdateListener { onUpdate(it.animatedFraction) }
        this.duration = duration
    }