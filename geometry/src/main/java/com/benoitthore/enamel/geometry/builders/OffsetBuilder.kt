package com.benoitthore.enamel.geometry.builders

import com.benoitthore.enamel.geometry.primitives.offset.EOffset

interface OffsetBuilder : BaseBuilder {

    //
    fun Offset(all: Number): EOffset = Offset(all, all, all, all)
    fun Offset(copy: EOffset): EOffset = Offset(
        top = copy.top,
        right = copy.right,
        bottom = copy.bottom,
        left = copy.left
    )

    val Offset get() = _Offset

    object _Offset {
        fun zero(target: EOffset = E.Offset()) = target.apply { set(0, 0, 0, 0) }
    }
}