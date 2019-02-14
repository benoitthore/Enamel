package com.thorebenoit.enamel.kotlin.genetics

import com.thorebenoit.enamel.kotlin.core.math.f
import com.thorebenoit.enamel.kotlin.core.math.random

fun randomFloatArray(n: Int) = (0 until n).map { random() }.toFloatArray()

fun <T : Any, N : Number> Map<T, N>.randomWithWeigth(): T {
    val totalWeight = values.asSequence().map { it.f }.sum()
    var value = random(0, totalWeight)
    forEach { (o, w) ->
        value -= w.f
        if (value < 0) {
            return o
        }
    }

    return toList().random().first
}

fun <T : Any, N : Number> MutableMap<T, N>.pollRandomWithWeigth(): T {
    val totalWeight = values.asSequence().map { it.f }.sum()
    var value = random(0, totalWeight)
    forEach { (o, w) ->
        value -= w.f
        if (value < 0) {
            remove(o)
            return o
        }
    }

    return toList().random().first.apply { remove(this) }
}