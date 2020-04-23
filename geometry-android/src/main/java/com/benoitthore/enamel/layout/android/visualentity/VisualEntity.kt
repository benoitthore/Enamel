package com.benoitthore.enamel.layout.android.visualentity

import com.benoitthore.enamel.layout.android.visualentity.style.EStyle
import com.benoitthore.enamel.layout.android.visualentity.style.EStyleable

abstract class VisualEntity : EStyleable, ETransformable {
    override val transformation: ETransformation = ETransformation()
}