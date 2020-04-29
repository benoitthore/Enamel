package com.benoitthore.enamel.geometry.builders

import com.benoitthore.enamel.geometry.primitives.*

interface AngleBuilder : BaseBuilder {
    fun Angle(value: Number = 0f, type: AngleType = AngleType.RADIAN): EAngle =
        AngleMutable(value, type).toMutable()

    object Angle {
        val zero = 0.degrees()
        val unit = 1.rotations()
    }

    object MutableAngle {
        fun zero(target: EAngleMutable) = 0.degrees(target)
        fun unit(target: EAngleMutable) = 1.rotations(target)
    }
}