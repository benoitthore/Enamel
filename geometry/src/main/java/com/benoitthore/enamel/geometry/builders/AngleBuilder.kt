package com.benoitthore.enamel.geometry.builders

import com.benoitthore.enamel.geometry.primitives.angle.*
import com.benoitthore.enamel.geometry.primitives.angle.EAngle.*
import com.benoitthore.enamel.geometry.primitives.angle.EAngle.*

fun Angle(value: Number = 0f, type: AngleType = AngleType.RADIAN): EAngle =
    EAngleImpl(
        value,
        type
    )

object Angle {
    fun zero(target: EAngle) = 0.degrees(target)
    fun unit(target: EAngle) = 1.rotations(target)
}
