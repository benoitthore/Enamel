package com.benoitthore.enamel.geometry.figures

import com.benoitthore.enamel.core.math.constrain
import com.benoitthore.enamel.core.math.d
import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.alignement.ERectEdge
import com.benoitthore.enamel.geometry.e.E
import com.benoitthore.enamel.geometry.e.rectSides
import com.benoitthore.enamel.geometry.primitives.EOffset
import com.benoitthore.enamel.geometry.primitives.EPoint

fun ERect.dividedFraction(
    fraction: Number,
    from: ERectEdge,
    target: Pair<ERectMutable, ERectMutable> = E.mrect() to E.mrect()
): Pair<ERect, ERect> {
    val fraction = fraction.f

    return if (from.isVertical) {
        divided(height * fraction, from,target=target)
    } else {
        divided(width * fraction, from,target=target)
    }
}

fun ERect.divided(
    distance: Number,
    from: ERectEdge,
    target: Pair<ERectMutable, ERectMutable> = E.mrect() to E.mrect()
): Pair<ERectMutable, ERectMutable> {
    val distance = distance.f

    var sliceHeight: Float
    var sliceWidth: Float
    var remainderHeight: Float
    var remainderWidth: Float

    if (from.isVertical) {
        sliceHeight = distance
        sliceWidth = width
        remainderHeight = height - distance
        remainderWidth = width
    } else {
        sliceHeight = height
        sliceWidth = distance
        remainderHeight = height
        remainderWidth = width - distance
    }

    sliceHeight = sliceHeight.constrain(0, height)
    sliceWidth = sliceWidth.constrain(0, width)
    remainderHeight = remainderHeight.constrain(0, height)
    remainderWidth = remainderWidth.constrain(0, width)


    val slice =
        rectAlignedInside(from.alignement, sliceWidth size sliceHeight, target = target.first)
    val remainderSize = E.size(remainderWidth, remainderHeight)
    val remainder =
        slice.rectAlignedOutside(from.alignement.flipped, remainderSize, target = target.second)
    return slice to remainder
}

operator fun ERect.minus(padding: EOffset) = padding(padding)
operator fun ERect.plus(padding: EOffset) = expand(padding)

fun List<ERect>.union(target: ERectMutable = E.mrect()): ERectMutable {
    if (isEmpty()) {
        return E.mrect()
    }

    var left = Float.MAX_VALUE
    var top = Float.MAX_VALUE
    var right = Float.MIN_VALUE
    var bottom = Float.MIN_VALUE
    forEach {
        if (it.left < left) {
            left = it.left
        }

        if (it.top < top) {
            top = it.top
        }

        if (it.right > right) {
            right = it.right
        }

        if (it.bottom > bottom) {
            bottom = it.bottom
        }
    }
    return E.rectSides(
        top = top,
        left = left,
        right = right,
        bottom = bottom,
        target = target
    )
}