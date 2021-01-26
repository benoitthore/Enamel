package com.benoitthore.enamel.geometry.functions

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.primitives.size.ESize
import com.benoitthore.enamel.geometry.primitives.offset.EOffset
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.primitives.Tuple2
import com.benoitthore.enamel.geometry.primitives.point.point

fun <T, I, M> T.selfOffset(
    x: Number = 0,
    y: Number = 0
) where T : EShapeMutable<I, M>, M : EShapeMutable<I, M>, I : EShape<I, M> =
    apply { offset(x, y, this) }


fun <T, I, M> T.selfOffset(p: Tuple2) where T : EShapeMutable<I, M>, M : EShapeMutable<I, M>, I : EShape<I, M> =
    apply { offset(p.v1, p.v2, this) }

fun <T, I, M> T.selfInset(margin: Number) where T : EShapeMutable<I, M>, M : EShapeMutable<I, M>, I : EShape<I, M> =
    apply { inset(margin, margin, this) }

fun <T, I, M> T.selfInset(p: EPoint) where T : EShapeMutable<I, M>, M : EShapeMutable<I, M>, I : EShape<I, M> =
    apply { inset(p.x, p.y, this) }

fun <T, I, M> T.selfInset(
    x: Number = 0,
    y: Number = 0
) where T : EShapeMutable<I, M>, M : EShapeMutable<I, M>, I : EShape<I, M> =
    apply { inset(x, y, this) }

fun <T, I, M> T.selfExpand(margin: Number) where T : EShapeMutable<I, M>, M : EShapeMutable<I, M>, I : EShape<I, M> =
    apply { expand(margin, margin, this) }

fun <T, I, M> T.selfExpand(p: EPoint) where T : EShapeMutable<I, M>, M : EShapeMutable<I, M>, I : EShape<I, M> =
    apply { expand(p.x, p.y, this) }

fun <T, I, M> T.selfExpand(
    x: Number,
    y: Number
) where T : EShapeMutable<I, M>, M : EShapeMutable<I, M>, I : EShape<I, M> =
    apply { inset(-x.f, -y.f, this) }

fun <T, I, M> T.selfPadding(padding: EOffset) where T : EShapeMutable<I, M>, M : EShapeMutable<I, M>, I : EShape<I, M> =
    apply { padding(padding, this) }

fun <T, I, M> T.selfPadding(
    top: Number = 0,
    bottom: Number = 0,
    left: Number = 0,
    right: Number = 0
) where T : EShapeMutable<I, M>, M : EShapeMutable<I, M>, I : EShape<I, M> = apply {
    padding(
        top = top,
        bottom = bottom,
        left = left,
        right = right,
        target = this
    )
}

fun <T, I, M> T.selfExpand(padding: EOffset) where T : EShapeMutable<I, M>, M : EShapeMutable<I, M>, I : EShape<I, M> =
    apply { expand(padding, this) }

fun <T, I, M> T.selfScale(
    factor: Number,
    anchor: EPoint
) where T : EShapeMutable<I, M>, M : EShapeMutable<I, M>, I : EShape<I, M> =
    apply { scale(factor, anchor, this) }

fun <T, I, M> T.selfScale(
    factor: Number,
    anchorX: Number,
    anchorY: Number
) where T : EShapeMutable<I, M>, M : EShapeMutable<I, M>, I : EShape<I, M> =
    apply { scale(factor, anchorX, anchorY, this) }

fun <T, I, M> T.selfScaleRelative(
    factor: Number,
    point: EPoint
) where T : EShapeMutable<I, M>, M : EShapeMutable<I, M>, I : EShape<I, M> =

    scaleRelative(factor, point, this)

fun <T, I, M> T.selfScaleRelative(
    factor: Number,
    pointX: Number,
    pointY: Number
) where T : EShapeMutable<I, M>, M : EShapeMutable<I, M>, I : EShape<I, M> =
    scaleRelative(scaleFactor = factor, pointX = pointX, pointY = pointY, target = this)

fun <T, I, M> T.selfMap(
    from: I,
    to: I
) where T : EShapeMutable<I, M>, M : EShapeMutable<I, M>, I : EShape<I, M> =
    apply { map(from, to, this) }

fun <T, I, M> T.selfMap(
    fromX: Number,
    fromY: Number,
    fromWidth: Number,
    fromHeight: Number,
    toX: Number,
    toY: Number,
    toWidth: Number,
    toHeight: Number
) where T : EShapeMutable<I, M>, M : EShapeMutable<I, M>, I : EShape<I, M> =
    apply {
        map(
            fromX = fromX,
            fromY = fromY,
            fromWidth = fromWidth,
            fromHeight = fromHeight,
            toX = toX,
            toY = toY,
            toWidth = toWidth,
            toHeight = toHeight,
            target = this
        )
    }