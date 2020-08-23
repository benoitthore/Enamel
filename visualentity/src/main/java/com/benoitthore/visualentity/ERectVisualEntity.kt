package com.benoitthore.visualentity

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransform
import com.benoitthore.visualentity.style.EStyle

internal class RectVisualEntityImpl(
    private val Rect: ERect,
    override var style: EStyle
) :
    ERectVisualEntity, ERect by Rect {
    override fun copy(): ERectVisualEntity = Rect.copy().toVisualEntity(style)
    override val transform: ETransform = E.Transform()
}

interface ERectVisualEntity : ERect, VisualEntity<ERect> {
    override fun copy(): ERectVisualEntity
}
