package com.benoitthore.enamel.core.math

import java.util.*
import kotlin.math.abs

inline val Number.i get() = toInt()
inline val Number.f get() = toFloat()
inline val Number.d get() = toDouble()
inline val Number.L get() = toLong()
inline val Number.b get() = toByte()
inline val Number.bool get() = this.toInt() == 1
inline val Boolean.i get() = if (this) 1 else 0


inline val Number.percent get() = f / 100f
const val œ = 0.001f // ALT + Q on Mac


private val RANDOM = Random()

fun randomBool() = random() > 0.5
fun randomSign() = if (randomBool()) 1 else -1

fun random(): Float = random(min = 0f, max = 1f)
fun random(max: Number): Float = random(min = 0f, max = max)
fun random(min: Number, max: Number): Float =
    RANDOM.nextFloat().lerp(min, max)

inline fun Number.interpolate(from: Number, to: Number, interpolator: (Number) -> Number): Float =
    from.f + interpolator(this.f).f * (to.f - from.f)

fun Number.lerp(from: Number, to: Number): Float = interpolate(from, to) { it }
fun Number.map(start1: Number, stop1: Number, start2: Number, stop2: Number) = Scale.map(
    this,
    start1 = start1,
    stop1 = stop1,
    start2 = start2,
    stop2 = stop2
)

inline infix fun Number.nearlyEquals(n2: Number) = nearlyEquals(n2, œ)
inline fun Number.nearlyEquals(n2: Number, threshold: Number) =
    abs(this.d - n2.d) < threshold.d

fun Number.constrain(min: Number, max: Number): Float {
    val max = max.f
    val min = min.f
    val number = this.f
    return if (number < min) min else if (number > max) max else number
}
