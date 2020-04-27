package com.benoitthore.enamel.geometry.builders

import com.benoitthore.enamel.geometry.allocate
import com.benoitthore.enamel.geometry.figures.ESize
import com.benoitthore.enamel.geometry.figures.ESizeMutable

interface SizeBuilder : BaseBuilder {

    fun size(width: Number = 0, height: Number = 0): ESize =
        msize(width, height)

    //
    fun msize(other: ESize) = msize(other.width, other.height)
    fun size(other: ESize): ESize = msize(other)

    val Size get() = _Size
    val SizeMutable get() = _SizeMutable

    object _Size {
        val zero: ESize = allocate { E.size() }
        val greatestSize: ESize = allocate { E.size(Float.MAX_VALUE, Float.MAX_VALUE) }
        fun square(size: Number) = E.msize(size, size)

        fun random(
            minSize: Number,
            maxSize: Number,
            target: ESizeMutable = E.msize()
        ): ESize =
            _SizeMutable.random(minSize, maxSize, minSize, maxSize, target)

        fun random(
            minWidth: Number = 0,
            maxWidth: Number = 1,
            minHeight: Number = 0,
            maxHeight: Number = 1,
            target: ESizeMutable = E.msize()
        ): ESize = _SizeMutable.random(minWidth, maxWidth, minHeight, maxHeight, target)
    }

    object _SizeMutable {
        fun zero(target: ESizeMutable = E.msize()) = target.set(0, 0)
        fun greatestSize(target: ESizeMutable = E.msize()) =
            target.set(Float.MAX_VALUE, Float.MAX_VALUE)

        fun square(size: Number) = E.msize(size, size)

        fun random(
            minSize: Number,
            maxSize: Number,
            target: ESizeMutable = E.msize()
        ): ESizeMutable =
            random(minSize, maxSize, minSize, maxSize, target)

        fun random(
            minWidth: Number = 0,
            maxWidth: Number = 1,
            minHeight: Number = 0,
            maxHeight: Number = 1,
            target: ESizeMutable = E.msize()
        ): ESizeMutable = target.set(
            com.benoitthore.enamel.core.math.random(minWidth, maxWidth),
            com.benoitthore.enamel.core.math.random(minHeight, maxHeight)
        )
    }

}