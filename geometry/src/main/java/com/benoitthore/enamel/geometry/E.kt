package com.benoitthore.enamel.geometry

import com.benoitthore.enamel.core.math.d
import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.figures.*
import com.benoitthore.enamel.geometry.primitives.EPoint
import com.benoitthore.enamel.geometry.primitives.EPointMutable
import kotlin.math.max
import kotlin.math.min

/**
 * This interface can be used to create any kind of shape provided by this library
 */
interface E {
    fun point(x: Number = 0, y: Number = 0): EPoint
    fun circle(center: EPoint = point(), radius: Number = 0f): ECircle
    fun size(width: Number = 0, height: Number = 0): ESize
    fun rect(origin: EPoint = point(), size: ESize = size()): ERect

    fun mpoint(x: Number = 0, y: Number = 0): EPointMutable
    fun mcircle(center: EPointMutable = mpoint(), radius: Number = 0f): ECircleMutable
    fun msize(width: Number = 0, height: Number = 0): ESizeMutable
    fun mrect(origin: EPointMutable = mpoint(), size: ESizeMutable = msize()): ERectMutable
}

fun E.rectCenter(
    position: EPoint,
    size: ESize, target: ERectMutable = mrect()
) = rectCenter(position.x, position.y, size.width, size.height, target)

fun E.rectCenter(
    position: EPoint,
    width: Number, height: Number, target: ERectMutable = mrect()
) = rectCenter(position.x, position.y, width, height, target)

fun E.rectCenter(
    x: Number = 0f, y: Number = 0f,
    width: Number, height: Number, target: ERectMutable = mrect()
): ERectMutable {

    val width = width.f
    val height = height.f
    val x = x.f - width / 2
    val y = y.f - height / 2

    return target.set(x = x, y = y, width = width, height = height)
}

fun E.rectCorners(
    corner1: EPoint,
    corner2: EPoint,
    target: ERectMutable = mrect()
) = rectCorners(corner1.x, corner1.y, corner2.x, corner2.y, target)

fun E.rectCorners(
    corner1X: Number = 0,
    corner1Y: Number = 0,
    corner2X: Number = 0,
    corner2Y: Number = 0,
    target: ERectMutable = mrect()
): ERectMutable {
    return rectSides(
        top = min(corner1Y.d, corner2Y.d),
        bottom = max(corner1Y.d, corner2Y.d),
        left = min(corner1X.d, corner2X.d),
        right = max(corner1X.d, corner2X.d),
        target = target
    )
}

fun E.rectSides(
    left: Number,
    top: Number,
    right: Number,
    bottom: Number,
    target: ERectMutable = mrect()
): ERectMutable {
    target.top = top.f
    target.left = left.f
    target.right = right.f
    target.bottom = bottom.f
    return target
}

fun E.rectAnchorPos(
    anchor: EPoint,
    position: EPoint,
    size: ESizeMutable,
    target: ERectMutable = mrect()
) =
    target.set(
        x = position.x - size.width * anchor.x,
        y = position.y - size.height * anchor.y,
        size = size
    )