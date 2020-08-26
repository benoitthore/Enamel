package com.benoitthore.enamel.geometry

import com.benoitthore.enamel.core.animations.Interpolator
import com.benoitthore.enamel.core.animations.linearInterpolator
import com.benoitthore.enamel.core.math.lerp
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.functions.EShape
import com.benoitthore.enamel.geometry.functions.copy
import com.benoitthore.enamel.geometry.functions.setOriginSize
import com.benoitthore.enamel.geometry.primitives.size.ESize

fun List<EPoint>.selfLerp(
    fraction: Number,
    from: List<EPoint>,
    to: List<EPoint>
): List<EPoint> {
    if (size != from.size || size != size) {
        throw Exception("Impossible to lerp lists with different sizes")
    }

    forEachIndexed { i, p ->
        p.selfLerp(fraction, from[i], to[i])
    }

    return this
}

fun EPoint.selfLerp(
    fraction: Number,
    from: EPoint,
    to: EPoint,
    interpolator: Interpolator = linearInterpolator
): EPoint =
    set(
        x = fraction.lerp(from.x, to.x, interpolator),
        y = fraction.lerp(from.y, to.y, interpolator)
    )

fun ESize.selfLerp(
    fraction: Number,
    from: ESize,
    to: ESize,
    interpolator: Interpolator = { it }
): ESize =
    set(
        width = fraction.lerp(from.width, to.width, interpolator),
        height = fraction.lerp(from.height, to.height, interpolator)
    )

fun <T : EShape<*>> T.selfLerp(
    fraction: Number,
    from: EShape<*>,
    to: EShape<*>,
    interpolator: Interpolator = linearInterpolator
) =
    lerp(fraction, from, to, this, interpolator)

fun <T : EShape<*>> T.lerp(
    fraction: Number,
    from: EShape<*>,
    to: EShape<*>,
    target: T = copy(),
    interpolator: Interpolator = linearInterpolator
) = target.apply {
    val x = fraction.lerp(from.originX, to.originX, interpolator)
    val y = fraction.lerp(from.originY, to.originY, interpolator)
    val width = fraction.lerp(from.width, to.width, interpolator)
    val height = fraction.lerp(from.height, to.height, interpolator)

    setOriginSize(
        x = x ,
        y = y ,
        width = width ,
        height = height
    )
}