package com.benoitthore.enamel.geometry.interfaces.bounds

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.primitives.point.EPoint


fun <T : EShape<T>> T.scaleRelative(
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

fun <T : EShape<T>> T.scaleRelative2D(
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

fun <T : EShape<T>> T.scaleAnchor(
    scaleFactor: Number,
    anchor: EPoint,
    target: T = copy()
) =
    scaleAnchor(scaleFactor, anchor.x, anchor.y, target)


fun <T : EShape<T>> T.scaleAnchor2D(
    scaleFactorX: Number,
    scaleFactorY: Number,
    anchor: EPoint,
    target: T = copy()
) =
    scaleAnchor2D(
        scaleFactorX = scaleFactorX,
        scaleFactorY = scaleFactorY,
        anchorX = anchor.x,
        anchorY = anchor.y,
        target = target
    )


fun <T : EShape<T>> T.scaleAnchor(
    scaleFactor: Number,
    anchorX: Number,
    anchorY: Number,
    target: T = copy()
) = scaleRelative(
    scaleFactor = scaleFactor,
    pointX = pointAtAnchorX(anchorX),
    pointY = pointAtAnchorY(anchorY),
    target = target
)

fun <T : EShape<T>> T.scaleAnchor2D(
    scaleFactorX: Number,
    scaleFactorY: Number,
    anchorX: Number,
    anchorY: Number,
    target: T = copy()
) = scaleRelative2D(
    scaleFactorX = scaleFactorX,
    scaleFactorY = scaleFactorY,
    pointX = pointAtAnchorX(anchorX),
    pointY = pointAtAnchorY(anchorY),
    target = target
)


fun <T : EShape<T>> T.scaleRelative(
    scaleFactor: Number,
    point: EPoint,
    target: T = copy()
) = scaleRelative(
    scaleFactor = scaleFactor,
    pointX = point.x,
    pointY = point.y,
    target = target
)

fun <T : EShape<T>> T.scaleRelative2D(
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
