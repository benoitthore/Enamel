package com.benoitthore.enamel.core

import com.benoitthore.enamel.core.math.Scale
import java.nio.charset.Charset

inline fun <T> Iterable<T>.contains(predicate: (T) -> Boolean) = any(predicate)


fun <T : Any> T.asList(): List<T> = listOf(this)

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

class LazyList<T> private constructor(
    val delegate: MutableList<T>,
    val maxSize: Int,
    val init: (Int) -> T

) : List<T> by delegate {

    constructor(maxSize: Int, init: (Int) -> T) : this(
        delegate = mutableListOf(),
        maxSize = maxSize,
        init = init
    )

    override val size: Int get() = maxSize

    override fun get(index: Int): T {
        if (index >= maxSize) {
            throw Exception("cannot retrieve index $index, max size is $maxSize")
        }
        if (delegate.size <= index) {
            for (i in delegate.size until index+1) {
                delegate += init(i)
            }
        }
        return delegate[index]
    }
}