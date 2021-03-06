package com.benoitthore.animation

import android.animation.ValueAnimator

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

//fun <T : CanSetBounds<*,*>> T.animateTo(
//    other: HasBounds<*,*>,
//    duration: Long = 1000L,
//    init: ValueAnimator.() -> Unit = {}
//) {
//    val from = getBounds()
//    val to = other.getBounds()
//    prepareAnimation(duration, init) { progress ->
//        // TODO
////        lerp(progress, from = from, to = to)
//    }.start()
//}