package com.benoitthore.enamel.geometry.builders

import com.benoitthore.enamel.geometry.primitives.angle.EAngle
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransform

interface TransformBuilder : PointBuilder, BaseBuilder {
    fun ETransform(
        rotation: EAngle = E.Angle(),
        rotationPivot: EPoint = E.Point.half(),
        scale: EPoint = E.Point.unit(),
        scalePivot: EPoint = E.Point.half(),
        translation: EPoint = E.Point()
    ): ETransform = TransformMutable(
        rotation = rotation,
        rotationPivot = rotationPivot,
        scale = scale,
        scalePivot = scalePivot,
        translation = translation
    )
}
