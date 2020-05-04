package com.benoitthore.enamel.geometry.primitives.transfrom

import com.benoitthore.enamel.geometry.primitives.angle.EAngle
import com.benoitthore.enamel.geometry.primitives.point.EPoint

interface ETransform {
    val rotation: EAngle
    val rotationPivot: EPoint
    val scale: EPoint
    val scalePivot: EPoint
    val translation: EPoint
}