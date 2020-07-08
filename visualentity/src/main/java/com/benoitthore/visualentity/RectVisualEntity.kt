package com.benoitthore.visualentity

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransform
import com.benoitthore.visualentity.style.EStyle
import com.benoitthore.visualentity.style.StyleBuilder
import com.benoitthore.visualentity.style.style

internal class RectVisualEntityImpl(
    private val Rect: ERect,
    override var style: EStyle
) :
    RectVisualEntity, ERect by Rect {
    override fun copy(): RectVisualEntity = Rect.copy().toVisualEntity(style)
    override val transform: ETransform = E.Transform()
}

interface RectVisualEntity : ERect, VisualEntity<ERect> {
    override fun copy(): RectVisualEntity
}
