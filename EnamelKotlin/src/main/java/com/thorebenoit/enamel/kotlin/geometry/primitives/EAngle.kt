package com.thorebenoit.enamel.kotlin.geometry.primitives

import com.thorebenoit.enamel.kotlin.core.*

val π = Math.PI.f

enum class AngleType {
    DEGREE, RADIAN, ROTATION
}


open class EAngleImmutable(protected open val value: Float, protected open val type: AngleType) {
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


    operator fun unaryMinus(): EAngle =
        EAngle(-value, type)

    operator fun plus(other: EAngle): EAngle =
        EAngle(
            radians + other.radians,
            AngleType.RADIAN
        )

    operator fun minus(other: EAngle): EAngle =
        EAngle(
            radians - other.radians,
            AngleType.RADIAN
        )

    operator fun times(n: Number): EAngle =
        EAngle(
            radians * n.f,
            AngleType.RADIAN
        )

    operator fun div(n: Number): EAngle =
        EAngle(
            radians / n.f,
            AngleType.RADIAN
        )
//    operator fun Number.times(angle: EAngle): EAngle = angle * this
}

open class EAngle constructor(override var value: Float, override var type: AngleType) : EAngleImmutable(value, type) {
    companion object {
        val zero = 0.degrees()
        val unit = 1.rotation()
    }

    fun offset(other: EAngleImmutable) {
        val increment = when (type) {

            AngleType.DEGREE -> other.degrees
            AngleType.RADIAN -> other.radians
            AngleType.ROTATION -> other.rotation
        }

        value += increment
    }

    override fun toString(): String {
        return "${degrees.toInt()}°"
    }
}

fun Number.degrees(): EAngle =
    EAngle(
        this.f,
        AngleType.DEGREE
    )

fun Number.radians(): EAngle =
    EAngle(
        this.f,
        AngleType.RADIAN
    )

fun Number.rotation(): EAngle =
    EAngle(
        this.f,
        AngleType.ROTATION
    )