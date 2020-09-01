package com.benoitthore.enamel.geometry.functions

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.primitives.size.ESize
import com.benoitthore.enamel.geometry.primitives.offset.EOffset
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.primitives.Tuple2
import com.benoitthore.enamel.geometry.primitives.point.point

fun <T : EShape> T.selfOffset(x: Number = 0, y: Number = 0): T =
    apply { offset(x, y, this) }

fun <T : EShape> T.selfOffset(p: Tuple2): T =
    apply { offset(p.v1, p.v2, this) }

fun <T : EShape> T.selfInset(margin: Number): T =
    apply { inset(margin, margin, this) }

fun <T : EShape> T.selfInset(p: EPoint): T =
    apply { inset(p.x, p.y, this) }

fun <T : EShape> T.selfInset(x: Number = 0, y: Number = 0): T =
    apply { inset(x, y, this) }

fun <T : EShape> T.selfExpand(margin: Number): T =
    apply { expand(margin, margin, this) }

fun <T : EShape> T.selfExpand(p: EPoint): T =
    apply { expand(p.x, p.y, this) }

fun <T : EShape> T.selfExpand(x: Number, y: Number): T =
    apply { inset(-x.f, -y.f, this) }

fun <T : EShape> T.selfPadding(padding: EOffset): T =
    apply { padding(padding, this) }

fun <T : EShape> T.selfPadding(
    top: Number = 0,
    bottom: Number = 0,
    left: Number = 0,
    right: Number = 0
): T = apply {
    padding(
        top = top,
        bottom = bottom,
        left = left,
        right = right,
        target = this
    )
}

fun <T : EShape> T.selfExpand(padding: EOffset): T =
    apply { expand(padding, this) }

fun <T : EShape> T.selfScale(factor: Number, anchor: EPoint): T =
    apply { scale(factor, anchor, this) }

fun <T : EShape> T.selfScale(
    factor: Number,
    anchorX: Number,
    anchorY: Number
) =
    apply { scale(factor, anchorX, anchorY, this) }

fun <T : EShape> T.selfScaleRelative(
    factor: Number,
    point: EPoint
) =

    scaleRelative(factor, point, this)

fun <T : EShape> T.selfScaleRelative(
    factor: Number,
    pointX: Number,
    pointY: Number
) =
    scaleRelative(scaleFactor = factor, pointX = pointX, pointY = pointY, target = this)

fun <T : EShape> T.selfMap(
    from: EShape,
    to: EShape
): T =
    apply { map(from, to, this) }

fun <T : EShape> T.selfMap(
    fromX: Number,
    fromY: Number,
    fromWidth: Number,
    fromHeight: Number,
    toX: Number,
    toY: Number,
    toWidth: Number,
    toHeight: Number
): T =
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