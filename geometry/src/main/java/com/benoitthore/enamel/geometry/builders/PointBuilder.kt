package com.benoitthore.enamel.geometry.builders

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.core.math.randomSign
import com.benoitthore.enamel.geometry.primitives.EAngle
import com.benoitthore.enamel.geometry.primitives.EPoint
import com.benoitthore.enamel.geometry.primitives.EPointMutable

interface PointBuilder : BaseBuilder {

    fun Point(x: Number = 0, y: Number = 0): EPoint =
        PointMutable(x, y)

    //
    fun Point(other: EPoint) =
        Point(other.x, other.y)

    fun Point(angle: EAngle, magnitude: Number) =
        Point(
            angle.cos * magnitude.f,
            angle.sin * magnitude.f
        )

    fun PointMutable(other: EPoint) =
        PointMutable(other.x, other.y)

    fun PointMutable(angle: EAngle, magnitude: Number) =
        PointMutable(
            angle.cos * magnitude.f,
            angle.sin * magnitude.f
        )

    val Point get() = _Point

    object _Point {
        val inv = E.Point(-1f, -1f)
        val zero = E.Point(0f, 0f)
        val half = E.Point(0.5f, 0.5f)
        val unit = E.Point(1f, 1f)

        fun random(magnitude: Number = 1f, target: EPointMutable = E.PointMutable()) =
            target.set(
                x = randomSign() * com.benoitthore.enamel.core.math.random() * magnitude.f,
                y = randomSign() * com.benoitthore.enamel.core.math.random() * magnitude.f
            ).selfLimitMagnitude(magnitude)

        fun random(
            minX: Number = 0f,
            minY: Number = 0f,
            maxX: Number = 1f,
            maxY: Number = 1f,
            target: EPointMutable = E.PointMutable()
        ) = target.set(
            x = com.benoitthore.enamel.core.math.random(minX, maxX),
            y = com.benoitthore.enamel.core.math.random(minY, maxY)
        )
    }

    val PointMutable get() = _PointMutable

    object _PointMutable {
        fun zero(target: EPointMutable = E.PointMutable()) = target.set(0f, 0f)
        fun half(target: EPointMutable = E.PointMutable()) = target.set(0.5f, 0.5f)
        fun unit(target: EPointMutable = E.PointMutable()) = target.set(1f, 1f)
    }

}