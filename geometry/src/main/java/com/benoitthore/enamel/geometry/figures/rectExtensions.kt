package com.benoitthore.enamel.geometry.figures

import com.benoitthore.enamel.core.math.constrain
import com.benoitthore.enamel.core.math.d
import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.alignement.ERectEdge
import com.benoitthore.enamel.geometry.primitives.EOffset
import com.benoitthore.enamel.geometry.primitives.EPoint

fun ERect.dividedFraction(fraction: Number, from: ERectEdge): Pair<ERect, ERect> {
    val fraction = fraction.f

    return if (from.isVertical) {
        divided(height * fraction, from)
    } else {
        divided(width * fraction, from)
    }
}

fun ERect.divided(distance: Number, from: ERectEdge): Pair<ERect, ERect> {
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

    sliceHeight = sliceHeight.constrain(0,height)
    sliceWidth = sliceWidth.constrain(0,width)
    remainderHeight = remainderHeight.constrain(0,height)
    remainderWidth = remainderWidth.constrain(0,width)


    val slice = rectAlignedInside(from.alignement, sliceWidth size sliceHeight)
    val remainderSize = ESize(remainderWidth, remainderHeight)

    return slice to slice.rectAlignedOutside(from.alignement.flipped, remainderSize)
}

operator fun ERect.minus(padding: EOffset) = padding(padding)
operator fun ERect.plus(padding: EOffset) = expand(padding)

fun ERectCenter(
    position: EPoint,
    size: ESize, buffer: ERectMutable = ERectMutable()
) = ERectCenter(position.x, position.y, size.width, size.height, buffer)


fun ERectCenter(
    position: EPoint,
    width: Number, height: Number, buffer: ERectMutable = ERectMutable()
) = ERectCenter(position.x, position.y, width, height, buffer)

fun ERectCenter(
    x: Number = 0f, y: Number = 0f,
    width: Number, height: Number, buffer: ERectMutable = ERectMutable()
): ERectMutable {

    val width = width.f
    val height = height.f
    val x = x.f - width / 2
    val y = y.f - height / 2


    return buffer.set(x = x, y = y, width = width, height = height)
}


fun ERectCorners(
    corner1: EPoint,
    corner2: EPoint,
    buffer: ERectMutable = ERectMutable()
) = ERectCorners(corner1.x, corner1.y, corner2.x, corner2.y, buffer)

fun ERectCorners(
    corner1X: Number = 0,
    corner1Y: Number = 0,
    corner2X: Number = 0,
    corner2Y: Number = 0,
    buffer: ERectMutable = ERectMutable()
): ERectMutable {
    return ERectSides(
        top = Math.min(corner1Y.d, corner2Y.d),
        bottom = Math.max(corner1Y.d, corner2Y.d),
        left = Math.min(corner1X.d, corner2X.d),
        right = Math.max(corner1X.d, corner2X.d),
        buffer = buffer
    )
}

fun ERectSides(left: Number, top: Number, right: Number, bottom: Number, buffer: ERectMutable = ERectMutable()): ERectMutable {
    buffer.top = top.f
    buffer.left = left.f
    buffer.right = right.f
    buffer.bottom = bottom.f
    return buffer
}

fun ERectAnchorPos(anchor: EPoint, position: EPoint, size: ESizeMutable, buffer: ERectMutable = ERectMutable()) =
    buffer.set(
        x = position.x - size.width * anchor.x,
        y = position.y - size.height * anchor.y,
        size = size
    )


fun List<ERect>.union(buffer: ERectMutable = ERectMutable()): ERectMutable {
    if (isEmpty()) {
        return ERectMutable()
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
    return ERectSides(
        top = top,
        left = left,
        right = right,
        bottom = bottom,
        buffer = buffer
    )
}