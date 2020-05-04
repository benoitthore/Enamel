package com.benoitthore.enamel.geometry.builders

import com.benoitthore.enamel.geometry.primitives.angle.EAngle
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransform

interface TransformBuilder : PointBuilder, BaseBuilder {
    fun ETransform(
        rotation: EAngle = E.AngleMutable(),
        rotationPivot: EPoint = E.PointMutable.half(),
        scale: EPoint = E.PointMutable.unit(),
        scalePivot: EPoint = E.PointMutable.half(),
        translation: EPoint = E.PointMutable()
    ): ETransform = ETransformMutable(
        rotation = rotation,
        rotationPivot = rotationPivot,
        scale = scale,
        scalePivot = scalePivot,
        translation = translation
    )
}
