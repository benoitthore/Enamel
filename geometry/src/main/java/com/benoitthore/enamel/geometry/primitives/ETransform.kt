package com.benoitthore.enamel.geometry.primitives

import com.benoitthore.enamel.geometry.builders.E

interface ETransformable {
    val transform: ETransform
}

interface ETransformableMutable : ETransformable {
    override val transform: ETransformMutable
}

interface ETransform {
    val rotation: EAngle
    val rotationPivot: EPoint
    val scale: EPoint
    val scalePivot: EPoint
    val translation: EPoint

}

interface ETransformMutable : ETransform {
    override val rotation: EAngleMutable
    override val rotationPivot: EPointMutable
    override val scale: EPointMutable
    override val scalePivot: EPointMutable
    override val translation: EPointMutable

    class Impl(
        override val rotation: EAngleMutable = E.AngleMutable(),
        override val rotationPivot: EPointMutable = E.PointMutable.half(),
        override val scale: EPointMutable = E.PointMutable.unit(),
        override val scalePivot: EPointMutable = E.PointMutable.half(),
        override val translation: EPointMutable = E.PointMutable()
    ) : ETransformMutable
}