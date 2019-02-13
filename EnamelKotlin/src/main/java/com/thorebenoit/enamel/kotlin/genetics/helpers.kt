package com.thorebenoit.enamel.kotlin.genetics

import com.thorebenoit.enamel.kotlin.core.math.f
import com.thorebenoit.enamel.kotlin.core.math.random


fun <T : Any> Map<T, Number>.randomWithWeigth(): T {
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