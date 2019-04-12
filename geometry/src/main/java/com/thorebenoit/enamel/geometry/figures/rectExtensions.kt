package com.thorebenoit.enamel.geometry.figures

import com.thorebenoit.enamel.core.math.d
import com.thorebenoit.enamel.core.math.f
import com.thorebenoit.enamel.geometry.alignement.ERectEdge
import com.thorebenoit.enamel.geometry.alignement.alignement
import com.thorebenoit.enamel.geometry.alignement.isHorizontal
import com.thorebenoit.enamel.geometry.alignement.isVertical
import com.thorebenoit.enamel.geometry.primitives.EOffset
import com.thorebenoit.enamel.geometry.primitives.EPoint

fun ERectType.dividedFraction(fraction: Number, from: ERectEdge): Pair<ERectType, ERectType> {
    val fraction = fraction.f

    return if (from.isVertical) {
        divided(height * fraction, from)
    } else {
        divided(width * fraction, from)
    }
}

fun ERectType.divided(distance: Number, from: ERectEdge): Pair<ERectType, ERectType> {
    val distance = distance.f

    if (from.isVertical && distance >= height) {
        return this to ERectType.zero
    }
    if (from.isHorizontal && distance >= width) {
        return this to ERectType.zero
    }

    val sliceHeight: Float
    val sliceWidth: Float
    val remainderHeight: Float
    val remainderWidth: Float

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

    val slice = rectAlignedInside(from.alignement, sliceWidth size sliceHeight)
    val remainderSize = ESizeType(remainderWidth, remainderHeight)

    return slice to slice.rectAlignedOutside(from.alignement.flipped, remainderSize)
}

operator fun ERectType.minus(padding: EOffset) = padding(padding)
operator fun ERectType.plus(padding: EOffset) = expand(padding)


fun ERectCenter(
    position: EPoint,
    width: Number, height: Number, buffer: ERect = ERect()
) = ERectCenter(position.x, position.y, width, height, buffer)

fun ERectCenter(
    x: Number = 0f, y: Number = 0f,
    width: Number, height: Number, buffer: ERect = ERect()
): ERect {

    val width = width.f
    val height = height.f
    val x = x.f - width / 2
    val y = y.f - height / 2


    return buffer.set(x = x, y = y, width = width, height = height)
}


fun ERectCorners(
    corner1: EPoint,
    corner2: EPoint,
    buffer: ERect = ERect()
) = ERectCorners(corner1.x, corner1.y, corner2.x, corner2.y, buffer)

fun ERectCorners(
    corner1X: Number = 0,
    corner1Y: Number = 0,
    corner2X: Number = 0,
    corner2Y: Number = 0,
    buffer: ERect = ERect()
): ERect {
    return ERectSides(
        top = Math.min(corner1Y.d, corner2Y.d),
        bottom = Math.max(corner1Y.d, corner2Y.d),
        left = Math.min(corner1X.d, corner2X.d),
        right = Math.max(corner1X.d, corner2X.d),
        buffer = buffer
    )
}

fun ERectSides(left: Number, top: Number, right: Number, bottom: Number, buffer: ERect = ERect()): ERect {
    buffer.top = top.f
    buffer.left = left.f
    buffer.right = right.f
    buffer.bottom = bottom.f
    return buffer
}

fun ERectAnchorPos(anchor: EPoint, position: EPoint, size: ESize, buffer: ERect = ERect()) =
    buffer.set(
        x = position.x - size.width * anchor.x,
        y = position.y - size.height * anchor.y,
        size = size
    )


fun List<ERectType>.union(buffer: ERect = ERect()): ERect {
    if (isEmpty()) {
        return ERect()
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