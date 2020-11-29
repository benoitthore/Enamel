package com.benoitthore.enamel.geometry.builders

import com.benoitthore.enamel.geometry.primitives.size.ESize
import com.benoitthore.enamel.geometry.primitives.size.ESizeImpl
import com.benoitthore.enamel.geometry.primitives.size.ESizeMutable

fun Size(width: Number = 0, height: Number = 0): ESize =
    ESizeImpl(width, height)

fun Size(other: ESize) = Size(other.width, other.height)

fun SizeSquare(n: Number) = Size(n, n)


fun MutableSize(width: Number = 0, height: Number = 0): ESizeMutable =
    ESizeImpl(width, height)

fun MutableSize(other: ESize) = MutableSize(other.width, other.height)

fun MutableSizeSquare(n: Number) = MutableSize(n, n)

val Size get() = _Size

object _Size {
    fun Zero(target: ESizeMutable = MutableSize()) = target.set(0, 0)
    fun GreatestSize(target: ESizeMutable = MutableSize()) =
        target.set(Float.MAX_VALUE, Float.MAX_VALUE)

    fun Square(size: Number) = Size(size, size)

    fun Random(
        minSize: Number,
        maxSize: Number,
        target: ESizeMutable = MutableSize()
    ): ESize =
        Random(minSize, maxSize, minSize, maxSize, target)

    fun Random(
        minWidth: Number = 0,
        maxWidth: Number = 1,
        minHeight: Number = 0,
        maxHeight: Number = 1,
        target: ESizeMutable = MutableSize()
    ): ESize = target.set(
        com.benoitthore.enamel.core.math.random(minWidth, maxWidth),
        com.benoitthore.enamel.core.math.random(minHeight, maxHeight)
    )
}

