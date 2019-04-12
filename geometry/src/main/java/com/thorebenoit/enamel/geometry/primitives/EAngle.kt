package com.thorebenoit.enamel.geometry.primitives

import com.thorebenoit.enamel.core.math.d
import com.thorebenoit.enamel.core.math.f
import com.thorebenoit.enamel.geometry.Resetable

val π = Math.PI.f

enum class AngleType {
    DEGREE, RADIAN, ROTATION
}


open class EAngleType(
    protected open val value: Float = 0f,
    protected open val type: AngleType = AngleType.DEGREE
) {
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


    operator fun unaryMinus(): EAngle {
        val opposite = value.degrees()
        return opposite.set((value % 360) - 180, type)
    }

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

open class EAngle constructor(override var value: Float = 0f, override var type: AngleType = AngleType.DEGREE) :
    EAngleType(value, type), Resetable {
    companion object {
        val zero get() = 0.degrees()
        val unit get() = 1.rotation()
    }

    fun set(other: EAngle) = set(
        other.value,
        other.type
    )

    fun set(value: Number, type: AngleType): EAngle {
        this.value = value.f
        this.type = type
        return this
    }

    override fun reset() {
        set(0, AngleType.DEGREE)
    }

    fun offset(other: EAngleType) {
        val increment = when (type) {

            AngleType.DEGREE -> other.degrees
            AngleType.RADIAN -> other.radians
            AngleType.ROTATION -> other.rotation
        }

        value += increment
    }

    override fun toString(): String {
        return "${degrees.toInt() % 360}°"
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