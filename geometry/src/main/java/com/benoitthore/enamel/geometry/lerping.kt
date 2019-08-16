package com.benoitthore.enamel.geometry

import com.benoitthore.enamel.core.math.lerp
import com.benoitthore.enamel.geometry.primitives.EPointMutable
import com.benoitthore.enamel.geometry.primitives.EPoint
import java.lang.Exception
import com.benoitthore.enamel.geometry.figures.ERectMutable
import com.benoitthore.enamel.geometry.figures.ERect
import com.benoitthore.enamel.geometry.figures.ESizeMutable
import com.benoitthore.enamel.geometry.figures.ESize

fun List<EPointMutable>.lerp(fraction: Number, from: List<EPoint>, to: List<EPoint>): List<EPointMutable> {
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

fun ERectMutable.lerp(fraction: Number, from: ERect, to: ERect): ERectMutable = apply {
    size.lerp(fraction, from.size, to.size)
    origin.lerp(fraction, from.origin, to.origin)
}