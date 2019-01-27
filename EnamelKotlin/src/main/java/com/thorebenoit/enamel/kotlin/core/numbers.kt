package com.thorebenoit.enamel.kotlin.core

import java.util.*


inline val Number.i get() = toInt()
inline val Number.f get() = toFloat()
inline val Number.d get() = toDouble()
inline val Number.L get() = toLong()
inline val Number.b get() = toByte()


private val RANDOM = Random()

fun random(min: Number, max: Number): Float = lerp(RANDOM.nextFloat(), min, max)

fun lerp(fraction: Number, from: Number, to: Number): Float =
    from.f + fraction.f * (to.f - from.f)

inline fun Number.nearlyEquals(n2: Number, threshold: Number = 1) =
    Math.abs(this.d - n2.d) < threshold.d

fun Number.constrain(min: Number, max: Number): Float {
    val max = max.f
    val min = min.f
    val number = this.f
    return if (number < min) min else if (number > max) max else number
}
