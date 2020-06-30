package com.benoitthore.visualentity

import com.benoitthore.enamel.geometry.builders.E
import com.benoitthore.enamel.geometry.figures.rect.ERect
import com.benoitthore.enamel.geometry.primitives.transfrom.ETransformMutable
import com.benoitthore.visualentity.style.EStyle

internal class RectVisualEntityMutableImpl(
    private val rect: ERect,
    override var style: EStyle
) :
    RectVisualEntityMutable, ERect by rect {
    override fun toMutable(): RectVisualEntityMutable = rect.toMutable().toVisualEntity(style)
    override fun toImmutable(): RectVisualEntity = toMutable()
    override val transform: ETransformMutable = E.TransformMutable()
}

interface RectVisualEntity : ERect, VisualEntity<ERect, ERect> {
    override fun toMutable(): RectVisualEntityMutable
    override fun toImmutable(): RectVisualEntity
}

interface RectVisualEntityMutable : RectVisualEntity, ERect,
    VisualEntityMutable<ERect, ERect>

fun ERect.toVisualEntity(style: EStyle = EStyle()): RectVisualEntity =
    toMutable().toVisualEntity(style)

fun ERect.toVisualEntity(style: EStyle = EStyle()): RectVisualEntityMutable =
    RectVisualEntityMutableImpl(this, style)
