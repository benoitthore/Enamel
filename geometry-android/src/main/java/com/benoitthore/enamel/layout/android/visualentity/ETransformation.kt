package com.benoitthore.enamel.layout.android.visualentity

import com.benoitthore.enamel.geometry.primitives.EAngleMutable
import com.benoitthore.enamel.geometry.primitives.EPointMutable
import com.benoitthore.enamel.layout.android.visualentity.style.EStyle

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