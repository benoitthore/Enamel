package com.benoitthore.enamel.geometry.builders

import com.benoitthore.enamel.geometry.allocate
import com.benoitthore.enamel.geometry.primitives.offset.EOffset
import com.benoitthore.enamel.geometry.primitives.offset.EOffsetMutable

interface OffsetBuilder : BaseBuilder {

    fun offset(
        top: Number = 0f,
        right: Number = 0f,
        bottom: Number = 0f,
        left: Number = 0f
    ): EOffset =
        OffsetMutable(
            top = top,
            right = right,
            bottom = bottom,
            left = left
        )

    //
    fun moffset(all: Number) = OffsetMutable(all, all, all, all)
    fun offset(all: Number): EOffset = OffsetMutable(all, all, all, all)

    val OffsetMutable get() = _OffsetMutable
    val Offset get() = _Offset

    object _Offset {
        val zero: EOffset = allocate { E.offset() }
    }

    object _OffsetMutable {
        fun zero(target: EOffsetMutable) = target.reset()

    }
}