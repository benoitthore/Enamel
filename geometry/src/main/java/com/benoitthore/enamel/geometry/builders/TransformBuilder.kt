package com.benoitthore.enamel.geometry.builders

import com.benoitthore.enamel.geometry.primitives.EAngle
import com.benoitthore.enamel.geometry.primitives.EPoint
import com.benoitthore.enamel.geometry.primitives.ETransform

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
