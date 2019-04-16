package com.thorebenoit.enamel.geometry.alignement

import com.thorebenoit.enamel.geometry.figures.ESize
import com.thorebenoit.enamel.geometry.primitives.EOffset
import com.thorebenoit.enamel.geometry.primitives.times

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

fun ESize.along(axis: ELayoutAxis): Float = when (axis) {
    ELayoutAxis.vertical -> height
    ELayoutAxis.horizontal -> width
}

fun ESize.with(side: ELayoutAxis, newValue: Number) = ESize(
    width = if (side == ELayoutAxis.horizontal) newValue else width,
    height = if (side == ELayoutAxis.vertical) newValue else height
)

fun ESize.fillSize(size: ESize): ESize {
    return (scaleToFill(size) * this).abs()
}

fun ESize.fitSize(size: ESize): ESize {
    return (scaleToFit(size) * this).abs()
}


fun ESize.scaleToFill(size: ESize): Float {
    val a = abs()
    val b = size.abs()
    return Math.max(b.width / a.width, b.height / a.height) // TODO what if Infinite
}


fun ESize.scaleToFit(size: ESize): Float {
    val a = abs()
    val b = size.abs()
    return Math.min(b.width / a.width, b.height / a.height) // TODO what if Infinite
}


fun EOffset.along(axis: ELayoutAxis): Float = when (axis) {
    ELayoutAxis.vertical -> vertical
    ELayoutAxis.horizontal -> horizontal
}