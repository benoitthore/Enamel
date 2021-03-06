package com.benoitthore.enamel.geometry.figures.rect

import com.benoitthore.enamel.core.math.constrain
import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.alignement.ERectEdge
import com.benoitthore.enamel.geometry.alignement.selfAlignInside
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.functions.EShape
import com.benoitthore.enamel.geometry.functions.expand
import com.benoitthore.enamel.geometry.functions.setSize
import com.benoitthore.enamel.geometry.primitives.offset.EOffset

fun ERect.dividedFraction(
    fraction: Number,
    from: ERectEdge,
    target: Pair<ERect, ERect> = E.Rect() to E.Rect()
): Pair<ERect, ERect> {
    val fraction = fraction.f

    return if (from.isVertical) {
        divided(height * fraction, from, target = target)
    } else {
        divided(width * fraction, from, target = target)
    }
}

fun ERect.divided(
    distance: Number,
    from: ERectEdge,
    target: Pair<ERect, ERect> = E.Rect() to E.Rect()
): Pair<ERect, ERect> {
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


    /* OLD CODE
     val slice =
         alignedInside(from.alignement, sliceWidth size sliceHeight, target = target.first)
     val remainderSize = E.Size(remainderWidth, remainderHeight)
     val remainder =
         slice.alignedOutside(from.alignement.flipped, remainderSize, target = target.second)

     */
    val slice = target.first.selfAlignInside(this, from.alignement).setSize(sliceWidth, sliceHeight)
    val remainder = target.second.selfAlignInside(slice, from.alignement.flipped)
        .setSize(remainderWidth, remainderHeight)

    TODO("Check if this works")
    return slice to remainder
}

operator fun ERect.minus(padding: EOffset): ERect = TODO()// padding(padding).ensureRect()
operator fun ERect.plus(padding: EOffset) = expand(padding)

fun List<ERect>.union(target: ERect = E.Rect()): ERect {
    if (isEmpty()) {
        return target
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
    return E.RectSides(
        top = top,
        left = left,
        right = right,
        bottom = bottom,
        target = target
    )
}