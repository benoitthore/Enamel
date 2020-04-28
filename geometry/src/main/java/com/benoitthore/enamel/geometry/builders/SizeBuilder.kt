package com.benoitthore.enamel.geometry.builders

import com.benoitthore.enamel.geometry.allocate
import com.benoitthore.enamel.geometry.figures.ESize
import com.benoitthore.enamel.geometry.figures.ESizeMutable

interface SizeBuilder : BaseBuilder {

    fun Size(width: Number = 0, height: Number = 0): ESize =
        mSize(width, height)

    //
    fun mSize(other: ESize) = mSize(other.width, other.height)
    fun Size(other: ESize): ESize = mSize(other)

    //

    fun msizeSquare(n: Number) = mSize(n, n)
    fun sizeSquare(n: Number): ESize = msizeSquare(n)

    val Size get() = _Size
    val SizeMutable get() = _SizeMutable

    object _Size {
        val zero: ESize = allocate { E.Size() }
        val greatestSize: ESize = allocate { E.Size(Float.MAX_VALUE, Float.MAX_VALUE) }
        fun square(size: Number) = E.mSize(size, size)

        fun random(
            minSize: Number,
            maxSize: Number,
            target: ESizeMutable = E.mSize()
        ): ESize =
            _SizeMutable.random(minSize, maxSize, minSize, maxSize, target)

        fun random(
            minWidth: Number = 0,
            maxWidth: Number = 1,
            minHeight: Number = 0,
            maxHeight: Number = 1,
            target: ESizeMutable = E.mSize()
        ): ESize = _SizeMutable.random(minWidth, maxWidth, minHeight, maxHeight, target)
    }

    object _SizeMutable {
        fun zero(target: ESizeMutable = E.mSize()) = target.set(0, 0)
        fun greatestSize(target: ESizeMutable = E.mSize()) =
            target.set(Float.MAX_VALUE, Float.MAX_VALUE)

        fun square(size: Number) = E.mSize(size, size)

        fun random(
            minSize: Number,
            maxSize: Number,
            target: ESizeMutable = E.mSize()
        ): ESizeMutable =
            random(minSize, maxSize, minSize, maxSize, target)

        fun random(
            minWidth: Number = 0,
            maxWidth: Number = 1,
            minHeight: Number = 0,
            maxHeight: Number = 1,
            target: ESizeMutable = E.mSize()
        ): ESizeMutable = target.set(
            com.benoitthore.enamel.core.math.random(minWidth, maxWidth),
            com.benoitthore.enamel.core.math.random(minHeight, maxHeight)
        )
    }

}