package com.benoitthore.enamel.geometry.builders

import com.benoitthore.enamel.geometry.primitives.angle.*
import com.benoitthore.enamel.geometry.primitives.angle.EAngle.*
import com.benoitthore.enamel.geometry.primitives.angle.EAngle.*

interface AngleBuilder : BaseBuilder {
    fun Angle(
        value: Number = 0f, type:
        AngleType = AngleType.RADIAN
    ): EAngle =
        AngleMutable(value, type).copy()

    object Angle {
        val zero = 0.degrees()
        val unit = 1.rotations()
    }

    object MutableAngle {
        fun zero(target: EAngle) = 0.degrees(target)
        fun unit(target: EAngle) = 1.rotations(target)
    }
}