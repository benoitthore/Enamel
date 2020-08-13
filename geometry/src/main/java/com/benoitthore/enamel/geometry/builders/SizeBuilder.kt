package com.benoitthore.enamel.geometry.builders

import com.benoitthore.enamel.geometry.primitives.size.ESize

interface SizeBuilder : BaseBuilder {

    //
    fun Size(other: ESize) = Size(other.width, other.height)

    //

    fun msizeSquare(n: Number) = Size(n, n)
    fun SizeSquare(n: Number): ESize = msizeSquare(n)

    val Size get() = _Size

    object _Size {
        fun Zero(target: ESize = E.Size()) = target.set(0, 0)
        fun GreatestSize(target: ESize = E.Size()) =
            target.set(Float.MAX_VALUE, Float.MAX_VALUE)

        fun Square(size: Number) = E.Size(size, size)

        fun Random(
            minSize: Number,
            maxSize: Number,
            target: ESize = E.Size()
        ): ESize =
            Random(minSize, maxSize, minSize, maxSize, target)

        fun Random(
            minWidth: Number = 0,
            maxWidth: Number = 1,
            minHeight: Number = 0,
            maxHeight: Number = 1,
            target: ESize = E.Size()
        ): ESize = target.set(
            com.benoitthore.enamel.core.math.random(minWidth, maxWidth),
            com.benoitthore.enamel.core.math.random(minHeight, maxHeight)
        )
    }

}