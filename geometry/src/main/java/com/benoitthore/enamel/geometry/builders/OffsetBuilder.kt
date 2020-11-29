package com.benoitthore.enamel.geometry.builders

import com.benoitthore.enamel.geometry.primitives.offset.EOffset
import com.benoitthore.enamel.geometry.primitives.offset.EOffsetImpl


fun Offset(
    top: Number = 0f, right: Number = 0f, bottom: Number = 0f, left: Number = 0f
): EOffset =
    EOffsetImpl(
        top = top.toFloat(),
        right = right.toFloat(),
        bottom = bottom.toFloat(),
        left = left.toFloat()
    )

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
