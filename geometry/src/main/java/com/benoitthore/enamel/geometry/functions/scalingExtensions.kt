package com.benoitthore.enamel.geometry.functions

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.alignement.NamedPoint
import com.benoitthore.enamel.geometry.figures.circle.ECircle
import com.benoitthore.enamel.geometry.primitives.point.EPoint


fun <T,I, M> T.scaleRelative(
    scaleFactor: Number,
    pointX: Number,
    pointY: Number,
    target: EShapeMutable<I, M> = toMutable()
) where T : EShapeMutable<I, M>, M : EShapeMutable<I, M>, I : EShape<I, M> = scaleRelative2D(
    scaleFactorX = scaleFactor,
    scaleFactorY = scaleFactor,
    pointX = pointX,
    pointY = pointY,
    target = target
)

fun <T,I, M> T.scaleRelative2D(
    scaleFactorX: Number,
    scaleFactorY: Number,
    pointX: Number,
    pointY: Number,
    target: EShapeMutable<I, M> = toMutable()
): EShapeMutable<I, M> where T : EShapeMutable<I, M>, M : EShapeMutable<I, M>, I : EShape<I, M> {
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

fun <T,I, M> T.scale(
    scaleFactor: Number,
    pivot: EPoint = NamedPoint.center,
    target: EShapeMutable<I, M> = toMutable()
) where T : EShapeMutable<I, M>, M : EShapeMutable<I, M>, I : EShape<I, M> =
    scale(scaleFactor, pivot.x, pivot.y, target)


fun <T,I, M> T.scale2D(
    scaleFactorX: Number,
    scaleFactorY: Number,
    pivot: EPoint,
    target: EShapeMutable<I, M> = toMutable()
)where T : EShapeMutable<I, M>, M : EShapeMutable<I, M>, I : EShape<I, M>  =
    scale2D(
        scaleFactorX = scaleFactorX,
        scaleFactorY = scaleFactorY,
        pivotX = pivot.x,
        pivotY = pivot.y,
        target = target
    )


fun <T,I, M> T.scale(
    scaleFactor: Number,
    pivotX: Number,
    pivotY: Number,
    target: EShapeMutable<I, M> = toMutable()
) where T : EShapeMutable<I, M>, M : EShapeMutable<I, M>, I : EShape<I, M> = scaleRelative(
    scaleFactor = scaleFactor,
    pointX = pointAtAnchorX(pivotX),
    pointY = pointAtAnchorY(pivotY),
    target = target
)

fun <T,I, M> T.scale2D(
    scaleFactorX: Number,
    scaleFactorY: Number,
    pivotX: Number,
    pivotY: Number,
    target: EShapeMutable<I, M> = toMutable()
) where T : EShapeMutable<I, M>, M : EShapeMutable<I, M>, I : EShape<I, M> = scaleRelative2D(
    scaleFactorX = scaleFactorX,
    scaleFactorY = scaleFactorY,
    pointX = pointAtAnchorX(pivotX),
    pointY = pointAtAnchorY(pivotY),
    target = target
)

fun <T,I, M> T.scaleRelative(
    scaleFactor: Number,
    point: EPoint,
    target: EShapeMutable<I, M> = toMutable()
) where T : EShapeMutable<I, M>, M : EShapeMutable<I, M>, I : EShape<I, M> = scaleRelative(
    scaleFactor = scaleFactor,
    pointX = point.x,
    pointY = point.y,
    target = target
)

fun <T,I, M> T.scaleRelative2D(
    scaleFactorX: Number,
    scaleFactorY: Number,
    point: EPoint,
    target: EShapeMutable<I, M> = toMutable()
) where T : EShapeMutable<I, M>, M : EShapeMutable<I, M>, I : EShape<I, M> = scaleRelative2D(
    scaleFactorX = scaleFactorX,
    scaleFactorY = scaleFactorY,
    pointX = point.x,
    pointY = point.y,
    target = target
)
