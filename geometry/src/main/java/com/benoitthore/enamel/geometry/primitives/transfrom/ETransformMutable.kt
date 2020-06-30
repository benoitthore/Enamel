package com.benoitthore.enamel.geometry.primitives.transfrom

import com.benoitthore.enamel.geometry.primitives.angle.EAngle
import com.benoitthore.enamel.geometry.primitives.point.EPointMutable

interface ETransformMutable : ETransform {
    override val rotation: EAngle
    override val rotationPivot: EPointMutable
    override val scale: EPointMutable
    override val scalePivot: EPointMutable
    override val translation: EPointMutable
}