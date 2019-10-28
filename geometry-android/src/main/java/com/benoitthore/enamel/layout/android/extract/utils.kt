package com.benoitthore.enamel.layout.android.extract

import android.animation.ValueAnimator

fun prepareAnimation(
    duration: Long = 1000L,
    init: ValueAnimator.() -> Unit = {},
    onUpdate: (Float) -> Unit
): ValueAnimator =
    ValueAnimator.ofFloat(0f, 1f).apply {
        addUpdateListener { onUpdate(it.animatedFraction) }
        this.duration = duration
        init()
    }