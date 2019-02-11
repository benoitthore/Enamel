package com.thorebenoit.enamel.kotlin.animations

import com.thorebenoit.enamel.kotlin.geometry.primitives.EPoint
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointType
import java.lang.Exception
import com.thorebenoit.enamel.kotlin.core.math.lerp as numberLerp

fun List<EPoint>.lerp(fraction: Number, from: List<EPointType>, to: List<EPointType>): List<EPoint> {
    if (size != from.size || size != size) {
        throw Exception("Impossible to lerp lists with different sizes")
    }

    forEachIndexed { i, p ->
        p.lerp(fraction, from[i], to[i])
    }

    return this
}

fun EPoint.lerp(fraction: Number, from: EPointType, to: EPointType): EPoint =
    set(
        x = numberLerp(fraction, from.x, to.x),
        y = numberLerp(fraction, from.y, to.y)
    )