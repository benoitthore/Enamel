package com.benoitthore.enamel.geometry.builders

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.core.math.randomSign
import com.benoitthore.enamel.geometry.primitives.EAngle
import com.benoitthore.enamel.geometry.primitives.EPoint
import com.benoitthore.enamel.geometry.primitives.EPointMutable

interface PointBuilder : BaseBuilder {

    fun point(x: Number = 0, y: Number = 0): EPoint =
        mpoint(x, y)

    //
    fun point(other: EPoint) =
        point(other.x, other.y)

    fun point(angle: EAngle, magnitude: Number) =
        point(
            angle.cos * magnitude.f,
            angle.sin * magnitude.f
        )

    fun mpoint(other: EPoint) =
        mpoint(other.x, other.y)

    fun mpoint(angle: EAngle, magnitude: Number) =
        mpoint(
            angle.cos * magnitude.f,
            angle.sin * magnitude.f
        )

    val Point get() = _Point

    object _Point {
        val inv = E.point(-1f, -1f)
        val zero = E.point(0f, 0f)
        val half = E.point(0.5f, 0.5f)
        val unit = E.point(1f, 1f)

        fun random(magnitude: Number = 1f, target: EPointMutable = E.mpoint()) =
            target.set(
                x = randomSign() * com.benoitthore.enamel.core.math.random() * magnitude.f,
                y = randomSign() * com.benoitthore.enamel.core.math.random() * magnitude.f
            ).selfLimitMagnitude(magnitude)

        fun random(
            minX: Number = 0f,
            minY: Number = 0f,
            maxX: Number = 1f,
            maxY: Number = 1f,
            target: EPointMutable = E.mpoint()
        ) = target.set(
            x = com.benoitthore.enamel.core.math.random(minX, maxX),
            y = com.benoitthore.enamel.core.math.random(minY, maxY)
        )
    }

    val PointMutable get() = _PointMutable

    object _PointMutable {
        fun zero(target: EPointMutable = E.mpoint()) = target.set(0f, 0f)
        fun half(target: EPointMutable = E.mpoint()) = target.set(0.5f, 0.5f)
        fun unit(target: EPointMutable = E.mpoint()) = target.set(1f, 1f)
    }

}