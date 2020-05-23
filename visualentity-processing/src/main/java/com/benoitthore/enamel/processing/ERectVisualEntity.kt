package com.benoitthore.enamel.processing

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.figures.rect.ERectMutable
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransformMutable
import com.benoitthore.enamel.geometry.toMutable
import com.benoitthore.visualentity.style.EStyle
import com.benoitthore.visualentity.style.EStyleable
import processing.core.PApplet


fun ERect.toVisualEntity(style: EStyle = EStyle()) = ERectVisualEntity(toMutable()).apply {
    this.style = style
}

class ERectVisualEntity internal constructor(
    val shape: ERectMutable,
    override val drawer: VisualEntityDrawer = VisualEntityDrawer { papplet ->
        papplet.draw(shape)
    }
) :
    ProcessingVisualEntity,
    EStyleable by drawer,
    ERectMutable by shape {
    override val transform: ETransformMutable = E.TransformMutable()
}


