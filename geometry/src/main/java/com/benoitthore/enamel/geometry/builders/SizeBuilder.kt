package com.benoitthore.enamel.geometry.builders

import com.benoitthore.enamel.geometry.primitives.size.ESize
import com.benoitthore.enamel.geometry.primitives.size.ESizeImpl

fun Size(width: Number = 0, height: Number = 0): ESize =
    ESizeImpl(width, height)

//
fun Size(other: ESize) = Size(other.width, other.height)

//

fun SizeSquare(n: Number) = Size(n, n)

val Size get() = _Size

object _Size {
    fun Zero(target: ESize = Size()) = target.set(0, 0)
    fun GreatestSize(target: ESize = Size()) =
        target.set(Float.MAX_VALUE, Float.MAX_VALUE)

    fun Square(size: Number) = Size(size, size)

    fun Random(
        minSize: Number,
        maxSize: Number,
        target: ESize = Size()
    ): ESize =
        Random(minSize, maxSize, minSize, maxSize, target)

    fun Random(
        minWidth: Number = 0,
        maxWidth: Number = 1,
        minHeight: Number = 0,
        maxHeight: Number = 1,
        target: ESize = Size()
    ): ESize = target.set(
        com.benoitthore.enamel.core.math.random(minWidth, maxWidth),
        com.benoitthore.enamel.core.math.random(minHeight, maxHeight)
    )
}

