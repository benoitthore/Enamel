package com.benoitthore.visualentity

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransform
import com.benoitthore.visualentity.style.EStyle

internal class RectVisualEntityImpl(
    private val rect: ERect,
    override var style: EStyle
) :
    RectVisualEntity, ERect by rect {
    override fun copy(): RectVisualEntity = rect.copy().toVisualEntity(style)
    override val transform: ETransform = E.TransformMutable()
}

interface RectVisualEntity : ERect, VisualEntity<ERect> {
    override fun copy(): RectVisualEntity
}

fun ERect.toVisualEntity(style: EStyle = EStyle()): RectVisualEntity =
    RectVisualEntityImpl(this, style)
