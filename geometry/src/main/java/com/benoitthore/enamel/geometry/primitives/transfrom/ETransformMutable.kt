package com.benoitthore.enamel.geometry.primitives.transfrom

import com.benoitthore.enamel.geometry.primitives.angle.EAngleMutable
import com.benoitthore.enamel.geometry.primitives.point.EPointMutable

interface ETransformMutable : ETransform {
    override val rotation: EAngleMutable
    override val rotationPivot: EPointMutable
    override val scale: EPointMutable
    override val scalePivot: EPointMutable
    override val translation: EPointMutable

}