package com.benoitthore.enamel.geometry

import com.benoitthore.enamel.core.math.lerp
import com.benoitthore.enamel.geometry.primitives.point.EPointMutable
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.interfaces.bounds.CanSetBounds
import com.benoitthore.enamel.geometry.interfaces.bounds.HasBounds
import com.benoitthore.enamel.geometry.interfaces.bounds.setOriginSize
import com.benoitthore.enamel.geometry.primitives.size.ESizeMutable
import com.benoitthore.enamel.geometry.primitives.size.ESize

fun List<EPointMutable>.lerp(
    fraction: Number,
    from: List<EPoint>,
    to: List<EPoint>
): List<EPointMutable> {
    if (size != from.size || size != size) {
        throw Exception("Impossible to lerp lists with different sizes")
    }

    forEachIndexed { i, p ->
        p.lerp(fraction, from[i], to[i])
    }

    return this
}

fun EPointMutable.lerp(fraction: Number, from: EPoint, to: EPoint): EPointMutable =
    set(
        x = fraction.lerp(from.x, to.x),
        y = fraction.lerp(from.y, to.y)
    )

fun ESizeMutable.lerp(fraction: Number, from: ESize, to: ESize): ESizeMutable =
    set(
        width = fraction.lerp(from.width, to.width),
        height = fraction.lerp(from.height, to.height)
    )

fun <T, I, M> T.lerp(fraction: Number, from: HasBounds<*, *>, to: HasBounds<*, *>)
        where  T : CanSetBounds<I, M>, I : HasBounds<I, M>, M : CanSetBounds<I, M> = apply {
    setOriginSize(
        originX = fraction.lerp(from.originX, to.originX),
        originY = fraction.lerp(from.originY, to.originY),
        width = fraction.lerp(from.width, to.width),
        height = fraction.lerp(from.height, to.height)
    )
}