package com.benoitthore.enamel.geometry.primitives

class ETransformation {
    val rotation: EAngleMutable = EAngleMutable()
    val rotationPivot: EPointMutable = EPointMutable.half

    val scale: EPointMutable = EPointMutable.unit
    val scalePivot: EPointMutable = EPointMutable.half

    val translation: EPointMutable = EPointMutable()
}

interface ETransformable {
    val transformation: ETransformation
}