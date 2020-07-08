package com.benoitthore.enamel.geometry.builders

import com.benoitthore.enamel.geometry.allocate
import com.benoitthore.enamel.geometry.primitives.offset.EOffset

interface OffsetBuilder : BaseBuilder {

    fun offset(
        top: Number = 0f,
        right: Number = 0f,
        bottom: Number = 0f,
        left: Number = 0f
    ): EOffset =
        Offset(
            top = top,
            right = right,
            bottom = bottom,
            left = left
        )

    //
    fun offset(all: Number): EOffset = Offset(all, all, all, all)

    val Offset get() = _Offset

    object _Offset {
        fun zero(target: EOffset) = target.apply { set(0, 0, 0, 0) }
    }
}