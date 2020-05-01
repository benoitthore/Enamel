package com.benoitthore.enamel.geometry.primitives

import com.benoitthore.enamel.core.math.*
import com.benoitthore.enamel.geometry.Resetable
import com.benoitthore.enamel.geometry.builders.E
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

val π = Math.PI.f

enum class AngleType {
    DEGREE, RADIAN, ROTATION
}


// TODO change internal to protected and fix the compile errors
interface EAngle {
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



//    operator fun Number.times(angle: EAngleMutable): EAngleMutable = angle * this
}

interface EAngleMutable : EAngle, Resetable {

    class Impl internal constructor(value: Number, override var type: AngleType) : EAngleMutable {
        override var value: Float = value.toFloat()


        override fun equals(other: Any?): Boolean {
            if (this === other) return true

            if (other !is EAngleMutable) return false

            if (type != other.type) return false
            if (value != other.value) return false

            return true
        }

        override fun hashCode(): Int {
            var result = type.hashCode()
            result = 31 * result + value.hashCode()
            return result
        }

        override fun toString(): String {
            return "${degrees.i}°"
        }

    }

    override var value: Float
    override var type: AngleType

    fun set(other: EAngle) = set(
        other.value,
        other.type
    )

    fun set(value: Number, type: AngleType): EAngleMutable {
        this.value = value.f
        this.type = type
        return this
    }

    override fun reset() {
        set(0, AngleType.DEGREE)
    }

    fun selfInverse() = inverse(this)
    fun selfOffset(angle: EAngle) = offset(angle, this)


}

fun Number.degrees(target: EAngleMutable = E.AngleMutable()): EAngleMutable =
    target.set(
        this.f,
        AngleType.DEGREE
    )

fun Number.radians(target: EAngleMutable = E.AngleMutable()): EAngleMutable =
    target.set(
        this.f,
        AngleType.RADIAN
    )

fun Number.rotations(target: EAngleMutable = E.AngleMutable()): EAngleMutable =
    target.set(
        this.f,
        AngleType.ROTATION
    )