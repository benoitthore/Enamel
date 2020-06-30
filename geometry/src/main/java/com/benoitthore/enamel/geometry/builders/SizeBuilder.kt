package com.benoitthore.enamel.geometry.builders

import com.benoitthore.enamel.geometry.allocate
import com.benoitthore.enamel.geometry.primitives.size.ESize

interface SizeBuilder : BaseBuilder {

    //
    fun Size(other: ESize) = Size(other.width, other.height)

    //

    fun msizeSquare(n: Number) = Size(n, n)
    fun SizeSquare(n: Number): ESize = msizeSquare(n)

    val Size get() = _Size

    object _Size {
        fun zero(target: ESize = E.Size()) = target.set(0, 0)
        fun greatestSize(target: ESize = E.Size()) =
            target.set(Float.MAX_VALUE, Float.MAX_VALUE)

        fun square(size: Number) = E.Size(size, size)

        fun random(
            minSize: Number,
            maxSize: Number,
            target: ESize = E.Size()
        ): ESize =
            random(minSize, maxSize, minSize, maxSize, target)

        fun random(
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