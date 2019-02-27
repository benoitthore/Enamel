package com.thorebenoit.enamel.kotlin.geometry.alignement

import com.thorebenoit.enamel.kotlin.geometry.figures.ERect
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeType
import com.thorebenoit.enamel.kotlin.geometry.primitives.EOffset
import com.thorebenoit.enamel.kotlin.geometry.primitives.times

enum class ELayoutAxis {
    vertical, horizontal
}

val ELayoutAxis.isVertical get() = this == ELayoutAxis.vertical


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

fun ESizeType.fillSize(size: ESizeType): ESizeType {
    return (scaleToFill(size) * this).abs()
}

fun ESizeType.fitSize(size: ESizeType): ESizeType {
    return (scaleToFit(size) * this).abs()
}


fun ESizeType.scaleToFill(size: ESizeType): Float {
    val a = abs()
    val b = size.abs()
    return Math.max(b.width / a.width, b.height / a.height) // TODO what if Infinite
}


fun ESizeType.scaleToFit(size: ESizeType): Float {
    val a = abs()
    val b = size.abs()
    return Math.min(b.width / a.width, b.height / a.height) // TODO what if Infinite
}


fun EOffset.along(axis: ELayoutAxis): Float = when (axis) {
    ELayoutAxis.vertical -> vertical
    ELayoutAxis.horizontal -> horizontal
}