package com.benoitthore.enamel.geometry.builders

import com.benoitthore.enamel.geometry.primitives.angle.EAngle
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransform
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransformImpl


fun Transform(
    rotation: EAngle = E.Angle(),
    rotationPivot: EPoint = E.Point.Half(),
    scale: EPoint = E.Point.Unit(),
    scalePivot: EPoint = E.Point.Half(),
    translation: EPoint = E.Point()
): ETransform =
    ETransformImpl(
        rotation = rotation,
        rotationPivot = rotationPivot,
        scale = scale,
        scalePivot = scalePivot,
        translation = translation
    )


fun ETransform(
    rotation: EAngle = E.Angle(),
    rotationPivot: EPoint = E.Point.Half(),
    scale: EPoint = E.Point.Unit(),
    scalePivot: EPoint = E.Point.Half(),
    translation: EPoint = E.Point()
): ETransform = Transform(
    rotation = rotation,
    rotationPivot = rotationPivot,
    scale = scale,
    scalePivot = scalePivot,
    translation = translation
)