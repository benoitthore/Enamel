package com.thorebenoit.enamel.kotlin.geometry.alignement

import com.thorebenoit.enamel.kotlin.geometry.figures.ERect
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeType
import com.thorebenoit.enamel.kotlin.geometry.primitives.EOffset

enum class ELayoutAxis {
    vertical, horizontal
}

val ELayoutAxis.opposite
    get() = when (this) {
        ELayoutAxis.vertical -> ELayoutAxis.horizontal
        ELayoutAxis.horizontal -> ELayoutAxis.vertical
    }


val EAlignment.layoutAxis: ELayoutAxis?
    get() {
        if (isVertical) {
            return ELayoutAxis.vertical
        }
        if (isHorizontal) {
            return ELayoutAxis.horizontal
        }
        return null
    }

val ERectEdge.layoutAxis get() = if (isVertical) ELayoutAxis.vertical else ELayoutAxis.horizontal

fun ESizeType.along(axis: ELayoutAxis): Float = when (axis) {
    ELayoutAxis.vertical -> height
    ELayoutAxis.horizontal -> width
}

fun ESizeType.with(side: ELayoutAxis, newValue: Number) = ESizeType(
    width = if (side == ELayoutAxis.horizontal) newValue else width,
    height = if (side == ELayoutAxis.vertical) newValue else height
)

fun EOffset.along(axis: ELayoutAxis): Float = when (axis) {
    ELayoutAxis.vertical -> vertical
    ELayoutAxis.horizontal -> horizontal
}