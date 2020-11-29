package com.benoitthore.enamel.geometry.builders

import com.benoitthore.enamel.geometry.primitives.angle.EAngle
import com.benoitthore.enamel.geometry.primitives.point.EPoint
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransform
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransformImpl


fun Transform(
    rotation: EAngle = Angle(),
    rotationPivot: EPoint = Point.Half(),
    scale: EPoint = Point.Unit(),
    scalePivot: EPoint = Point.Half(),
    translation: EPoint = Point()
): ETransform =
    ETransformImpl(
        rotation = rotation,
        rotationPivot = rotationPivot,
        scale = scale,
        scalePivot = scalePivot,
        translation = translation
    )


fun ETransform(
    rotation: EAngle = Angle(),
    rotationPivot: EPoint = Point.Half(),
    scale: EPoint = Point.Unit(),
    scalePivot: EPoint = Point.Half(),
    translation: EPoint = Point()
): ETransform = Transform(
    rotation = rotation,
    rotationPivot = rotationPivot,
    scale = scale,
    scalePivot = scalePivot,
    translation = translation
)