package com.thorebenoit.enamel.kotlin.core.data

import com.thorebenoit.enamel.kotlin.core.math.Scale
import java.nio.charset.Charset
import java.util.*

fun List<Byte>.toStringFromBytes() = toByteArray().toStringFromBytes()
fun ByteArray.toStringFromBytes() = toString(Charset.defaultCharset())

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

fun <E> MutableList<E>.addIfDoesntContains(e: E) {
    if (!contains(e)) {
        add(e)
    }
}

fun <E> List<E>.split(splitSize: Int): List<List<E>> {
    val ret = mutableListOf<List<E>>()
    for (i in 0 until size step splitSize) {
        if (i + splitSize <= size) {
            ret += subList(i, i + splitSize)
        } else {
            ret += subList(i, size)
        }
    }
    return ret
}

inline fun <T> Iterable<T>.findIndex(predicate: (T) -> Boolean): Int? {
    forEachIndexed { index, t -> if (predicate(t)) return index }
    return null
}