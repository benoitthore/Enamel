package com.benoitthore.enamel.geometry.functions

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.primitives.offset.EOffset
import com.benoitthore.enamel.geometry.primitives.Tuple2


fun <I, M> EShape<I, M>.offset(
    p: Tuple2, target: EShapeMutable<I, M> = toMutable()
) where M : EShapeMutable<I, M>, I : EShape<I, M> = offset(p.v1, p.v2, target)


fun <I, M> EShape<I, M>.offset(
    x: Number,
    y: Number,
    target: EShapeMutable<I, M> = toMutable()
): EShapeMutable<I, M> where M : EShapeMutable<I, M>, I : EShape<I, M> {
    target.setOriginSize(originX + x.f, originY + y.f, width, height)
    return target
}

fun <I, M> EShape<I, M>.inset(
    margin: Number,
    target: EShapeMutable<I, M> = toMutable()
) where M : EShapeMutable<I, M>, I : EShape<I, M> = inset(margin, margin, target)

fun <I, M> EShape<I, M>.inset(
    p: Tuple2,
    target: EShapeMutable<I, M> = toMutable()
) where M : EShapeMutable<I, M>, I : EShape<I, M> = inset(p.v1, p.v2, target)

fun <I, M> EShape<I, M>.inset(
    x: Number,
    y: Number,
    target: EShapeMutable<I, M> = toMutable()
) where M : EShapeMutable<I, M>, I : EShape<I, M> =
    inset(left = x, top = y, right = x, bottom = y, target = target)

fun <I, M> EShape<I, M>.inset(
    left: Number = 0,
    top: Number = 0,
    right: Number = 0,
    bottom: Number = 0,
    target: EShapeMutable<I, M> = toMutable()
): EShapeMutable<I, M> where M : EShapeMutable<I, M>, I : EShape<I, M> {
    target._setBounds(
        left = this.left + left.toFloat(),
        top = this.top + top.toFloat(),
        bottom = this.bottom - bottom.toFloat(),
        right = this.right - right.toFloat()
    )
    return target
}

fun <I, M> EShape<I, M>.expand(
    margin: Number,
    target: EShapeMutable<I, M> = toMutable()
) where M : EShapeMutable<I, M>, I : EShape<I, M> = expand(margin, margin, target)

fun <I, M> EShape<I, M>.expand(
    p: Tuple2,
    target: EShapeMutable<I, M> = toMutable()
) where M : EShapeMutable<I, M>, I : EShape<I, M> = expand(p.v1, p.v2, target)

fun <I, M> EShape<I, M>.expand(
    x: Number = 0f,
    y: Number = 0f,
    target: EShapeMutable<I, M> = toMutable()
) where M : EShapeMutable<I, M>, I : EShape<I, M> = inset(-x.f, -y.f, target)

fun <I, M> EShape<I, M>.expand(
    left: Number = 0,
    top: Number = 0,
    right: Number = 0,
    bottom: Number = 0,
    target: EShapeMutable<I, M> = toMutable()
) where M : EShapeMutable<I, M>, I : EShape<I, M> = inset(
    left = -left.toFloat(),
    top = -top.toFloat(),
    right = -right.toFloat(),
    bottom = -bottom.toFloat(),
    target = target
)

fun <I, M> EShape<I, M>.expand(
    padding: EOffset,
    target: EShapeMutable<I, M> = toMutable()
) where M : EShapeMutable<I, M>, I : EShape<I, M> = expand(
    left = padding.left,
    top = padding.top,
    right = padding.right,
    bottom = padding.bottom,
    target = target
)

fun <I, M> EShape<I, M>.padding(
    top: Number = 0f,
    bottom: Number = 0f,
    left: Number = 0f,
    right: Number = 0f,
    target: EShapeMutable<I, M> = toMutable()
) where M : EShapeMutable<I, M>, I : EShape<I, M> = inset(
    left = left,
    top = top,
    bottom = bottom,
    right = right,
    target = target
)

fun <I, M> EShape<I, M>.padding(
    padding: EOffset,
    target: EShapeMutable<I, M> = toMutable()
) where M : EShapeMutable<I, M>, I : EShape<I, M> =
    padding(
        left = padding.left,
        top = padding.top,
        bottom = padding.bottom,
        right = padding.right,
        target = target
    )


fun <I, M> EShape<I, M>.map(
    from: I,
    to: I,
    target: EShapeMutable<I, M> = toMutable()
)  where M : EShapeMutable<I, M>, I : EShape<I, M> = map(
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
