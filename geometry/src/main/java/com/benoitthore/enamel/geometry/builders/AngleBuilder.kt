package com.benoitthore.enamel.geometry.builders

import com.benoitthore.enamel.geometry.primitives.angle.*
import com.benoitthore.enamel.geometry.primitives.angle.EAngle.*
import com.benoitthore.enamel.geometry.primitives.angle.EAngle.*

interface AngleBuilder : BaseBuilder {

    object Angle {
        fun zero(target: EAngle) = 0.degrees(target)
        fun unit(target: EAngle) = 1.rotations(target)
    }
}