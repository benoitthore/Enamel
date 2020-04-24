package com.benoitthore.enamel.layout.android.visualentity

import com.benoitthore.enamel.geometry.primitives.ETransformable
import com.benoitthore.enamel.geometry.primitives.ETransformation

interface VisualEntity :
    ETransformable {
    class Impl : VisualEntity {
        override val transformation: ETransformation =
            ETransformation()
    }
    override val transformation: ETransformation
}