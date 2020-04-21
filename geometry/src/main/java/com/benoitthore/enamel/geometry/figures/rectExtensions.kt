package com.benoitthore.enamel.geometry.figures

import com.benoitthore.enamel.core.math.constrain
import com.benoitthore.enamel.core.math.d
import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.alignement.ERectEdge
import com.benoitthore.enamel.geometry.primitives.EOffset
import com.benoitthore.enamel.geometry.primitives.EPoint

fun ERect.dividedFraction(
    fraction: Number,
    from: ERectEdge,
    target: Pair<ERectMutable, ERectMutable> = ERectMutable() to ERectMutable()
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
    target: Pair<ERectMutable, ERectMutable> = ERectMutable() to ERectMutable()
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
    val remainderSize = ESize(remainderWidth, remainderHeight)
    val remainder =
        slice.rectAlignedOutside(from.alignement.flipped, remainderSize, target = target.second)
    return slice to remainder
}

operator fun ERect.minus(padding: EOffset) = padding(padding)
operator fun ERect.plus(padding: EOffset) = expand(padding)

fun ERectCenter(
    position: EPoint,
    size: ESize, target: ERectMutable = ERectMutable()
) = ERectCenter(position.x, position.y, size.width, size.height, target)


fun ERectCenter(
    position: EPoint,
    width: Number, height: Number, target: ERectMutable = ERectMutable()
) = ERectCenter(position.x, position.y, width, height, target)

fun ERectCenter(
    x: Number = 0f, y: Number = 0f,
    width: Number, height: Number, target: ERectMutable = ERectMutable()
): ERectMutable {

    val width = width.f
    val height = height.f
    val x = x.f - width / 2
    val y = y.f - height / 2

    return target.set(x = x, y = y, width = width, height = height)
}


fun ERectCorners(
    corner1: EPoint,
    corner2: EPoint,
    target: ERectMutable = ERectMutable()
) = ERectCorners(corner1.x, corner1.y, corner2.x, corner2.y, target)

fun ERectCorners(
    corner1X: Number = 0,
    corner1Y: Number = 0,
    corner2X: Number = 0,
    corner2Y: Number = 0,
    target: ERectMutable = ERectMutable()
): ERectMutable {
    return ERectSides(
        top = Math.min(corner1Y.d, corner2Y.d),
        bottom = Math.max(corner1Y.d, corner2Y.d),
        left = Math.min(corner1X.d, corner2X.d),
        right = Math.max(corner1X.d, corner2X.d),
        target = target
    )
}

fun ERectSides(
    left: Number,
    top: Number,
    right: Number,
    bottom: Number,
    target: ERectMutable = ERectMutable()
): ERectMutable {
    target.top = top.f
    target.left = left.f
    target.right = right.f
    target.bottom = bottom.f
    return target
}

fun ERectAnchorPos(
    anchor: EPoint,
    position: EPoint,
    size: ESizeMutable,
    target: ERectMutable = ERectMutable()
) =
    target.set(
        x = position.x - size.width * anchor.x,
        y = position.y - size.height * anchor.y,
        size = size
    )


fun List<ERect>.union(target: ERectMutable = ERectMutable()): ERectMutable {
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
        target = target
    )
}