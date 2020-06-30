package com.benoitthore.enamel.geometry.primitives.transfrom

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.primitives.angle.EAngle
import com.benoitthore.enamel.geometry.primitives.point.EPointMutable

internal class ETransformMutableImpl(
    override val rotation: EAngle = E.AngleMutable(),
    override val rotationPivot: EPointMutable = E.PointMutable.half(),
    override val scale: EPointMutable = E.PointMutable.unit(),
    override val scalePivot: EPointMutable = E.PointMutable.half(),
    override val translation: EPointMutable = E.PointMutable()
) : ETransformMutable