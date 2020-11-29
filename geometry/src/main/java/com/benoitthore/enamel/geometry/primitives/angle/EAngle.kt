package com.benoitthore.enamel.geometry.primitives.angle

import com.benoitthore.enamel.core.math.d
import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.builders.*
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

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

    fun copy(): EAngle = Angle(value, type)

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


    operator fun unaryMinus(): EAngle = inverse()

    fun inverse(target: EAngleMutable = MutableAngle()): EAngle {
        val opposite = value.degrees(target)
        return opposite.set(-value, type)
    }

    fun offset(other: EAngle, target: EAngleMutable = MutableAngle()) = target.apply {
        val increment = when (type) {

            AngleType.DEGREE -> other.degrees
            AngleType.RADIAN -> other.radians
            AngleType.ROTATION -> other.rotations
        }

        value += increment
    }

    operator fun plus(other: EAngle): EAngle =
        Angle(
            radians + other.radians,
            AngleType.RADIAN
        )

    operator fun minus(other: EAngle): EAngle =
        Angle(
            radians - other.radians,
            AngleType.RADIAN
        )

    operator fun times(n: Number): EAngle =
        Angle(
            radians * n.f,
            AngleType.RADIAN
        )

    operator fun div(n: Number): EAngle =
        Angle(
            radians / n.f,
            AngleType.RADIAN
        )

    operator fun compareTo(angle: EAngle): Int =
        ((rotations - angle.rotations) * 100).toInt()
}

interface EAngleMutable : EAngle {
    override var value: Float
    override var type: EAngle.AngleType

    override fun copy(): EAngle  = MutableAngle(this)

    fun set(other: EAngle) = set(
        other.value,
        other.type
    )

    fun set(value: Number, type: EAngle.AngleType): EAngleMutable {
        this.value = value.f
        this.type = type
        return this
    }

    fun selfInverse() = inverse(this)
    fun selfOffset(angle: EAngle) = offset(angle, this)

}
