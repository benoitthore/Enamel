package com.benoitthore.enamel.geometry.builders

import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.core.math.randomSign
import com.benoitthore.enamel.geometry.primitives.angle.EAngle
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.primitives.point.EPointMutableImpl
import com.benoitthore.enamel.geometry.primitives.point.EPointMutable


fun Point(x: Number, y: Number): EPoint =
    EPointMutableImpl(x, y)

fun Point(): EPoint = Point(0, 0)
fun Point(n: Number): EPoint = Point(n, n)

fun Point(other: EPoint) =
    Point(other.x, other.y)

fun Point(angle: EAngle, magnitude: Number) =
    Point(
        angle.cos * magnitude.f,
        angle.sin * magnitude.f
    )


/*
 *  Immutable
 */

/*
 *  Mutable
 */
fun MutablePoint(x: Number, y: Number): EPointMutable =
    EPointMutableImpl(x, y)

fun MutablePoint(): EPointMutable = MutablePoint(0, 0)
fun MutablePoint(n: Number): EPoint = Point(n, n)

fun MutablePoint(other: EPoint) =
    MutablePoint(other.x, other.y)

fun MutablePoint(angle: EAngle, magnitude: Number) =
    MutablePoint(
        angle.cos * magnitude.f,
        angle.sin * magnitude.f
    )

/*
 *  Misc
 */
val Point get() = _Point

object _Point {
    fun Zero(target: EPointMutable = MutablePoint()) = target.set(0f, 0f)
    fun Half(target: EPointMutable = MutablePoint()) = target.set(0.5f, 0.5f)
    fun Unit(target: EPointMutable = MutablePoint()) = target.set(1f, 1f)


    fun Random(magnitude: Number = 1f, target: EPointMutable = MutablePoint()) =
        target.set(
            x = randomSign() * com.benoitthore.enamel.core.math.random() * magnitude.f,
            y = randomSign() * com.benoitthore.enamel.core.math.random() * magnitude.f
        ).selfLimitMagnitude(magnitude)

    fun Random(
        minX: Number = 0f,
        minY: Number = 0f,
        maxX: Number = 1f,
        maxY: Number = 1f,
        target: EPointMutable = MutablePoint()
    ) = target.set(
        x = com.benoitthore.enamel.core.math.random(minX, maxX),
        y = com.benoitthore.enamel.core.math.random(minY, maxY)
    )
}
