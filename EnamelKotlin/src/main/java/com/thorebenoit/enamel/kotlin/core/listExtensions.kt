package com.thorebenoit.enamel.kotlin.core

import com.thorebenoit.enamel.kotlin.core.math.Scale
import com.thorebenoit.enamel.kotlin.core.math.i

// TODO Fix
//operator fun <E> List<E>.get(percentage: Float) = this[((size - 1) * percentage).toInt()]
operator fun <E> List<E>.get(percentage: Float) =
    this[Scale.map(percentage, 0, 1, 0, size).toInt()]

fun <E> MutableList<E>.limitFirst(n: Int) {
    while (size >= n) {
        removeAt(size - 1)
    }
}

fun <E> MutableList<E>.limitLast(n: Int) {
    while (size >= n) {
        removeAt(0)
    }
}