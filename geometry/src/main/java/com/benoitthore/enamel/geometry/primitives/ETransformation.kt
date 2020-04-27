package com.benoitthore.enamel.geometry.primitives

import com.benoitthore.enamel.geometry.e.E

interface ETransformation {
    val rotation: EAngleMutable
    val rotationPivot: EPointMutable
    val scale: EPointMutable
    val scalePivot: EPointMutable
    val translation: EPointMutable

    class Impl : ETransformation{
        override val rotation: EAngleMutable = EAngleMutable()
        override val rotationPivot: EPointMutable = E.PointMutable.half
        override val scale: EPointMutable = E.PointMutable.unit
        override val scalePivot: EPointMutable = E.PointMutable.half
        override val translation: EPointMutable = E.mpoint()
    }
}

interface ETransformable {
    val transformation: ETransformation
}