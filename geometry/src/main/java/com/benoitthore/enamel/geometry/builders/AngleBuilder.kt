package com.benoitthore.enamel.geometry.builders

import com.benoitthore.enamel.geometry.primitives.angle.*
import com.benoitthore.enamel.geometry.primitives.angle.EAngle.*
import com.benoitthore.enamel.geometry.primitives.angle.EAngle.*

fun Angle(value: Number = 0f, type: AngleType = AngleType.RADIAN): EAngle =
    EAngleImpl(
        value,
        type
    )

fun Angle(other: EAngle): EAngle = Angle(other.value, other.type)
fun MutableAngle(other: EAngle): EAngle = MutableAngle(other.value, other.type)

fun MutableAngle(value: Number = 0f, type: AngleType = AngleType.RADIAN): EAngleMutable =
    EAngleImpl(
        value,
        type
    )


object Angle {
    fun zero(target: EAngleMutable = MutableAngle()) = 0.degrees(target)
    fun unit(target: EAngleMutable = MutableAngle()) = 1.rotations(target)
}
