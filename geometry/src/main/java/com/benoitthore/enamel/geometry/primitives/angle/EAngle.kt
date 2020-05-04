package com.benoitthore.enamel.geometry.primitives.angle

import com.benoitthore.enamel.core.math.d
import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.builders.E
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan
import com.benoitthore.enamel.geometry.primitives.angle.EAngle.*

private val π = Math.PI.f

interface EAngle {

    enum class AngleType {
        DEGREE, RADIAN, ROTATION
    }

    enum class Direction {
        CW, //ClockWise
        CCW //CounterClockWise
    }

    val value: Float
    val type: AngleType

    // The use of by lazy would create 3 new objects so it's better to calculate on initialisation
    val radians
        get() =
            when (type) {
                AngleType.RADIAN -> value
                AngleType.ROTATION ->
                    (value * 2 * π)
                AngleType.DEGREE -> Math.toRadians(value.d).f
            }
    val degrees
        get() =
            when (type) {
                AngleType.RADIAN -> Math.toDegrees(value.d).f
                AngleType.ROTATION -> 360 * value
                AngleType.DEGREE -> value
            }
    val rotations
        get() =
            when (type) {
                AngleType.RADIAN -> (value / (2 * π))
                AngleType.ROTATION -> value
                AngleType.DEGREE -> value / 360
            }
    val cos
        get() = cos(radians.d).f
    val sin
        get() = sin(radians.d).f
    val tan
        get() = tan(radians.d).f


    operator fun unaryMinus(): EAngleMutable = inverse()

    fun inverse(target: EAngleMutable = E.AngleMutable()): EAngleMutable {
        val opposite = value.degrees(target)
        return opposite.set(-value, type)
    }

    fun offset(other: EAngle, target: EAngleMutable = E.AngleMutable()) = target.apply {
        val increment = when (type) {

            AngleType.DEGREE -> other.degrees
            AngleType.RADIAN -> other.radians
            AngleType.ROTATION -> other.rotations
        }

        value += increment
    }

    operator fun plus(other: EAngleMutable): EAngleMutable =
        E.AngleMutable(
            radians + other.radians,
            AngleType.RADIAN
        )

    operator fun minus(other: EAngleMutable): EAngleMutable =
        E.AngleMutable(
            radians - other.radians,
            AngleType.RADIAN
        )

    operator fun times(n: Number): EAngleMutable =
        E.AngleMutable(
            radians * n.f,
            AngleType.RADIAN
        )

    operator fun div(n: Number): EAngleMutable =
        E.AngleMutable(
            radians / n.f,
            AngleType.RADIAN
        )

    operator fun compareTo(angle: EAngleMutable): Int =
        ((rotations - angle.rotations) * 100).toInt()


}
