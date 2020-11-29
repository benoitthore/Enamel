package com.benoitthore.enamel.geometry.builders

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.core.math.randomSign
import com.benoitthore.enamel.geometry.primitives.angle.EAngle
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.primitives.point.EPointImpl


fun Point(x: Number, y: Number): EPoint =
    EPointImpl(x, y)


fun Point(n: Number = 1f): EPoint = Point(n, n)

fun Point(other: EPoint) =
    Point(other.x, other.y)

fun Point(angle: EAngle, magnitude: Number) =
    Point(
        angle.cos * magnitude.f,
        angle.sin * magnitude.f
    )

val Point get() = _Point

object _Point {
    fun Zero(target: EPoint = Point()) = target.set(0f, 0f)
    fun Half(target: EPoint = Point()) = target.set(0.5f, 0.5f)
    fun Unit(target: EPoint = Point()) = target.set(1f, 1f)


    fun Random(magnitude: Number = 1f, target: EPoint = Point()) =
        target.set(
            x = randomSign() * com.benoitthore.enamel.core.math.random() * magnitude.f,
            y = randomSign() * com.benoitthore.enamel.core.math.random() * magnitude.f
        ).selfLimitMagnitude(magnitude)

    fun Random(
        minX: Number = 0f,
        minY: Number = 0f,
        maxX: Number = 1f,
        maxY: Number = 1f,
        target: EPoint = Point()
    ) = target.set(
        x = com.benoitthore.enamel.core.math.random(minX, maxX),
        y = com.benoitthore.enamel.core.math.random(minY, maxY)
    )
}
