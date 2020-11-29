package com.benoitthore.enamel.geometry.primitives.transfrom

import com.benoitthore.enamel.geometry.builders.*
import com.benoitthore.enamel.geometry.primitives.angle.EAngle
import com.benoitthore.enamel.geometry.primitives.point.EPoint

internal class ETransformImpl(
    override val rotation: EAngle = Angle(),
    override val rotationPivot: EPoint = Point.Half(),
    override val scale: EPoint = Point.Unit(),
    override val scalePivot: EPoint = Point.Half(),
    override val translation: EPoint = Point()
) : ETransform