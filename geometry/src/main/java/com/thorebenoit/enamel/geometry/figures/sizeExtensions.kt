package com.thorebenoit.enamel.geometry.figures

import com.thorebenoit.enamel.core.math.random
import com.thorebenoit.enamel.geometry.alignement.ELayoutAxis
import com.thorebenoit.enamel.geometry.alignement.isVertical
import com.thorebenoit.enamel.geometry.primitives.times


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


fun List<ESize>.union(buffer: ESizeMutable = ESizeMutable()): ESizeMutable {
    var width = Float.MIN_VALUE
    var height = Float.MIN_VALUE

    forEach {
        if (it.width > width) {
            width = it.width
        }
        if (it.height > height) {
            height = it.height
        }
    }

    return buffer.set(width, height)
}

fun List<ESize>.unionAlongAxis(axis: ELayoutAxis, buffer: ESizeMutable = ESizeMutable()): ESizeMutable {

    var sum = 0f

    forEach {
        sum += it.along(axis)
    }

    val union = union(buffer = buffer)
    if (axis.isVertical) {
        union.height = sum
    } else {
        union.width = sum
    }

    return union
}

fun randomSize(maxWidth: Number = 1, maxHeight: Number = 1) = random(maxWidth) size random(maxHeight)