package com.benoitthore.enamel.geometry.functions

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.primitives.offset.EOffset
import com.benoitthore.enamel.geometry.primitives.Tuple2


fun <T : EShape> T.offset(
    p: Tuple2, target: T = copy()
): T {
    return offset(p.v1, p.v2, target)
}

fun <T : EShape> T.offset(
    x: Number,
    y: Number,
    target: T = copy()
): T {
    target.setOriginSize(originX + x.f, originY + y.f, width, height)
    return target
}

fun <T : EShape> T.inset(margin: Number, target: T = copy()) = inset(margin, margin, target)

fun <T : EShape> T.inset(p: Tuple2, target: T = copy()) = inset(p.v1, p.v2, target)

fun <T : EShape> T.inset(
    x: Number,
    y: Number,
    target: T = copy()
) =
    inset(left = x, top = y, right = x, bottom = y, target = target)

fun <T : EShape> T.inset(
    left: Number = 0,
    top: Number = 0,
    right: Number = 0,
    bottom: Number = 0,
    target: T = copy()
): T {
    target._setBounds(
        left = this.left + left.toFloat(),
        top = this.top + top.toFloat(),
        bottom = this.bottom - bottom.toFloat(),
        right = this.right - right.toFloat()
    )
    return target
}

fun <T : EShape> T.expand(margin: Number, target: T = copy()) = expand(margin, margin, target)

fun <T : EShape> T.expand(p: Tuple2, target: T = copy()) = expand(p.v1, p.v2, target)

fun <T : EShape> T.expand(
    x: Number = 0f,
    y: Number = 0f,
    target: T = copy()
) = inset(-x.f, -y.f, target)

fun <T : EShape> T.expand(
    left: Number = 0,
    top: Number = 0,
    right: Number = 0,
    bottom: Number = 0,
    target: T = copy()
) = inset(
    left = -left.toFloat(),
    top = -top.toFloat(),
    right = -right.toFloat(),
    bottom = -bottom.toFloat(),
    target = target
)

fun <T : EShape> T.expand(padding: EOffset, target: T = copy()) = expand(
    left = padding.left,
    top = padding.top,
    right = padding.right,
    bottom = padding.bottom,
    target = target
)

fun <T : EShape> T.padding(
    top: Number = 0f,
    bottom: Number = 0f,
    left: Number = 0f,
    right: Number = 0f,
    target: T = copy()
) = inset(
    left = left,
    top = top,
    bottom = bottom,
    right = right,
    target = target
)

fun <T : EShape> T.padding(
    padding: EOffset,
    target: T = copy()
) =
    padding(
        left = padding.left,
        top = padding.top,
        bottom = padding.bottom,
        right = padding.right,
        target = target
    )


fun <T : EShape> T.map(
    from: EShape,
    to: EShape,
    target: T = copy()
) = map(
    fromX = from.originX,
    fromY = from.originY,
    fromWidth = from.width,
    fromHeight = from.height,
    toX = to.originX,
    toY = to.originY,
    toWidth = to.width,
    toHeight = to.height,
    target = target
)
