package com.benoitthore.enamel.geometry.primitives

import com.benoitthore.enamel.core.math.d
import com.benoitthore.enamel.core.math.f
import com.benoitthore.enamel.geometry.Resetable

val π = Math.PI.f

enum class AngleType {
    DEGREE, RADIAN, ROTATION
}


// TODO change internal to protected and fix the compile errors
open class EAngle(
    internal open val value: Float = 0f,
    internal open val type: AngleType = AngleType.DEGREE
) {
    constructor(angle: EAngle) : this(angle.value, angle.type)

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
    val rotation
        get() =
            when (type) {
                AngleType.RADIAN -> (value / (2 * π))
                AngleType.ROTATION -> value
                AngleType.DEGREE -> value / 360
            }
    val cos
        get() = Math.cos(radians.d).f
    val sin
        get() = Math.sin(radians.d).f
    val tan
        get() = Math.tan(radians.d).f


    operator fun unaryMinus(): EAngleMutable = inverse()

    fun inverse(buffer: EAngleMutable = EAngleMutable()): EAngleMutable {
        val opposite = value.degrees(buffer)
        return opposite.set(-value, type)
    }

    fun offset(other: EAngle, buffer: EAngleMutable = EAngleMutable()) = buffer.apply {
        val increment = when (type) {

            AngleType.DEGREE -> other.degrees
            AngleType.RADIAN -> other.radians
            AngleType.ROTATION -> other.rotation
        }

        value += increment
    }

    operator fun plus(other: EAngleMutable): EAngleMutable =
        EAngleMutable(
            radians + other.radians,
            AngleType.RADIAN
        )

    operator fun minus(other: EAngleMutable): EAngleMutable =
        EAngleMutable(
            radians - other.radians,
            AngleType.RADIAN
        )

    operator fun times(n: Number): EAngleMutable =
        EAngleMutable(
            radians * n.f,
            AngleType.RADIAN
        )

    operator fun div(n: Number): EAngleMutable =
        EAngleMutable(
            radians / n.f,
            AngleType.RADIAN
        )

    operator fun compareTo(angle: EAngleMutable): Int = ((rotation - angle.rotation) * 100).toInt()

//    operator fun Number.times(angle: EAngleMutable): EAngleMutable = angle * this
}

class EAngleMutable constructor(override var value: Float = 0f, override var type: AngleType = AngleType.DEGREE) :
    EAngle(value, type), Resetable {
    companion object {
        val zero get() = 0.degrees()
        val unit get() = 1.rotations()
    }

    constructor(angle: EAngle) : this(angle.value, angle.type)


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

    override fun toString(): String {
        return "${degrees.toInt() % 360}°"
    }

}

fun Number.degrees(buffer: EAngleMutable = EAngleMutable()): EAngleMutable =
    buffer.set(
        this.f,
        AngleType.DEGREE
    )

fun Number.radians(buffer: EAngleMutable = EAngleMutable()): EAngleMutable =
    buffer.set(
        this.f,
        AngleType.RADIAN
    )

fun Number.rotations(buffer: EAngleMutable = EAngleMutable()): EAngleMutable =
    buffer.set(
        this.f,
        AngleType.ROTATION
    )