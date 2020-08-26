package com.benoitthore.enamel.geometry.functions

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.alignement.NamedPoint
import com.benoitthore.enamel.geometry.primitives.point.EPoint


fun <T : EShape<*>> T.scaleRelative(
    scaleFactor: Number,
    pointX: Number,
    pointY: Number,
    target: T = copy()
): T = scaleRelative2D(
    scaleFactorX = scaleFactor,
    scaleFactorY = scaleFactor,
    pointX = pointX,
    pointY = pointY,
    target = target
)

fun <T : EShape<*>> T.scaleRelative2D(
    scaleFactorX: Number,
    scaleFactorY: Number,
    pointX: Number,
    pointY: Number,
    target: T = copy()
): T {
    target.setBounds(this)

    val scaleFactorX = scaleFactorX.f
    val scaleFactorY = scaleFactorY.f

    target.setOriginSize(
        x = originX + (pointX.f - originX) * (1f - scaleFactorX),
        y = originY + (pointY.f - originY) * (1f - scaleFactorY),
        width = width * scaleFactorX,
        height = height * scaleFactorY
    )
    return target
}

//// //// //// //// //// //// //// ////
//// //// //// //// //// //// //// ////

fun <T : EShape<*>> T.scale(
    scaleFactor: Number,
    pivot: EPoint = NamedPoint.center,
    target: T = copy()
) =
    scale(scaleFactor, pivot.x, pivot.y, target)


fun <T : EShape<*>> T.scale2D(
    scaleFactorX: Number,
    scaleFactorY: Number,
    pivot: EPoint,
    target: T = copy()
) =
    scale2D(
        scaleFactorX = scaleFactorX,
        scaleFactorY = scaleFactorY,
        pivotX = pivot.x,
        pivotY = pivot.y,
        target = target
    )


fun <T : EShape<*>> T.scale(
    scaleFactor: Number,
    pivotX: Number,
    pivotY: Number,
    target: T = copy()
) = scaleRelative(
    scaleFactor = scaleFactor,
    pointX = pointAtAnchorX(pivotX),
    pointY = pointAtAnchorY(pivotY),
    target = target
)

fun <T : EShape<*>> T.scale2D(
    scaleFactorX: Number,
    scaleFactorY: Number,
    pivotX: Number,
    pivotY: Number,
    target: T = copy()
) = scaleRelative2D(
    scaleFactorX = scaleFactorX,
    scaleFactorY = scaleFactorY,
    pointX = pointAtAnchorX(pivotX),
    pointY = pointAtAnchorY(pivotY),
    target = target
)


fun <T : EShape<*>> T.scaleRelative(
    scaleFactor: Number,
    point: EPoint,
    target: T = copy()
) = scaleRelative(
    scaleFactor = scaleFactor,
    pointX = point.x,
    pointY = point.y,
    target = target
)

fun <T : EShape<*>> T.scaleRelative2D(
    scaleFactorX: Number,
    scaleFactorY: Number,
    point: EPoint,
    target: T = copy()
) = scaleRelative2D(
    scaleFactorX = scaleFactorX,
    scaleFactorY = scaleFactorY,
    pointX = point.x,
    pointY = point.y,
    target = target
)
