package com.benoitthore.enamel.geometry.builders

import com.benoitthore.enamel.geometry.figures.oval.EOval
import com.benoitthore.enamel.geometry.primitives.angle.EAngle
import com.benoitthore.enamel.geometry.primitives.angle.degrees
import com.benoitthore.enamel.geometry.primitives.angle.rotations

interface OvalBuilder : BaseBuilder {

    fun Oval(other: EOval) = Oval(other.centerX, other.centerY, other.rx, other.ry)

}