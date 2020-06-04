package com.benoitthore.enamel.geometry.interfaces.bounds

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.primitives.Tuple2
import com.benoitthore.enamel.geometry.primitives.point.EPoint


fun <I, M> HasBounds<I, M>.scaleRelative(
    scaleFactor: Number,
    pointX: Number,
    pointY: Number,
    target: CanSetBounds<I, M> = toMutable()
)
        where  I : HasBounds<I, M>, M : CanSetBounds<I, M> = scaleRelative2D(
    scaleFactorX = scaleFactor,
    scaleFactorY = scaleFactor,
    pointX = pointX,
    pointY = pointY,
    target = target
)

fun <I, M> HasBounds<I, M>.scaleRelative2D(
    scaleFactorX: Number,
    scaleFactorY: Number,
    pointX: Number,
    pointY: Number,
    target: CanSetBounds<I, M> = toMutable()
): CanSetBounds<I, M>
        where I : HasBounds<I, M>, M : CanSetBounds<I, M> {
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

fun <I, M> HasBounds<I, M>.scaleAnchor(
    scaleFactor: Number,
    anchor: EPoint,
    target: CanSetBounds<I, M> = toMutable()
)
        where I : HasBounds<I, M>, M : CanSetBounds<I, M> =
    scaleAnchor(scaleFactor, anchor.x, anchor.y, target)


fun <I, M> HasBounds<I, M>.scaleAnchor2D(
    scaleFactorX: Number,
    scaleFactorY: Number,
    anchor: EPoint,
    target: CanSetBounds<I, M> = toMutable()
) where I : HasBounds<I, M>, M : CanSetBounds<I, M> =
    scaleAnchor2D(
        scaleFactorX = scaleFactorX,
        scaleFactorY = scaleFactorY,
        anchorX = anchor.x,
        anchorY = anchor.y,
        target = target
    )


fun <I, M> HasBounds<I, M>.scaleAnchor(
    scaleFactor: Number,
    anchorX: Number,
    anchorY: Number,
    target: CanSetBounds<I, M> = toMutable()
) where I : HasBounds<I, M>, M : CanSetBounds<I, M> = scaleRelative(
    scaleFactor = scaleFactor,
    pointX = pointAtAnchorX(anchorX),
    pointY = pointAtAnchorY(anchorY),
    target = target
)

fun <I, M> HasBounds<I, M>.scaleAnchor2D(
    scaleFactorX: Number,
    scaleFactorY: Number,
    anchorX: Number,
    anchorY: Number,
    target: CanSetBounds<I, M> = toMutable()
) where I : HasBounds<I, M>, M : CanSetBounds<I, M> = scaleRelative2D(
    scaleFactorX = scaleFactorX,
    scaleFactorY = scaleFactorY,
    pointX = pointAtAnchorX(anchorX),
    pointY = pointAtAnchorY(anchorY),
    target = target
)


fun <I, M> HasBounds<I, M>.scaleRelative(
    scaleFactor: Number,
    point: EPoint,
    target: CanSetBounds<I, M> = toMutable()
) where I : HasBounds<I, M>, M : CanSetBounds<I, M> = scaleRelative(
    scaleFactor = scaleFactor,
    pointX = point.x,
    pointY = point.y,
    target = target
)

fun <I, M> HasBounds<I, M>.scaleRelative2D(
    scaleFactorX: Number,
    scaleFactorY: Number,
    point: EPoint,
    target: CanSetBounds<I, M> = toMutable()
) where I : HasBounds<I, M>, M : CanSetBounds<I, M> = scaleRelative2D(
    scaleFactorX = scaleFactorX,
    scaleFactorY = scaleFactorY,
    pointX = point.x,
    pointY = point.y,
    target = target
)
