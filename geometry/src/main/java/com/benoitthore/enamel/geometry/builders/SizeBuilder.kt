package com.benoitthore.enamel.geometry.builders

import com.benoitthore.enamel.geometry.allocate
import com.benoitthore.enamel.geometry.primitives.size.ESize

interface SizeBuilder : BaseBuilder {

    fun Size(width: Number = 0, height: Number = 0): ESize =
        SizeMutable(width, height)

    //
    fun SizeMutable(other: ESize) = SizeMutable(other.width, other.height)
    fun Size(other: ESize): ESize = SizeMutable(other)

    //

    fun msizeSquare(n: Number) = SizeMutable(n, n)
    fun SizeSquare(n: Number): ESize = msizeSquare(n)

    val Size get() = _Size
    val SizeMutable get() = _SizeMutable

    object _Size {
        val zero: ESize = allocate { E.Size() }
        val greatestSize: ESize = allocate { E.Size(Float.MAX_VALUE, Float.MAX_VALUE) }
        fun square(size: Number) = E.SizeMutable(size, size)

        fun random(
            minSize: Number,
            maxSize: Number,
            target: ESize = E.SizeMutable()
        ): ESize =
            _SizeMutable.random(minSize, maxSize, minSize, maxSize, target)

        fun random(
            minWidth: Number = 0,
            maxWidth: Number = 1,
            minHeight: Number = 0,
            maxHeight: Number = 1,
            target: ESize = E.SizeMutable()
        ): ESize = _SizeMutable.random(minWidth, maxWidth, minHeight, maxHeight, target)
    }

    object _SizeMutable {
        fun zero(target: ESize = E.SizeMutable()) = target.set(0, 0)
        fun greatestSize(target: ESize = E.SizeMutable()) =
            target.set(Float.MAX_VALUE, Float.MAX_VALUE)

        fun square(size: Number) = E.SizeMutable(size, size)

        fun random(
            minSize: Number,
            maxSize: Number,
            target: ESize = E.SizeMutable()
        ): ESize =
            random(minSize, maxSize, minSize, maxSize, target)

        fun random(
            minWidth: Number = 0,
            maxWidth: Number = 1,
            minHeight: Number = 0,
            maxHeight: Number = 1,
            target: ESize = E.SizeMutable()
        ): ESize = target.set(
            com.benoitthore.enamel.core.math.random(minWidth, maxWidth),
            com.benoitthore.enamel.core.math.random(minHeight, maxHeight)
        )
    }

}