package com.thorebenoit.enamel.kotlin.animations

import com.thorebenoit.enamel.kotlin.geometry.primitives.EPoint
import com.thorebenoit.enamel.kotlin.geometry.primitives.EPointType
import java.lang.Exception
import com.thorebenoit.enamel.kotlin.core.math.lerp
import com.thorebenoit.enamel.kotlin.geometry.figures.ERect
import com.thorebenoit.enamel.kotlin.geometry.figures.ERectType
import com.thorebenoit.enamel.kotlin.geometry.figures.ESize
import com.thorebenoit.enamel.kotlin.geometry.figures.ESizeType

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
        x = fraction.lerp(from.x, to.x),
        y = fraction.lerp(from.y, to.y)
    )

fun ESize.lerp(fraction: Number, from: ESizeType, to: ESizeType): ESize =
    set(
        width = fraction.lerp(from.width, to.width),
        height = fraction.lerp(from.height, to.height)
    )

fun ERect.lerp(fraction: Number, from: ERectType, to: ERectType): ERect = apply {
    size.lerp(fraction, from.size, to.size)
    origin.lerp(fraction, from.origin, to.origin)
}