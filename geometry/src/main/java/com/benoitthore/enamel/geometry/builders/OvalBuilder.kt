package com.benoitthore.enamel.geometry.builders

import com.benoitthore.enamel.geometry.figures.EOval
import com.benoitthore.enamel.geometry.primitives.*

interface OvalBuilder : BaseBuilder {

    fun Oval(centerX: Number, centerY: Number, rx: Number, ry: Number): EOval
            = OvalMutable(centerX, centerY, rx, ry)

    object Angle {
        val zero = 0.degrees()
        val unit = 1.rotations()
    }

    object MutableAngle {
        fun zero(target: EAngleMutable) = 0.degrees(target)
        fun unit(target: EAngleMutable) = 1.rotations(target)
    }
}