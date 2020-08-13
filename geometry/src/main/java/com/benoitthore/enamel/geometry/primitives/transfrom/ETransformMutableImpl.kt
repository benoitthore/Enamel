package com.benoitthore.enamel.geometry.primitives.transfrom

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.primitives.angle.EAngle
import com.benoitthore.enamel.geometry.primitives.point.EPoint

internal class ETransformImpl(
    override val rotation: EAngle = E.Angle(),
    override val rotationPivot: EPoint = E.Point.Half(),
    override val scale: EPoint = E.Point.Unit(),
    override val scalePivot: EPoint = E.Point.Half(),
    override val translation: EPoint = E.Point()
) : ETransform