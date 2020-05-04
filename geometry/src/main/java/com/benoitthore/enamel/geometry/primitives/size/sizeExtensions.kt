package com.benoitthore.enamel.geometry.primitives.size

import com.benoitthore.enamel.geometry.alignement.ELayoutAxis
import com.benoitthore.enamel.geometry.alignement.isVertical
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.primitives.times
import kotlin.math.max
import kotlin.math.min

infix fun Number.size(height: Number) = E.SizeMutable(this, height)

fun ESize.along(axis: ELayoutAxis): Float = when (axis) {
    ELayoutAxis.vertical -> height
    ELayoutAxis.horizontal -> width
}

fun ESize.with(side: ELayoutAxis, newValue: Number) = E.Size(
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
    return max(b.width / a.width, b.height / a.height) // TODO what if Infinite
}


fun ESize.scaleToFit(size: ESize): Float {
    val a = abs()
    val b = size.abs()
    return min(b.width / a.width, b.height / a.height) // TODO what if Infinite
}

fun List<ESize>.union(target: ESizeMutable = E.SizeMutable()): ESizeMutable {
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

    return target.set(width, height)
}

fun List<ESize>.unionAlongAxis(
    axis: ELayoutAxis,
    target: ESizeMutable = E.SizeMutable()
): ESizeMutable {

    var sum = 0f

    forEach {
        sum += it.along(axis)
    }

    val union = union(target = target)
    if (axis.isVertical) {
        union.height = sum
    } else {
        union.width = sum
    }

    return union
}