package com.benoitthore.enamel.geometry

import com.benoitthore.enamel.core.animations.Interpolator
import com.benoitthore.enamel.core.animations.linearInterpolator
import com.benoitthore.enamel.core.math.lerp
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.functions.EShape
import com.benoitthore.enamel.geometry.functions.EShapeMutable
import com.benoitthore.enamel.geometry.functions.setOriginSize
import com.benoitthore.enamel.geometry.primitives.point.EPointMutable
import com.benoitthore.enamel.geometry.primitives.size.ESize
import com.benoitthore.enamel.geometry.primitives.size.ESizeMutable

fun List<EPointMutable>.selfLerp(
    fraction: Number,
    from: List<EPoint>,
    to: List<EPoint>
): List<EPointMutable> {
    if (size != from.size || size != size) {
        throw Exception("Impossible to lerp lists with different sizes")
    }

    forEachIndexed { i, p ->
        p.selfLerp(fraction, from[i], to[i])
    }

    return this
}

fun EPointMutable.selfLerp(
    fraction: Number,
    from: EPoint,
    to: EPoint,
    interpolator: Interpolator = linearInterpolator
): EPointMutable =
    set(
        x = fraction.lerp(from.x, to.x, interpolator),
        y = fraction.lerp(from.y, to.y, interpolator)
    )

fun ESizeMutable.selfLerp(
    fraction: Number,
    from: ESize,
    to: ESize,
    interpolator: Interpolator = { it }
): ESizeMutable =
    set(
        width = fraction.lerp(from.width, to.width, interpolator),
        height = fraction.lerp(from.height, to.height, interpolator)
    )


fun <T, I, M> T.selfLerp(
    fraction: Number,
    from: I,
    to: I,
    interpolator: Interpolator = linearInterpolator
) where T : EShapeMutable<I, M>, M : EShapeMutable<I, M>, I : EShape<I, M> =
    lerp(fraction, from, to, this, interpolator)

fun <T, I, M> T.lerp(
    fraction: Number,
    from: I,
    to: I,
    target: EShapeMutable<I, M> = toMutable(),
    interpolator: Interpolator = linearInterpolator
) where T : EShapeMutable<I, M>, M : EShapeMutable<I, M>, I : EShape<I, M> = target.apply {
    val x = fraction.lerp(from.originX, to.originX, interpolator)
    val y = fraction.lerp(from.originY, to.originY, interpolator)
    val width = fraction.lerp(from.width, to.width, interpolator)
    val height = fraction.lerp(from.height, to.height, interpolator)

    setOriginSize(
        x = x,
        y = y,
        width = width,
        height = height
    )
}


fun <T, I, M> T.selfLerp(
    fraction: Number,
    from: ERect,
    to: ERect,
    interpolator: Interpolator = linearInterpolator
) where T : EShapeMutable<I, M>, M : EShapeMutable<I, M>, I : EShape<I, M> =
    lerp(fraction, from, to, this, interpolator)

fun <T, I, M> T.lerp(
    fraction: Number,
    from: ERect,
    to: ERect,
    target: EShapeMutable<I, M> = toMutable(),
    interpolator: Interpolator = linearInterpolator
) where T : EShapeMutable<I, M>, M : EShapeMutable<I, M>, I : EShape<I, M> = target.apply {
    val x = fraction.lerp(from.originX, to.originX, interpolator)
    val y = fraction.lerp(from.originY, to.originY, interpolator)
    val width = fraction.lerp(from.width, to.width, interpolator)
    val height = fraction.lerp(from.height, to.height, interpolator)

    setOriginSize(
        x = x,
        y = y,
        width = width,
        height = height
    )
}