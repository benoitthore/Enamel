package com.benoitthore.enamel.geometry.builders

import com.benoitthore.enamel.geometry.figures.EOval
import com.benoitthore.enamel.geometry.primitives.*

interface OvalBuilder : BaseBuilder {

    fun Oval(centerX: Number = 0, centerY: Number= 0, rx: Number= 0, ry: Number= 0): EOval =
        OvalMutable(centerX, centerY, rx, ry)


    fun OvalMutable(other: EOval) = OvalMutable(other.centerX, other.centerY, other.rx, other.ry)
    fun Oval(other: EOval): EOval = OvalMutable(other)

    object Angle {
        val zero = 0.degrees()
        val unit = 1.rotations()
    }

    object MutableAngle {
        fun zero(target: EAngleMutable) = 0.degrees(target)
        fun unit(target: EAngleMutable) = 1.rotations(target)
    }
}