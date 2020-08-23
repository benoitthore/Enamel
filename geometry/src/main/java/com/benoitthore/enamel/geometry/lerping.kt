package com.benoitthore.enamel.geometry

import com.benoitthore.enamel.core.math.lerp
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.functions.EShape
import com.benoitthore.enamel.geometry.functions.setOriginSize
import com.benoitthore.enamel.geometry.primitives.size.ESize

fun List<EPoint>.lerp(
    fraction: Number,
    from: List<EPoint>,
    to: List<EPoint>
): List<EPoint> {
    if (size != from.size || size != size) {
        throw Exception("Impossible to lerp lists with different sizes")
    }

    forEachIndexed { i, p ->
        p.lerp(fraction, from[i], to[i])
    }

    return this
}

fun EPoint.lerp(fraction: Number, from: EPoint, to: EPoint): EPoint =
    set(
        x = fraction.lerp(from.x, to.x),
        y = fraction.lerp(from.y, to.y)
    )

fun ESize.lerp(fraction: Number, from: ESize, to: ESize): ESize =
    set(
        width = fraction.lerp(from.width, to.width),
        height = fraction.lerp(from.height, to.height)
    )

fun <T : EShape<T>> T.lerp(fraction: Number, from: EShape<*>, to: EShape<*>) = apply {
    setOriginSize(
        x = fraction.lerp(from.originX, to.originX),
        y = fraction.lerp(from.originY, to.originY),
        width = fraction.lerp(from.width, to.width),
        height = fraction.lerp(from.height, to.height)
    )
}