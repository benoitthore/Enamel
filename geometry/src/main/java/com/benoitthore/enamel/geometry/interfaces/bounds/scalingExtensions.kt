package com.benoitthore.enamel.geometry.interfaces.bounds

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.primitives.Tuple2
import com.benoitthore.enamel.geometry.primitives.point.EPoint


fun HasBounds.scaleRelative(
    scaleFactor: Number,
    pointX: Number,
    pointY: Number,
    target: CanSetBounds = E.RectMutable()
) = scaleRelative2D(
    scaleFactorX = scaleFactor,
    scaleFactorY = scaleFactor,
    pointX = pointX,
    pointY = pointY,
    target = target
)

fun HasBounds.scaleRelative2D(
    scaleFactorX: Number,
    scaleFactorY: Number,
    pointX: Number,
    pointY: Number,
    target: CanSetBounds = E.RectMutable()
): CanSetBounds {
    target.set(this)

    val scaleFactorX = scaleFactorX.f
    val scaleFactorY = scaleFactorY.f

    target.setOriginSize(
        originX = originX + (pointX.f - originX) * (1f - scaleFactorX),
        originY = originY + (pointY.f - originY) * (1f - scaleFactorY),
        width = width * scaleFactorX,
        height = height * scaleFactorY
    )
    return target
}

//// //// //// //// //// //// //// ////
//// //// //// //// //// //// //// ////

fun HasBounds.scaleAnchor(
    scaleFactor: Number,
    anchor: EPoint,
    target: CanSetBounds = E.RectMutable()
) =
    scaleAnchor(scaleFactor, anchor.x, anchor.y, target)


fun HasBounds.scaleAnchor2D(
    scaleFactorX: Number,
    scaleFactorY: Number,
    anchor: EPoint,
    target: CanSetBounds = E.RectMutable()
) =
    scaleAnchor2D(
        scaleFactorX = scaleFactorX,
        scaleFactorY = scaleFactorY,
        anchorX = anchor.x,
        anchorY = anchor.y,
        target = target
    )


fun HasBounds.scaleAnchor(
    scaleFactor: Number,
    anchorX: Number,
    anchorY: Number,
    target: CanSetBounds = E.RectMutable()
) = scaleRelative(
    scaleFactor = scaleFactor,
    pointX = pointAtAnchorX(anchorX),
    pointY = pointAtAnchorY(anchorY),
    target = target
)

fun HasBounds.scaleAnchor2D(
    scaleFactorX: Number,
    scaleFactorY: Number,
    anchorX: Number,
    anchorY: Number,
    target: CanSetBounds = E.RectMutable()
) = scaleRelative2D(
    scaleFactorX = scaleFactorX,
    scaleFactorY = scaleFactorY,
    pointX = pointAtAnchorX(anchorX),
    pointY = pointAtAnchorY(anchorY),
    target = target
)


fun HasBounds.scaleRelative(
    scaleFactor: Number,
    point: EPoint,
    target: CanSetBounds = E.RectMutable()
) = scaleRelative(
    scaleFactor = scaleFactor,
    pointX = point.x,
    pointY = point.y,
    target = target
)

fun HasBounds.scaleRelative2D(
    scaleFactorX: Number,
    scaleFactorY: Number,
    point: EPoint,
    target: CanSetBounds = E.RectMutable()
) = scaleRelative2D(
    scaleFactorX = scaleFactorX,
    scaleFactorY = scaleFactorY,
    pointX = point.x,
    pointY = point.y,
    target = target
)
